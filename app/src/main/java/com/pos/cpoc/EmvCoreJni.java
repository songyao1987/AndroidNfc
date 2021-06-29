package com.pos.cpoc;

import android.util.Log;

import com.pos.cpoc.emv.EmvApp;
import com.pos.cpoc.emv.EmvBlacklist;
import com.pos.cpoc.emv.EmvCapk;
import com.pos.cpoc.emv.EmvRevoc;
import com.pos.cpoc.emv.EmvTermParam;
import com.pos.cpoc.emv.EmvTransLog;
import com.pos.cpoc.emv.EmvTransParam;
import com.pos.cpoc.emv.OnEmvListener;

import java.lang.reflect.Method;

/**
 * @author eric.song
 * @date 2021/5/21 15:07
 */
public class EmvCoreJni {

    private static EmvCoreJni emvCoreJni;

    private static OnEmvListener emvListener;

    static {
        System.loadLibrary("EmvCoreJni");
    }

    private EmvCoreJni() {
    }

    private static void createClass() {
        if (emvCoreJni == null) {
            Class var0 = EmvCoreJni.class;
            synchronized(EmvCoreJni.class) {
                if (emvCoreJni == null) {
                    emvCoreJni = new EmvCoreJni();
                }
            }
        }

    }

    public static void getJni() {
        createClass();

        try {
            Class<?> cls = Class.forName("com.pos.cpoc.EmvHandler");
            Object obj = cls.newInstance();
            Method setFunc = cls.getMethod("setJni", EmvCoreJni.class);
            setFunc.invoke(obj, emvCoreJni);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public native String sdkEmvKernelVersion();

    public native void sdkEmvDelAllApp();

    public native void sdkEmvDelAllCapk();

    public native void sdkEmvDelAllRecvo();

    public native void sdkEmvDelAllBlacklist();

    public native int sdkEmvGetAppNum();

    public native int sdkEmvGetCapkNum();

    public native int sdkEmvGetRecvoNum();

    public native int sdkEmvGetBlacklistNum();

    public native int sdkEmvGetAppTLVList(int var1, byte[] var2, int[] var3);

    public native int sdkEmvGetCapkTLVList(int var1, byte[] var2, int[] var3);

    public native int sdkEmvAddAppTLVList(byte[] var1, int var2);

    public native int sdkEmvAddCapkTLVList(byte[] var1, int var2);

    public native int sdkEmvAddRevocTLVList(byte[] var1, int var2);

    public native int sdkEmvAddApp(EmvApp var1);

    public native int sdkEmvAddCapk(EmvCapk var1);

    public native int sdkEmvAddRevoc(EmvRevoc var1);

    public native int sdkEmvAddBlacklist(EmvBlacklist var1);

    public native int sdkEmvAppSelect();

    public native int sdkEmvAppInit();

    public native int sdkEmvReadAppData();

    public native int sdkEmvOfflineDataAuth();

    public native int sdkEmvProcessRestrictions();

    public native int sdkEmvCardholderVerification();

    public native int sdkEmvTerminalRiskManagement();

    public native int sdkEmvTerminalAndCardActionAnalysis(byte[] var1);

    public native int sdkEmvOnlineProcessing();

    public native int sdkEmvSetPinBlock(byte[] var1);

    public native int sdkEmvSeparateOnlineResp(byte[] var1, byte[] var2, int var3);

    public native int sdkEmvGetTransResult(byte[] var1);

    public native void sdkEmvCoreInit(EmvTermParam var1);

    public native void sdkEmvTransParamInit(EmvTransParam var1);

    public native int sdkEmvTrans(byte[] var1, byte[] var2, byte[] var3);

    public native int sdkEmvQTransPreProc(byte[] var1);

    public native int sdkEmvQTrans(byte[] var1, byte[] var2, byte[] var3);

    public native int sdkEmvPaypassTrans(byte[] var1);

    public native int sdkEmvBalanceQuery(int var1, byte[] var2);

    public native int sdkEmvReadPANProc(int var1, byte[] var2, String[] var3, String[] var4);

    public native int sdkEmvReadTransLog(int var1, byte[] var2, EmvTransLog[] var3);

    public native int sdkEmvGetTrack2AndPAN(String[] var1, String[] var2);

    public native int sdkEmvGetTLVData(int var1, byte[] var2, int[] var3);

    public native int sdkEmvSetTLVData(int var1, byte[] var2, int var3);

    public native int sdkEmvPackageTLV(int var1, byte[] var2, int var3, byte[] var4);

    public native int sdkEmvFindTLV(byte[] var1, int var2, int var3, byte[] var4, int[] var5);

    public void setOnEmvListener(OnEmvListener onEmvListener) {
        emvListener = onEmvListener;
    }

    public int sdkEmvOnSelApp(String[] appLabelList) {
        Log.d("Debug", "in sdkEmvOnSelApp");

        for(int i = 0; i < appLabelList.length; ++i) {
            Log.d("Debug", appLabelList[i]);
        }

        try {
            return emvListener.onSelApp(appLabelList);
        } catch (Exception var3) {
            var3.printStackTrace();
            return -7;
        }
    }

    public int sdkEmvOnConfirmCardNo(String cardNo) {
        Log.d("Debug", "in sdkEmvOnConfirmCardNo");

        try {
            return emvListener.onConfirmCardNo(cardNo);
        } catch (Exception var3) {
            var3.printStackTrace();
            return -7;
        }
    }

    public int sdkEmvOnCertVerify(int uiCertType, String certNo) {
        Log.d("Debug", "in sdkEmvOnCertVerify");

        try {
            return emvListener.onCertVerify(uiCertType, certNo);
        } catch (Exception var4) {
            var4.printStackTrace();
            return -7;
        }
    }

    public int sdkEmvOnInputPIN(byte ucPinType, byte[] pinBlock) {
        Log.d("Debug", "in sdkEmvOnInputPIN");

        try {
            return emvListener.onInputPIN(ucPinType);
        } catch (Exception var4) {
            var4.printStackTrace();
            return -7;
        }
    }

    public int sdkEmvOnlineProc() {
        Log.d("Debug", "in sdkEmvOnOnlineProc");

        try {
            return emvListener.onlineProc();
        } catch (Exception var2) {
            var2.printStackTrace();
            return -20;
        }
    }

    public byte[] sdkEmvOnExchangeApdu(byte[] send) {
        Log.d("Debug", "sdkEmvOnExchangeApdu");

        try {
            return emvListener.onExchangeApdu(send);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
