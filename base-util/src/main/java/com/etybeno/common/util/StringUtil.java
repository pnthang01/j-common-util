package com.etybeno.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.List;


/**
 * Created by thangpham on 20/11/2017.
 */
public class StringUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final Gson GSON = new Gson();

    private static final Type LIST_JSON_TYPE = new TypeToken<List<String>>() {
    }.getType();

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static List<String> fromJsonToListString(String json) {
        return GSON.fromJson(json, LIST_JSON_TYPE);
    }

    public static Long Dot2LongIP(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256, power));
        }
        return num;
    }

    public static double safeParseFloat(Float o) {
        return safeParseFloat(o, 0);
    }

    public static double safeParseFloat(Object o, float defaultVal) {
        if (null == o) return defaultVal;
        return safeParseFloat(o.toString(), defaultVal);
    }

    public static double safeParseFloat(String s) {
        return safeParseFloat(s, 0);
    }

    public static double safeParseFloat(String s, float defaultVal) {
        if (isEmpty(s)) return defaultVal;
        double n = defaultVal;
        try {
            n = Float.parseFloat(s);
        } catch (Throwable e) {
        }
        return n;
    }

    public static double safeParseDouble(Object o) {
        return safeParseDouble(o, 0);
    }

    public static double safeParseDouble(Object o, double defaultVal) {
        if (null == o) return defaultVal;
        return safeParseDouble(o.toString(), defaultVal);
    }

    public static double safeParseDouble(String s) {
        return safeParseDouble(s, 0);
    }

    public static double safeParseDouble(String s, double defaultVal) {
        if (isEmpty(s)) return defaultVal;
        double n = defaultVal;
        try {
            n = Double.parseDouble(s);
        } catch (Throwable e) {
        }
        return n;
    }

    public static long safeParseLong(Object o) {
        return safeParseLong(o, 0);
    }

    public static long safeParseLong(Object o, long defaultVal) {
        if (null == o) return defaultVal;
        return safeParseLong(o.toString(), defaultVal);
    }

    public static long safeParseLong(String s) {
        return safeParseLong(s, 0);
    }

    public static long safeParseLong(String s, long defaultVal) {
        if (isEmpty(s)) return defaultVal;
        long n = defaultVal;
        try {
            n = Long.parseLong(s);
        } catch (Throwable e) {
        }
        return n;
    }

    public static int safeParseInt(Object o) {
        return safeParseInt(o, 0);
    }

    public static int safeParseInt(Object o, int defaultVal) {
        if (null == o) return defaultVal;
        return safeParseInt(o.toString(), defaultVal);
    }

    public static int safeParseInt(String s) {
        return safeParseInt(s, 0);
    }

    public static int safeParseInt(String s, int defaultVal) {
        if (isEmpty(s)) return defaultVal;
        int n = defaultVal;
        try {
            n = Integer.parseInt(s);
        } catch (Throwable e) {
        }
        return n;
    }

    public static boolean safeParseBoolean(Object o) {
        return safeParseBoolean(o, false);
    }

    public static boolean safeParseBoolean(Object o, boolean defaultVal) {
        if (null == o) return defaultVal;
        return safeParseBoolean(o.toString(), defaultVal);
    }

    public static boolean safeParseBoolean(String s) {
        return safeParseBoolean(s, false);
    }

    public static boolean safeParseBoolean(String s, boolean defaultVal) {
        if (isEmpty(s)) return false;
        boolean n = defaultVal;
        try {
            n = Boolean.parseBoolean(s);
        } catch (Throwable e) {
        }
        return n;
    }


    public static boolean isNullOrEmpty(Object o) {
        if (o == null || "".equals(o.toString())) return true;
        return false;
    }

    public static boolean isNullOrEmpty(String s) {
        if (s == null || "".equals(s)) return true;
        return false;
    }

    public static boolean isEmpty(String s) {
        if (s == null) return true;
        return s.trim().isEmpty();
    }

    public static boolean isEmpty(Object s) {
        if (s == null) return true;
        return s.toString().isEmpty();
    }


    public static String toString(String delimiter, Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object a : args) {
            if (null != a) sb.append(a).append(delimiter);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static String toString(Object... args) {
        StringBuilder s = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                s.append(arg);
            }
        }
        return s.toString();
    }
}
