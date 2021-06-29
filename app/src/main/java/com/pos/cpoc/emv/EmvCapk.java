package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:11
 */
public class EmvCapk {
    private String rid;
    private byte keyID;
    private byte hashIndicator;
    private byte capkIndicator;
    private String modul;
    private String exponent;
    private String expDate;
    private String checkSum;

    public EmvCapk() {
    }

    public EmvCapk(String rid, byte keyID, byte hashIndicator, byte capkIndicator, String modul, String exponent, String expDate, String checkSum) {
        this.rid = rid;
        this.keyID = keyID;
        this.hashIndicator = hashIndicator;
        this.capkIndicator = capkIndicator;
        this.modul = modul;
        this.exponent = exponent;
        this.expDate = expDate;
        this.checkSum = checkSum;
    }

    public String getRID() {
        return this.rid;
    }

    public void setRID(String rid) {
        this.rid = rid;
    }

    public byte getKeyID() {
        return this.keyID;
    }

    public void setKeyID(byte keyID) {
        this.keyID = keyID;
    }

    public byte getHashIndicator() {
        return this.hashIndicator;
    }

    public void setHashIndicator(byte hashIndicator) {
        this.hashIndicator = hashIndicator;
    }

    public byte getCapkIndicator() {
        return this.capkIndicator;
    }

    public void setCapkIndicator(byte capkIndicator) {
        this.capkIndicator = capkIndicator;
    }

    public String getModul() {
        return this.modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getExponent() {
        return this.exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public String getExpDate() {
        return this.expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCheckSum() {
        return this.checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}
