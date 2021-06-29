package com.pos.cpoc.emv;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author eric.song
 * @date 2021/5/21 15:13
 */
public class EmvTransParam {
    private byte transKernalType = 0;
    private byte terminalSupportIndicator = 0;
    private byte isForceOnline = 0;
    private byte isSimpleFlow = 0;
    private String readerTTQ = "26000080";
    private String transNo = "00000001";
    private String transDate = DateToStr(new Date(), "yyMMdd");
    private String transTime = DateToStr(new Date(), "HHmmss");
    private String amountAuth = "000000000001";
    private String amountOther = "000000000000";
    private byte transType;

    public EmvTransParam() {
    }

    public byte getTransKernalType() {
        return this.transKernalType;
    }

    public void setTransKernalType(byte transKernalType) {
        this.transKernalType = transKernalType;
    }

    public byte getTerminalSupportIndicator() {
        return this.terminalSupportIndicator;
    }

    public void setTerminalSupportIndicator(byte terminalSupportIndicator) {
        this.terminalSupportIndicator = terminalSupportIndicator;
    }

    public byte getIsForceOnline() {
        return this.isForceOnline;
    }

    public void setIsForceOnline(byte isForceOnline) {
        this.isForceOnline = isForceOnline;
    }

    public byte getIsSimpleFlow() {
        return this.isSimpleFlow;
    }

    public void setIsSimpleFlow(byte isSimpleFlow) {
        this.isSimpleFlow = isSimpleFlow;
    }

    public String getReaderTTQ() {
        return this.readerTTQ;
    }

    public void setReaderTTQ(String readerTTQ) {
        this.readerTTQ = readerTTQ;
    }

    public String getTransNo() {
        return this.transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransDate() {
        return this.transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return this.transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getAmountAuth() {
        return this.amountAuth;
    }

    public void setAmountAuth(String amountAuth) {
        this.amountAuth = amountAuth;
    }

    public String getAmountOther() {
        return this.amountOther;
    }

    public void setAmountOther(String amountOther) {
        this.amountOther = amountOther;
    }

    public byte getTransType() {
        return this.transType;
    }

    public void setTransType(byte transType) {
        this.transType = transType;
    }

    public static String DateToStr(Date date, String formatString) {
        String str = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            str = format.format(date);
        } catch (Exception var4) {
            str = "000000";
        }

        return str;
    }
}
