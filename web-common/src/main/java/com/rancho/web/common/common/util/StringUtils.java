package com.rancho.web.common.common.util;

public class StringUtils {

    public static boolean endsWithAny(String string, String[] searchStrings) {
        if (isEmpty(string) || searchStrings == null || searchStrings.length == 0) {
            return false;
        }
        for (int i = 0; i < searchStrings.length; i++) {
            String searchString = searchStrings[i];
            if (StringUtils.endsWith(string, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWithAny(String string, String[] searchStrings) {
        if (isEmpty(string) || searchStrings == null || searchStrings.length == 0) {
            return false;
        }
        for (int i = 0; i < searchStrings.length; i++) {
            String searchString = searchStrings[i];
            if (searchString.equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
    }
}
