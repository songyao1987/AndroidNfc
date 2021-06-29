package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:12
 */
public class EmvTransLog {
    private String cardAMT;
    private String otherAMT;
    private String cardATC;
    private byte transType;
    private String termCountry;
    private String transCurrency;
    private String transDate;
    private String transTime;
    private String merchantName;

    public EmvTransLog() {
    }

    public String getCardAMT() {
        return this.cardAMT;
    }

    public void setCardAMT(String cardAMT) {
        this.cardAMT = cardAMT;
    }

    public String getOtherAMT() {
        return this.otherAMT;
    }

    public void setOtherAMT(String otherAMT) {
        this.otherAMT = otherAMT;
    }

    public String getCardATC() {
        return this.cardATC;
    }

    public void setCardATC(String cardATC) {
        this.cardATC = cardATC;
    }

    public byte getTransType() {
        return this.transType;
    }

    public void setTransType(byte transType) {
        this.transType = transType;
    }

    public String getTermCountry() {
        return this.termCountry;
    }

    public void setTermCountry(String termCountry) {
        this.termCountry = termCountry;
    }

    public String getTransCurrency() {
        return this.transCurrency;
    }

    public void setTransCurrency(String transCurrency) {
        this.transCurrency = transCurrency;
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

    public String getMerchantName() {
        return this.merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
