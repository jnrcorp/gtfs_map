package com.jnrcorp.gtfs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.jnrcorp.gtfs.dao.model.DAOBaseObject;

public class PersistentUtils {

	public static String objectsListToIdsString(Collection<? extends DAOBaseObject> domainObjects) {
		StringBuilder ids = new StringBuilder();
		for (Iterator<? extends DAOBaseObject> it = domainObjects.iterator(); it.hasNext();) {
			DAOBaseObject o = it.next();
			ids.append(o.getId());
			if (it.hasNext()) {
				ids.append(',');
			}
		}
		return ids.toString();
	}

	public static List<Long> objectsListToIdsList(Collection<? extends DAOBaseObject> domainObjects) {
		List<Long> ids = new ArrayList<>();
		if (domainObjects == null || domainObjects.size() == 0) {
			return ids;
		}
		for (DAOBaseObject baseObject : domainObjects) {
			DAOBaseObject o = baseObject;
			ids.add(o.getId());
		}
		return ids;
	}

	public static <K> Map<Long, K> objectsListToMapById(Collection<K> collection) {
		if (collection.size() == 0) {
			return Collections.emptyMap();
		}
		Map<Long, K> map = new HashMap<>();
		for (K obj : collection) {
			DAOBaseObject o = (DAOBaseObject) obj;
			map.put(o.getId(), obj);
		}
		return map;
	}

	public static String intListToIdsString(Collection<?> integerIds) {
		return StringUtils.join(integerIds, ",");
	}

	public static String stringListToString(Collection<String> stringList, Boolean useQuotes) {
		StringBuilder string = new StringBuilder();
		if (!CollectionUtils.isEmpty(stringList)) {
			int count = 0;
			for (String inputString : stringList) {
				count++;
				if (StringUtils.isNotBlank(inputString)) {
					if (count != 1) {
						string.append(',');
					}
					if (useQuotes) {
						string.append("'" + inputString + "'");
					} else {
						string.append(inputString);
					}
				}
			}
		}
		return string.toString();
	}

	public static List<Object[]> toUniqueArrayList(List<?> list, int index) {
		List<Object[]> result = new ArrayList<>();
		Set<Object> keys = new HashSet<Object>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[])list.get(i);
			Object key = objs[index];
			if (key == null) {
				continue;
			}
			if (keys.contains(key)) {
				continue;
			}
			result.add(objs);
			keys.add(key);
		}
		return result;
	}

}
