package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:11
 */
public class EmvRevoc {
    private String rid;
    private byte index;
    private String certSn;

    public EmvRevoc() {
    }

    public EmvRevoc(String rid, byte index, String certSn) {
        this.rid = rid;
        this.index = index;
        this.certSn = certSn;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public byte getIndex() {
        return this.index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    public String getCertSn() {
        return this.certSn;
    }

    public void setCertSn(String certSn) {
        this.certSn = certSn;
    }
}
