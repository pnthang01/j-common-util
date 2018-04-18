package com.etybeno.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by thangpham on 20/11/2017.
 */
public class StringUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final Gson GSON = new Gson();

    private static final Type LIST_JSON_TYPE = new TypeToken<List<String>>() {
    }.getType();

    /**
     * Remove accent from a string
     *
     * @param s
     * @return
     */
    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static List<String> fromJsonToListString(String json) {
        return GSON.fromJson(json, LIST_JSON_TYPE);
    }

    /**
     * Convert Ipv4 to long number
     *
     * @param dottedIP
     * @return
     */
    public static Long Dot2LongIP(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256, power));
        }
        return num;
    }

    public static float safeParseFloat(Float o) {
        return safeParseFloat(o, 0);
    }

    public static float safeParseFloat(Object o, float defaultVal) {
        if (null == o) return defaultVal;
        return safeParseFloat(o.toString(), defaultVal);
    }

    public static float safeParseFloat(String s) {
        return safeParseFloat(s, 0);
    }

    public static float safeParseFloat(String s, float defaultVal) {
        if (isEmpty(s)) return defaultVal;
        float n = defaultVal;
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
        for (Object arg : args) if (arg != null) s.append(arg);
        return s.toString();
    }

    /**
     * Parse arguments from main method to map. If the keys set is empty, it will not be validate.
     * The arguments array must be divisible by 2.
     *
     * @param args
     * @param keys
     * @return
     */
    public static Map<String, String> parseMainArgs(String[] args, Set<String> keys) {
        if (args == null || args.length == 0 || args.length % 2 == 1) return null;
        Map<String, String> rs = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            String key = args[i], value = args[i + 1];
            if (!keys.isEmpty() && !key.contains(key)) {
                throw new IllegalArgumentException(String.format("Key %s is out of scope in %s",
                        key, StringUtil.GSON.toJson(keys)));
            }
            rs.put(key, value);
        }
        return rs;
    }
}
