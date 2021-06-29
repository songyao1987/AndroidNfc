package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:10
 */
public class EmvBlacklist {
    private String pan;
    private byte panSeq;

    public EmvBlacklist() {
    }

    public EmvBlacklist(String pan, byte panSeq) {
        this.pan = pan;
        this.panSeq = panSeq;
    }

    public String getPan() {
        return this.pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public byte getPanSeq() {
        return this.panSeq;
    }

    public void setPanSeq(byte panSeq) {
        this.panSeq = panSeq;
    }
}
