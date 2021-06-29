package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:10
 */
public class EmvApp {
    private String aid;
    private byte selFlag;
    private byte targetPer;
    private byte maxTargetPer;
    private int floorLimit;
    private byte onLinePINFlag;
    private int threshold;
    private String tacDefault;
    private String tacDenial;
    private String tacOnline;
    private String dDOL;
    private String tDOL;
    private String version;
    private String clTransLimit;
    private String clOfflineLimit;
    private String clCVMLimit;
    private String ecTTLVal;
    private String acquierId;
    private String merchCateCode;
    private String merchId;
    private String merName;
    private String termId;
    private String transCurrCode;
    private byte transCurrExp;
    private String transRefCode;
    private byte transRefExp;
    private String terRisk;

    public EmvApp() {
    }

    public EmvApp(String aid, byte selFlag, byte targetPer, byte maxTargetPer, int floorLimit, byte onLinePINFlag, int threshold, String tacDefault, String tacDenial, String tacOnline, String dDOL, String tDOL, String version, String clTransLimit, String clOfflineLimit, String clCVMLimit, String ecTTLVal, String acquierId, String merchCateCode, String merchId, String merName, String termId, String transCurrCode, byte transCurrExp, String transRefCode, byte transRefExp, String terRisk) {
        this.aid = aid;
        this.selFlag = selFlag;
        this.targetPer = targetPer;
        this.maxTargetPer = maxTargetPer;
        this.floorLimit = floorLimit;
        this.onLinePINFlag = onLinePINFlag;
        this.threshold = threshold;
        this.tacDefault = tacDefault;
        this.tacDenial = tacDenial;
        this.tacOnline = tacOnline;
        this.dDOL = dDOL;
        this.tDOL = tDOL;
        this.version = version;
        this.clTransLimit = clTransLimit;
        this.clOfflineLimit = clOfflineLimit;
        this.clCVMLimit = clCVMLimit;
        this.ecTTLVal = ecTTLVal;
        this.acquierId = acquierId;
        this.merchCateCode = merchCateCode;
        this.merchId = merchId;
        this.merName = merName;
        this.termId = termId;
        this.transCurrCode = transCurrCode;
        this.transCurrExp = transCurrExp;
        this.transRefCode = transRefCode;
        this.transRefExp = transRefExp;
        this.terRisk = terRisk;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public byte getSelFlag() {
        return this.selFlag;
    }

    public void setSelFlag(byte selFlag) {
        this.selFlag = selFlag;
    }

    public byte getTargetPer() {
        return this.targetPer;
    }

    public void setTargetPer(byte targetPer) {
        this.targetPer = targetPer;
    }

    public byte getMaxTargetPer() {
        return this.maxTargetPer;
    }

    public void setMaxTargetPer(byte maxTargetPer) {
        this.maxTargetPer = maxTargetPer;
    }

    public int getFloorLimit() {
        return this.floorLimit;
    }

    public void setFloorLimit(int floorLimit) {
        this.floorLimit = floorLimit;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getTacDenial() {
        return this.tacDenial;
    }

    public void setTacDenial(String tacDenial) {
        this.tacDenial = tacDenial;
    }

    public String getTacOnline() {
        return this.tacOnline;
    }

    public void setTacOnline(String tacOnline) {
        this.tacOnline = tacOnline;
    }

    public String getTacDefault() {
        return this.tacDefault;
    }

    public void setTacDefault(String tacDefault) {
        this.tacDefault = tacDefault;
    }

    public String getAcquierId() {
        return this.acquierId;
    }

    public void setAcquierId(String acquierId) {
        this.acquierId = acquierId;
    }

    public String getdDOL() {
        return this.dDOL;
    }

    public void setdDOL(String dDOL) {
        this.dDOL = dDOL;
    }

    public String gettDOL() {
        return this.tDOL;
    }

    public void settDOL(String tDOL) {
        this.tDOL = tDOL;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchCateCode() {
        return this.merchCateCode;
    }

    public void setMerchCateCode(String merchCateCode) {
        this.merchCateCode = merchCateCode;
    }

    public String getMerchId() {
        return this.merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getMerName() {
        return this.merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getTermId() {
        return this.termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTransCurrCode() {
        return this.transCurrCode;
    }

    public void setTransCurrCode(String transCurrCode) {
        this.transCurrCode = transCurrCode;
    }

    public byte getTransCurrExp() {
        return this.transCurrExp;
    }

    public void setTransCurrExp(byte transCurrExp) {
        this.transCurrExp = transCurrExp;
    }

    public String getTransRefCode() {
        return this.transRefCode;
    }

    public void setTransRefCode(String transRefCode) {
        this.transRefCode = transRefCode;
    }

    public byte getTransRefExp() {
        return this.transRefExp;
    }

    public void setTransRefExp(byte transRefExp) {
        this.transRefExp = transRefExp;
    }

    public String getTerRisk() {
        return this.terRisk;
    }

    public void setTerRisk(String terRisk) {
        this.terRisk = terRisk;
    }

    public String getEcTTLVal() {
        return this.ecTTLVal;
    }

    public void setEcTTLVal(String ecTTLVal) {
        this.ecTTLVal = ecTTLVal;
    }

    public String getClOfflineLimit() {
        return this.clOfflineLimit;
    }

    public void setClOfflineLimit(String clOfflineLimit) {
        this.clOfflineLimit = clOfflineLimit;
    }

    public String getClTransLimit() {
        return this.clTransLimit;
    }

    public void setClTransLimit(String clTransLimit) {
        this.clTransLimit = clTransLimit;
    }

    public String getClCVMLimit() {
        return this.clCVMLimit;
    }

    public void setClCVMLimit(String clCVMLimit) {
        this.clCVMLimit = clCVMLimit;
    }

    public byte getOnLinePINFlag() {
        return this.onLinePINFlag;
    }

    public void setOnLinePINFlag(byte onLinePINFlag) {
        this.onLinePINFlag = onLinePINFlag;
    }
}
