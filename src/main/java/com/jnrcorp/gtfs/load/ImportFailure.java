package com.jnrcorp.gtfs.load;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ImportFailure implements Serializable {

	private static final long serialVersionUID = -4664132021020153057L;

	private Integer lineNumber;
	private String errorMessage;
	private String[] lineValues;

	public ImportFailure(Integer lineNumber, String errorMessage, String[] lineValues) {
		this.lineNumber = lineNumber;
		this.errorMessage = errorMessage;
		this.lineValues = lineValues;
	}

	public String getFailureMessage() {
		return "Failure at line #" + lineNumber + " : error=" + errorMessage + "; values=" + StringUtils.join(lineValues, ',');
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String[] getLineValues() {
		return lineValues;
	}

}
