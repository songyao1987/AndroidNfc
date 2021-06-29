package com.pos.cpoc.config;

/**
 * EMVRevoc配置文件操作
 * 
 * @author zhengwei
 *
 */
public class EMVTransConfig extends XmlUtil<EmvTransRecs> {
	public static final String file = "/sdcard/emvtransrecs.xml";

	public EMVTransConfig() {
		super(EmvTransRecs.class, file);
	}

	@Override
	EmvTransRecs setDefaultConfig() {
		return null;
	}

}
