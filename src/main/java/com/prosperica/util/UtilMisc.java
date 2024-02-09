package com.prosperica.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

final public class UtilMisc {

	static public String toString(String[] arr, String separator, String flex) {
		String result = "";

		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				result += separator;
			}
			result += flex + arr[i].toString() + flex;
		}

		return result;
	}

	static public String toString(Vector<Object> v, String separator, String flex) {
		String result = "";

		for (int i = 0, max = v.size(); i < max; i++) {
			if (i > 0) {
				result += separator;
			}
			result += flex + v.get(i).toString() + flex;
		}

		return result;
	}

	static public Set<String> toSet(String... vals) {
		Set<String> set = new HashSet<String>();

		for (String v : vals) {
			set.add(v);
		}

		return set;
	}

	static public Vector<String> toVector(String... args) {
		final Vector<String> v = new Vector<String>();

		for (int i = 0; i < args.length; i++) {
			v.add(args[i]);
		}

		return v;
	}

	static public Map<String, String> toMap(String... args) {
		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0], args[i + 1]);
		}

		return map;
	}

	static public Map<String, Object> toMapo(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), args[i + 1]);
		}

		return map;
	}

	static public Map<String, Object> toMap(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), args[i + 1]);
		}

		return map;
	}

	final static public boolean isEmpty(Object o) {
		return o == null;
	}

	final static public boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	final static public boolean isSameOrEmpty(String s1, String s2) {
		if (isEmpty(s1))
			if (isEmpty(s2))
				return true;
			else
				return false;

		if (isEmpty(s2))
			return false;
		else if (s1.equals(s2))
			return true;
		else
			return false;
	}

	final static public boolean isEmpty(Set<Object> s) {
		return s == null || s.size() == 0;
	}

	final static public boolean isEmpty(List<Object> list) {
		return list == null || list.size() == 0;
	}

	final static public boolean isTrue(String s, boolean defaultValue) {
		if (!UtilMisc.isEmpty(s)) {
			if ("true".equalsIgnoreCase(s))
				return true;
			else if ("false".equalsIgnoreCase(s))
				return false;
		}
		return defaultValue;
	}

	final static public String emptyIfNull(Object o) {
		return o == null ? "" : o.toString();
	}

	final static public String emptyIfNull(Object o, Object r) {
		return o == null ? "" : emptyIfNull(r);
	}
}