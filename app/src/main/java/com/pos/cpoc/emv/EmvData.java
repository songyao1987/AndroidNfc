package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:37
 */
public class EmvData {
    public static final int BUF_LEN = 300;
    public static final byte KERNAL_EMV_PBOC = 0;
    public static final byte KERNAL_CONTACTLESS_ENTRY_POINT = 1;
    public static final byte KERNAL_CONFIG_UNIONPAY = 0;
    public static final byte KERNAL_CONFIG_EMV = 1;
    public static final byte KERNAL_CONFIG_PBOC = 2;
    public static final byte KERNAL_CONFIG_QPBOC = 3;
    public static final byte KERNAL_CONFIG_QUICS = 4;
    public static final byte KERNAL_CONFIG_PAYWARE = 5;
    public static final byte KERNAL_CONFIG_PAYPASS = 6;
    public static final byte RD_CVM_NO = 0;
    public static final byte RD_CVM_ONLINE_PIN = -128;
    public static final byte RD_CVM_SIG = 64;
    public static final byte APPROVE_M = 64;
    public static final byte DECLINE_M = 0;
    public static final byte ONLINE_M = -128;
    public static final byte ONLINE_ENCIPHERED_PIN = 0;
    public static final byte OFFLINE_PLAINTEXT_PIN = 1;
    public static final byte OFFLINE_ENCIPHERED_PIN = 2;
    public static final byte OPTION_CARDHOLDER_NOT_PERFORM = 1;
    public static final byte OPTION_OFFLINE_DATA_AUTH_PREFORM = 2;

    public EmvData() {
    }
}
