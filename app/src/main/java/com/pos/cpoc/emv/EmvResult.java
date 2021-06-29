package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:39
 */
public class EmvResult {
    public static final int EMV_OK = 0;
    public static final int ICC_CMD_ERR = -1;
    public static final int EMV_PARAM_ERR = -2;
    public static final int ICC_BLOCK = -3;
    public static final int ICC_RSP_ERR = -4;
    public static final int EMV_APP_BLOCK = -5;
    public static final int EMV_NO_APP = -6;
    public static final int EMV_USER_CANCEL = -7;
    public static final int EMV_TIME_OUT = -8;
    public static final int EMV_DATA_ERR = -9;
    public static final int EMV_NOT_ACCEPT = -10;
    public static final int EMV_KEY_EXP = -12;
    public static final int EMV_DATETIME_ERR = -13;
    public static final int EMV_FILE_ERR = -14;
    public static final int EMV_SUM_ERR = -15;
    public static final int EMV_NOT_FOUND = -16;
    public static final int EMV_DATA_AUTH_FAIL = -17;
    public static final int EMV_NOT_MATCH = -18;
    public static final int EMV_NO_TRANS_LOG = -19;
    public static final int EMV_ONLINE_FAILED = -20;
    public static final int EMV_NOT_ORG_ICC = -21;
    public static final int ICC_RSP_6985 = -22;
    public static final int EMV_EXCP_FILE = -23;
    public static final int EMV_USE_CONTACT = -24;
    public static final int EMV_CARD_EXPIRED = -25;
    public static final int EMV_TERMINATE = -26;
    public static final int EMV_BALANCE_ERR = -27;
    public static final int EMV_NOT_PAY = -28;
    public static final int EMV_ALREADY_PAY = -29;
    public static final int EMV_BALANCE_NOT_ENOUGH = -30;
    public static final int EMV_DECLINE = -31;
    public static final int EMV_NO_PASSWORD = -32;
    public static final int EMV_FANGBA = -33;
    public static final int EMV_NO_PINPAD_OR_ERR = -34;
    public static final int EMV_NOT_QPBOC = -35;
    public static final int EMV_NOT_SUPPORT = -36;
    public static final int ICC_RSP_6986 = -37;
    public static final int EMV_ONLINE_NORESP_MAC_OR_RECV_ERR = -38;
    public static final int EMV_NO_ONLINE = -39;
    public static final int EMV_ONLINE_RESP_AAC = -40;
    public static final int EMV_PARSING_ERROR = -73;

    public EmvResult() {
    }
}
