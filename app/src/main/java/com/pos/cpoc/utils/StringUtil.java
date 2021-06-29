package com.pos.cpoc.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author eric.song
 * @date 2021/5/21 13:22
 */
public class StringUtil {

    public static boolean isNullWithTrim(String str) {
        return str == null || str.trim().equals("")||str.trim().equals("null");
    }

    public static String getSecurityNum(String cardNo, int prefix, int suffix) {
        StringBuffer cardNoBuffer = new StringBuffer();
        int len = prefix + suffix;
        if ( cardNo.length() > len) {
            cardNoBuffer.append(cardNo.substring(0, prefix));
            for (int i = 0; i < cardNo.length() - len; i++) {
                cardNoBuffer.append("*");
            }
            cardNoBuffer.append(cardNo.substring(cardNo.length() - suffix, cardNo.length()));
        }
        return cardNoBuffer.toString();
    }

    public static String TwoWei(double s){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(s);
    }

    public static String TwoWei(String amount){
        String str ="000000000000";
        String str_m =str.substring(0, 12-amount.length())+amount;
        StringBuffer sb1 = new StringBuffer(str_m);
        sb1 = sb1.insert(sb1.length() - 2, ".");
        return sb1.toString();
    }

    public static String StringPattern(String date, String oldPattern,
                                       String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);
        Date d = null;
        try {
            d = sdf1.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf2.format(d);
    }
}
