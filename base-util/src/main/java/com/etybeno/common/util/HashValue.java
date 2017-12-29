package com.etybeno.common.util;

import com.google.common.base.Strings;

import java.math.BigInteger;

/**
 * Created by thangpham on 28/12/2017.
 */
public class HashValue {
    private final BigInteger digest;

    public HashValue(byte[] digest) {
        this.digest = new BigInteger(1, digest);
    }

    public HashValue(String hexString) {
        this.digest = new BigInteger(hexString, 16);
    }

    public static HashValue parse(String inputString) {
        if (inputString == null || inputString.length() == 0) return null;
        return new HashValue(parseInput(inputString));
    }

    private static String parseInput(String inputString) {
        if (inputString == null) return null;
        String cleaned = inputString.trim().toLowerCase();
        int spaceIndex = cleaned.indexOf(' ');
        if (spaceIndex != -1) {
            String firstPart = cleaned.substring(0, spaceIndex);
            if (firstPart.startsWith("md") || firstPart.startsWith("sha")) {
                cleaned = cleaned.substring(cleaned.lastIndexOf(' ') + 1);
            } else if (firstPart.endsWith(":")) {
                cleaned = cleaned.substring(spaceIndex + 1).replace(" ", "");
            } else {
                cleaned = cleaned.substring(0, spaceIndex);
            }
        }
        return cleaned;
    }

    public String asString() {
        return digest.toString();
    }

    public String asCompactString() {
        return digest.toString(36);
    }

    public String asHexString() {
        return digest.toString(16);
    }

    public byte[] asByteArray() {
        return digest.toByteArray();
    }

    public BigInteger asBigInteger() {
        return digest;
    }

    public String asZeroPaddedHexString(int expectedLength) {
        return Strings.padStart(asHexString(), expectedLength, '0');
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof HashValue)) return false;
        HashValue otherHashValue = (HashValue) other;
        return digest.equals(otherHashValue.digest);
    }

    @Override
    public int hashCode() {
        return digest.hashCode();
    }
}