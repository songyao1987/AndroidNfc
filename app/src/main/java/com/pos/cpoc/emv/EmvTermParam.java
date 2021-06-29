package com.pos.cpoc.emv;

import android.os.Environment;

/**
 * @author eric.song
 * @date 2021/5/21 15:12
 */
public class EmvTermParam {
    public static String ifd = "12345678";
    public static String terminalCountry = "0156";
    public static byte termType = 34;
    public static String termCapa = "E0E9C8";
    public static String addTermCapa = "6000F0A001";
    public static String merchantNameLocation = "CPOCS";
    public static String merchantCode = "3031";
    public static String merchantID = "123456789012345";
    public static String acquirerID = "1234567890";
    public static String termID = "12345678";
    public static byte tranRefCurrExp = 2;
    public static String tranRefCurr = "0156";
    public static byte tranCurrExp = 2;
    public static String tranCurrCode = "0156";
    public static byte hostType = 0;
    public static byte termSMSupportIndicator = 0;
    public static byte termFLmtFlg = 1;
    public static byte rfTxnLmtFlg = 1;
    public static byte rfFLmtFlg = 1;
    public static byte rfCVMLmtFlg = 1;
    public static byte rfStatusCheckFlg = 0;
    public static byte rfZeroAmtNoAllowed = 1;
    public static byte printfDebugInfo = 1;
    public static byte useFangba = 0;
    public static byte emvTest = 0;
    public static byte useCallBackApdu = 0;
    public static String emvParamFilePath = Environment.getExternalStorageDirectory().getPath() + "/emv/";

    public EmvTermParam() {
    }
}
