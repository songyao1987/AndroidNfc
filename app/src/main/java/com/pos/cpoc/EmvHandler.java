package com.pos.cpoc;


import com.pos.cpoc.emv.EmvApp;
import com.pos.cpoc.emv.EmvBlacklist;
import com.pos.cpoc.emv.EmvCapk;
import com.pos.cpoc.emv.EmvRevoc;
import com.pos.cpoc.emv.EmvTermParam;
import com.pos.cpoc.emv.EmvTransLog;
import com.pos.cpoc.emv.EmvTransParam;
import com.pos.cpoc.emv.OnEmvListener;

import java.util.Arrays;

/**
 * @author eric.song
 * @date 2021/5/21 15:16
 */
public class EmvHandler {
    private byte cvmType = 0;
    private static EmvHandler evmHandler;
    static EmvCoreJni emvCoreJni;

    public byte getCvmType() {
        return this.cvmType;
    }

    private void setCvmType(byte cvmType) {
        this.cvmType = cvmType;
    }

    public EmvHandler() {
    }

    public static EmvHandler getInstance() {
        if (evmHandler == null) {
            Class var0 = EmvHandler.class;
            synchronized(EmvHandler.class) {
                if (evmHandler == null) {
                    evmHandler = new EmvHandler();
                    EmvCoreJni.getJni();
                }
            }
        }

        return evmHandler;
    }

    public void setJni(EmvCoreJni emvCoreJni1) {
        emvCoreJni = emvCoreJni1;
    }

    public String getKernelVersion() {
        return emvCoreJni.sdkEmvKernelVersion();
    }

    public void delAllApp() {
        emvCoreJni.sdkEmvDelAllApp();
    }

    public void delAllCapk() {
        emvCoreJni.sdkEmvDelAllCapk();
    }

    public void delAllRecvo() {
        emvCoreJni.sdkEmvDelAllRecvo();
    }

    public void delAllBlacklist() {
        emvCoreJni.sdkEmvDelAllBlacklist();
    }

    public int getAppNum() {
        return emvCoreJni.sdkEmvGetAppNum();
    }

    public int getCapkNum() {
        return emvCoreJni.sdkEmvGetCapkNum();
    }

    public int getRecvoNum() {
        return emvCoreJni.sdkEmvGetRecvoNum();
    }

    public int getBlacklistNum() {
        return emvCoreJni.sdkEmvGetBlacklistNum();
    }

    public byte[] getApp(int index) {
        byte[] pucAid = new byte[300];
        int[] pucLen = new int[1];
        int iRet = emvCoreJni.sdkEmvGetAppTLVList(index, pucAid, pucLen);
        return iRet < 0 ? null : Arrays.copyOf(pucAid, pucLen[0]);
    }

    public byte[] getCapk(int index) {
        byte[] pucCapk = new byte[300];
        int[] puiLen = new int[1];
        int iRet = emvCoreJni.sdkEmvGetCapkTLVList(index, pucCapk, puiLen);
        return iRet < 0 ? null : Arrays.copyOf(pucCapk, puiLen[0]);
    }

    public int addApp(byte[] aid) {
        return emvCoreJni.sdkEmvAddAppTLVList(aid, aid.length);
    }

    public int addCapk(byte[] capk) {
        return emvCoreJni.sdkEmvAddCapkTLVList(capk, capk.length);
    }

    public int addRevoc(byte[] revoc) {
        return emvCoreJni.sdkEmvAddRevocTLVList(revoc, revoc.length);
    }

    public int addApp(EmvApp emvApp) {
        return emvCoreJni.sdkEmvAddApp(emvApp);
    }

    public int addCapk(EmvCapk emvCapk) {
        return emvCoreJni.sdkEmvAddCapk(emvCapk);
    }

    public int addRevoc(EmvRevoc emvRevoc) {
        return emvCoreJni.sdkEmvAddRevoc(emvRevoc);
    }

    public int addBlacklist(EmvBlacklist emvBlacklist) {
        return emvCoreJni.sdkEmvAddBlacklist(emvBlacklist);
    }

    public void kernelInit(EmvTermParam emvTermparam) {
        emvCoreJni.sdkEmvCoreInit(emvTermparam);
    }

    public void transParamInit(EmvTransParam emvTransParam) {
        emvCoreJni.sdkEmvTransParamInit(emvTransParam);
    }

    public void setPinBlock(byte[] pinBlock) {
        emvCoreJni.sdkEmvSetPinBlock(pinBlock);
    }

    public int separateOnlineResp(byte[] authRespCode, byte[] issuerResp, int issuerRespLen) {
        return emvCoreJni.sdkEmvSeparateOnlineResp(authRespCode, issuerResp, issuerRespLen);
    }

