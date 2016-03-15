package com.jnrcorp.gtfs.util;

public class FileNameUtil {

	/**
	 * @return For example, if fileName is "test.doc" then return ".doc".
	 */
	public static String getFileNameSuffix(String fileName) {
		String result = null;
		if (fileName != null) {
			int pos = fileName.lastIndexOf(".");
			result = fileName.substring(pos, fileName.length());
		}
		return result;
	}

	private FileNameUtil() {
		super();
	}

}
