package com.pos.cpoc.config;

/**
 * EMVApp配置文件操作
 * 
 * @author zhengwei
 *
 */
public class TerminalConfig extends XmlUtil<Terminal> {
	private static final String file = "/sdcard/emvterminal.xml";

	public TerminalConfig() {
		super(Terminal.class, file);		
	}

	@Override
	Terminal setDefaultConfig() {
		return null;
	}

}
