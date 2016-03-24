package com.jnrcorp.gtfs.load.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jnrcorp.gtfs.dao.hibernate.BaseObjectDAO;
import com.jnrcorp.gtfs.dao.hibernate.GTFSRemovalDAO;
import com.jnrcorp.gtfs.dao.model.DAOBaseObject;
import com.jnrcorp.gtfs.exception.GenericIOException;
import com.jnrcorp.gtfs.exception.ImportHeaderException;
import com.jnrcorp.gtfs.exception.InvalidInputException;
import com.jnrcorp.gtfs.load.CSVReader;
import com.jnrcorp.gtfs.load.GTFSImportUtil;
import com.jnrcorp.gtfs.load.GTFSRowImport;
import com.jnrcorp.gtfs.load.ImportFailure;
import com.jnrcorp.gtfs.load.ImportHeaderMatchInfo;
import com.jnrcorp.gtfs.util.FileNameUtil;
import com.jnrcorp.gtfs.util.StringUtil;

@Service("importDataService")
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ImportDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataService.class);

	@Autowired
	@Qualifier("baseObjectDAO")
	private BaseObjectDAO baseObjectDAO;

	@Autowired
	@Qualifier("gtfsRemovalDAO")
	private GTFSRemovalDAO gtfsRemovalDAO;

	public <T> void removeData(Class<T> theClass) {
		List<T> objectsToRemove = baseObjectDAO.getAll(theClass);
		baseObjectDAO.removeAll(objectsToRemove);
	}

	public <T extends DAOBaseObject> void loadData(Class<T> theClass, File theFile) {
		List<T> objectsToSave = createObjects(theClass, theFile);
		LOGGER.info(theClass.getName() + " : " + objectsToSave.size());
		baseObjectDAO.saveOrUpdateAll(objectsToSave);
		LOGGER.info("Finished file: " + theFile.getName());
	}

	private <T extends DAOBaseObject> List<T> createObjects(Class<T> theClass, File file) {
		List<T> results = null;
		try {
			results = getObjectsForOperation(theClass, file);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return results;
	}

	private <T extends DAOBaseObject> void setFields(T object, Class<T> theClass, Map<String, String> objectValues) {
		Map<String, ? extends GTFSRowImport.FieldSetter<?>> fieldsByColumnHeader = GTFSRowImport.fieldSettersByClass.get(theClass);
		for (String columnHeaderName : fieldsByColumnHeader.keySet()) {
			if (objectValues.containsKey(columnHeaderName)) {
				GTFSRowImport.FieldSetter<DAOBaseObject> fieldSetter = (GTFSRowImport.FieldSetter<DAOBaseObject>) fieldsByColumnHeader.get(columnHeaderName);
				String consumerFieldValue = objectValues.get(columnHeaderName);
				fieldSetter.setValue(object, consumerFieldValue, baseObjectDAO);
			}
		}
	}

	private <T extends DAOBaseObject> List<T> getObjectsForOperation(Class<T> theClass, File file) throws IOException, InstantiationException, IllegalAccessException {
		List<T> results = new ArrayList<>();
		List<ImportFailure> failures = new ArrayList<>();

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		LineNumberReader lin = new LineNumberReader(new BufferedReader(isr));
		CSVReader reader = new CSVReader(lin, ',', '"');
		String[] columnNames = reader.readNext();

		if (columnNames == null) {
			LOGGER.warn("Patron file format error: file is empty ");
			throw new ImportHeaderException("Error before parsing import file - Headers are empty: headers=" + columnNames);
		}
		ImportHeaderMatchInfo headerMatchInfo = compareHeader(columnNames);
		if (!headerMatchInfo.isFoundMatch()) {
			LOGGER.error("Error before parsing import file: !compareHeader: headers=" + columnNames + "; headerMatchInfo=" + headerMatchInfo.getCause());
			throw new ImportHeaderException("Please verify the column headers in your import file: " + headerMatchInfo.getCause());
		}

		String[] columnsValues = null;
		int currentLineCount = 0;
		while ((columnsValues = reader.readNext()) != null) {
			try {
				if (Thread.interrupted()) {
					break;
				}
				if (currentLineCount > 0 && currentLineCount % 100000 == 0) {
					LOGGER.info("currentCount=" + currentLineCount);
				}
				++currentLineCount;
				verifyRowPopulated(columnsValues);
				Map<String, String> rowValuesByHeaderName = new HashMap<>();
				for (int i = 0; i < columnNames.length; i++) {
					rowValuesByHeaderName.put(columnNames[i], columnsValues[i]);
				}
				T newObject = theClass.newInstance();
				setFields(newObject, theClass, rowValuesByHeaderName);
				results.add(newObject);

				LOGGER.debug("Successfully prepped gtfs import: " + StringUtils.join(columnsValues, ';') + "; currentCount=" + currentLineCount);
			} catch (InvalidInputException iie) {
				processError(columnsValues, currentLineCount, iie);
				failures.add(new ImportFailure(currentLineCount, iie.getMessage(), columnsValues));
			} catch (InvalidParameterException ipe) {
				processError(columnsValues, currentLineCount, ipe);
				failures.add(new ImportFailure(currentLineCount, ipe.getMessage(), columnsValues));
			} catch (RuntimeException ex) {
				processError(columnsValues, currentLineCount, ex);
				failures.add(new ImportFailure(currentLineCount, ex.getMessage(), columnsValues));
			}
		}
		lin.close();
		for (ImportFailure importFailure : failures) {
			String errorMessage = importFailure.getFailureMessage();
			LOGGER.error(errorMessage);
		}
		if (!CollectionUtils.isEmpty(failures)) {
			throw new GenericIOException("There was a parsing error with at least 1 row, cannot start import process.  Please see details below.");
		}
		return results;
	}

	private void verifyRowPopulated(String[] columns) {
		if(columns == null || columns.length == 0) {
			throw new InvalidInputException("No data in this line");
		}
		for (String column : columns) {
			if(!StringUtils.isBlank(column)) {
				return;
			}
		}
		throw new InvalidInputException("No data in this line");
	}

	private void processError(String[] columnsValues, int currentLineCount, Exception ex) {
		String errorMsg = "There was an error trying to import: lineNumber=" + currentLineCount + "; cause=" + ex.getMessage() + "; line=" + StringUtils.join(columnsValues, ',');
		LOGGER.error(errorMsg);
	}

	private ImportHeaderMatchInfo compareHeader(String[] headersInFile) {
		ImportHeaderMatchInfo headerMatchInfo = new ImportHeaderMatchInfo(true);

		String[] validHeadersArray = GTFSImportUtil.getHeader();
		Map<String, String> validHeaders = new HashMap<>();
		for (String string : validHeadersArray) {
			validHeaders.put(string, string);
		}

		for (int i = 0; i < headersInFile.length; i++) {
			String currentHeader = headersInFile[i].trim();
			if (!validHeaders.containsKey(currentHeader)) {
				StringBuilder errorMessage = new StringBuilder();
				errorMessage.append("Header with name '");
				errorMessage.append(currentHeader);
				errorMessage.append("' (Column #" + (i + 1) + ") is not a valid header name.");
				errorMessage.append(" Valid headers are as follows: <br>" + StringUtil.concatStringArray(validHeadersArray, "<br>"));
				headerMatchInfo.setError(errorMessage.toString(), false);
				break;
			}
		}
		return headerMatchInfo;
	}

}
