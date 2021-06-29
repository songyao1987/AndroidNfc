package com.pos.cpoc.utils;

import java.math.BigDecimal;

/**
 * @author eric.song
 * @date 2021/5/21 13:41
 */
public class AmountUtil {

    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    public static String changeFen2Yuan(Long amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("Fomat error");
        }

        int flag = 0;
        String amString = amount.toString();
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(intString.substring(intString.length() - i,
                        intString.length() - i + 1));
            }
            result.reverse().append(".")
                    .append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    public static String changeFen2Yuan(String amount) {
        if(null==amount){
            return "";
        }
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            return "";
        }
        return BigDecimal.valueOf(Long.valueOf(amount))
                .divide(new BigDecimal(100)).toString();
    }

    public static String changeY2F(Long amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100))
                .toString();
    }

    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
        // 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
                    ".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
                    ".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
                    ".", "") + "00");
        }
        return amLong.toString();
    }

    public static double sub(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();

    }

    public static String add(String v1, String v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();

    }

    public static int compareTo(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);

    }

    public static String change12(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        System.out.println("length" + length);
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
                    ".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
                    ".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
                    ".", "") + "00");
        }
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 12 - amLong.toString().length(); i++) {
            prefix.append("0");
        }
        return prefix.append(amLong.toString()).toString();
    }
}
