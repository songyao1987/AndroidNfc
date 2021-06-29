package com.pos.cpoc.utils;

/**
 * @author eric.song
 * @date 2021/5/21 13:36
 */
public class ByteUtil {

    /**
     * Convert a byte array to hex string
     *
     * @param byte[] src the specified hex string
     * @return String
     */
    public static String convertBytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert a hex string to byte array
     *
     * @param String
     *            src the specified hex string
     * @return byte[]
     */
    public static byte[] convertHexToBytes(String src) {
        src = src.toUpperCase();
        int length = src.length() / 2;
        char[] hexChars = src.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            d[i] = (byte) (charToByte(hexChars[i * 2]) << 4 | charToByte(hexChars[i * 2 + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String covertHexToASCII(String str) {
        if (str == null)
            return null;
        try {
            String[] ss = str.replaceAll("..", "$0 ").split(" ");
            StringBuffer sb = new StringBuffer();
            for (String d : ss) {
                sb.append((char) Integer.parseInt(d, 16));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return null;

    }

    public static String hexToStringGBK(String s) {
        if (s == null)
            return "";
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        try {
            s = new String(baKeyword, "GBK");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        }
        return s;
    }


    public static String bytes2HexString(byte[] data) {
        if (data == null)
            return "";
        StringBuilder buffer = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                buffer.append('0');
            }
            buffer.append(hex);
        }
        return buffer.toString().toUpperCase();
    }
}
