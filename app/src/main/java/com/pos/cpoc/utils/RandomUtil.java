package com.pos.cpoc.utils;

import java.util.Random;
/**
 * @author eric.song
 * @date 2021/5/21 14:14
 */
public class RandomUtil {
    /**
     * the specified random data: number and Lower case letters a~z and Upper
     * case letters A~Z
     */
    public static final String CHAR_ALL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * the specified random data: Lower case letters a~z and Upper case letters
     * A~Z
     */
    public static final String CHAR_LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * the specified random data: number
     */
    public static final String CHAR_NUMBER = "0123456789";
    /**
     * the specified random data: number and Upper case letters A~F(Hex string)
     */
    public static final String CHAR_HEX = "0123456789ABCDEF";

    /**
     * Generate the specified length and random string
     *
     * @param length
     *            the specified length
     * @param data
     *            the specified random data
     * @return String
     */
    public static String generateString(int length, String data) {
        StringBuffer sbf = new StringBuffer();
        Random random = new Random();
        int max = data.length();
        for (int i = 0; i < length; i++) {
            sbf.append(data.charAt(random.nextInt(max)));
        }
        return sbf.toString();
    }

    /**
     * Generate the specified length and random hex string
     *
     * @param length
     *            the specified length
     * @return String
     */
    public static String generateHexString(int length) {
        StringBuffer sbf = new StringBuffer();
        Random random = new Random();
        int max = CHAR_HEX.length();
        for (int i = 0; i < length; i++) {
            sbf.append(CHAR_HEX.charAt(random.nextInt(max)));
        }
        return sbf.toString();
    }
}
