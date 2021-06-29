
package com.pos.cpoc.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("terminal")
public class Terminal {
	@XStreamAlias("dataCapture")
	private String dataCapture ="00";// 00-批上送 01-联机
	@XStreamAlias("termCountryCode")
	private String termCountryCode = "0156";// 终端国家代码
	@XStreamAlias("transDate")
	private String transDate = "20180101";// 交易日期
	@XStreamAlias("transTime")
	private String transTime = "000000";// 交易时间

	public String getDataCapture() {
		return dataCapture;
	}

	public void setDataCapture(String dataCapture) {
		this.dataCapture = dataCapture;
	}


	public String getTermCountryCode() {
		return termCountryCode;
	}

	public void setTermCountryCode(String termCountryCode) {
		this.termCountryCode = termCountryCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

}