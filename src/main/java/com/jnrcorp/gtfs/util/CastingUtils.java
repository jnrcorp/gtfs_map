package com.jnrcorp.gtfs.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CastingUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> listAndCast(List l) {
	    return l;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K,V> Map<K,V> mapAndCast(Map m) {
		return m;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Set<T> setAndCast(Set s) {
		return s;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Collection<T> collectionAndCast(Collection c) {
		return c;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Iterator<T> iterateAndCast(Collection c) {
		return c.iterator();
	}

	private CastingUtils() {
		super();
	}

}
