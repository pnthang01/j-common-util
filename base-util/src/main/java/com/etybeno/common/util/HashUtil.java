package com.etybeno.common.util;

/**
 * Created by thangpham on 28/12/2017.
 */

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        HashValue hash = createHash("ahehehe", "MD5");
        System.out.println(hash.asString());
        System.out.println(hash.asCompactString());
        System.out.println(hash.asHexString());
        System.out.println(hash.asZeroPaddedHexString(4));
    }

    public static HashValue createHash(String scriptText, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = createMessageDigest(algorithm);
        messageDigest.update(scriptText.getBytes());
        return new HashValue(messageDigest.digest());
    }

    public static HashValue createHash(File file, String algorithm) throws NoSuchAlgorithmException, FileNotFoundException {
        return createHash(new FileInputStream(file), algorithm);
    }

    public static HashValue createHash(InputStream instr, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = createMessageDigest(algorithm);
            byte[] buffer = new byte[4096];
            try {
                while (true) {
                    int nread = instr.read(buffer);
                    if (nread < 0) {
                        break;
                    }
                    messageDigest.update(buffer, 0, nread);
                }
            } finally {
                instr.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new HashValue(messageDigest.digest());
    }

    private static MessageDigest createMessageDigest(String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    public static String createCompactMD5(String scriptText) throws NoSuchAlgorithmException {
        return createHash(scriptText, "MD5").asCompactString();
    }

    public static String compactStringFor(HashCode hashCode) {
        return compactStringFor(hashCode.toByteArray());
    }

    public static String compactStringFor(byte[] digest) {
        return new HashValue(digest).asCompactString();
    }

    public static HashValue sha1(byte[] bytes) throws NoSuchAlgorithmException {
        return createHash(new ByteArrayInputStream(bytes), "SHA1");
    }

    public static HashValue sha1(InputStream inputStream) throws NoSuchAlgorithmException {
        return createHash(inputStream, "SHA1");
    }

    public static HashValue sha1(File file) throws FileNotFoundException, NoSuchAlgorithmException {
        return createHash(file, "SHA1");
    }

    public static HashValue sha256(byte[] bytes) throws NoSuchAlgorithmException {
        return createHash(new ByteArrayInputStream(bytes), "SHA-256");
    }

    public static HashValue sha256(InputStream inputStream) throws NoSuchAlgorithmException {
        return createHash(inputStream, "SHA-256");
    }

    public static HashValue sha256(File file) throws FileNotFoundException, NoSuchAlgorithmException {
        return createHash(file, "SHA-256");
    }
}
