package com.jnrcorp.gtfs.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String concatStringArray(Object[] strArr) {
		return concatStringArray(strArr, ",");
	}

	public static String concatStringArray(Object[] strArr, String separator) {
		String result = "";
		if (strArr != null) {
			for (Object element : strArr) {
				if (StringUtils.isEmpty(result)) {
					result = element.toString();
				} else {
					result += separator + element.toString();
				}
			}
		}
		return result;
	}

	private StringUtil() {
		super();
	}

}