    public int emvTrans(EmvTransParam emvTransParam, OnEmvListener emvListener, byte[] isEcTrans, byte[] balance, byte[] transResult) {
        emvCoreJni.setOnEmvListener(emvListener);
        emvCoreJni.sdkEmvTransParamInit(emvTransParam);
        if (emvTransParam.getTransKernalType() == 0) {
            return emvCoreJni.sdkEmvTrans(isEcTrans, balance, transResult);
        } else {
            byte[] cvmType = new byte[1];
            int iRet = emvCoreJni.sdkEmvQTrans(balance, transResult, cvmType);
            this.setCvmType(cvmType[0]);
            if (emvTransParam.getIsSimpleFlow() == 1) {
                return iRet;
            } else if (transResult[0] == -128) {
                isEcTrans[0] = 0;
                iRet = emvListener.onInputPIN((byte)0);
                if (iRet != 0 && iRet != -32) {
                    transResult[0] = 0;
                    return iRet;
                } else {
                    int iOnlineRet = emvListener.onlineProc();
                    if (iOnlineRet != 0) {
                        transResult[0] = 0;
                        return iOnlineRet;
                    } else {
                        transResult[0] = 64;
                        return 0;
                    }
                }
            } else {
                isEcTrans[0] = 1;
                return iRet;
            }
        }
    }

    public int qTransProProc(byte[] amountAuth) {
        return emvCoreJni.sdkEmvQTransPreProc(amountAuth);
    }

    public int qTrans(byte[] balance, byte[] transResult, byte[] cvmType) {
        return emvCoreJni.sdkEmvQTrans(balance, transResult, cvmType);
    }

    public int qTrans(EmvTransParam emvTransParam, OnEmvListener emvListener, byte[] balance, byte[] transResult, byte[] cvmType) {
        emvCoreJni.setOnEmvListener(emvListener);
        emvCoreJni.sdkEmvTransParamInit(emvTransParam);
        return emvCoreJni.sdkEmvQTrans(balance, transResult, cvmType);
    }

    public int paypassTrans(byte[] transResult) {
        return emvCoreJni.sdkEmvPaypassTrans(transResult);
    }

    public int paypassTrans(EmvTransParam emvTransParam, OnEmvListener emvListener, byte[] transResult) {
        emvCoreJni.setOnEmvListener(emvListener);
        emvCoreJni.sdkEmvTransParamInit(emvTransParam);
        return emvCoreJni.sdkEmvPaypassTrans(transResult);
    }

    public int balanceQuery(int transKernalType, OnEmvListener emvListener, byte[] balance) {
        emvCoreJni.setOnEmvListener(emvListener);
        return emvCoreJni.sdkEmvBalanceQuery(transKernalType, balance);
    }

    public int readPANProc(int transKernalType, OnEmvListener emvListener, byte[] transDate, String[] track2, String[] pan) {
        emvCoreJni.setOnEmvListener(emvListener);
        return emvCoreJni.sdkEmvReadPANProc(transKernalType, transDate, track2, pan);
    }

    public int readTransLog(int transKernalType, OnEmvListener emvListener, byte[] transLogNum, EmvTransLog[] emvTransLog) {
        return emvCoreJni.sdkEmvReadTransLog(transKernalType, transLogNum, emvTransLog);
    }

    public int getTrack2AndPAN(String[] track2, String[] pan) {
        return emvCoreJni.sdkEmvGetTrack2AndPAN(track2, pan);
    }

    public byte[] getTlvData(int tag) {
        byte[] pucDataOut = new byte[300];
        int[] puiLen = new int[1];
        int iRet = emvCoreJni.sdkEmvGetTLVData(tag, pucDataOut, puiLen);
        if (iRet < 0) {
            return null;
        } else {
            return puiLen[0] == 0 ? null : Arrays.copyOf(pucDataOut, puiLen[0]);
        }
    }

    public int setTlvData(int tag, byte[] value) {
        return emvCoreJni.sdkEmvSetTLVData(tag, value, value.length);
    }

    public byte[] packageTlv(int tag, byte[] value) {
        byte[] pucOutTLVData = new byte[300];
        int iRet = emvCoreJni.sdkEmvPackageTLV(tag, value, value.length, pucOutTLVData);
        return iRet <= 0 ? null : Arrays.copyOf(pucOutTLVData, iRet);
    }

    public byte[] packageTlvFormKernel(int tag) {
        byte[] ucValue = this.getTlvData(tag);
        return ucValue == null ? null : this.packageTlv(tag, ucValue);
    }

    public byte[] packageTlvList(int[] tags) {
        StringBuffer sb = new StringBuffer();
        int[] var6 = tags;
        int var5 = tags.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            int i = var6[var4];
            byte[] res = this.packageTlvFormKernel(i);
            if (res != null) {
                sb.append(this.bytesToHexString(res));
            }
        }

        return this.hexStringToBytes(sb.toString());
    }

    public byte[] findTagValue(byte[] tlvList, int findTag) {
        byte[] pucDataOut = new byte[300];
        int[] puiDataOutLen = new int[1];
        int iRet = emvCoreJni.sdkEmvFindTLV(tlvList, tlvList.length, findTag, pucDataOut, puiDataOutLen);
        return iRet != 0 && puiDataOutLen[0] == 0 ? null : Arrays.copyOf(pucDataOut, puiDataOutLen[0]);
    }

    public byte[] hexStringToBytes(String src) {
        src = src.toUpperCase();
        int length = src.length() / 2;
        char[] hexChars = src.toCharArray();
        byte[] d = new byte[length];

        for(int i = 0; i < length; ++i) {
            d[i] = (byte)(charToByte(hexChars[i * 2]) << 4 | charToByte(hexChars[i * 2 + 1]));
        }

        return d;
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");

        for(int i = 0; i < src.length; ++i) {
            int v = src[i] & 255;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }
}
