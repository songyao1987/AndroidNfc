package com.pos.cpoc.emv;

/**
 * @author eric.song
 * @date 2021/5/21 15:09
 */
public interface OnEmvListener {
    byte[] onExchangeApdu(byte[] var1);

    int onRequestInputAmount();

    int onSelApp(String[] var1);

    int onConfirmEcSwitch();

    int onConfirmCardNo(String var1);

    int onInputPIN(byte var1);

    int onCertVerify(int var1, String var2);

    int onlineProc();
}
