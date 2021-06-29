
package com.pos.cpoc.config;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * EMV Revoc
 * 
 * @author xmen.lin@gmail.com
 */
@XStreamAlias("transrec")
public class EmvTransRec {
	@XStreamAlias("icField")
	private String icField; //ic卡数据域

	public EmvTransRec() {
	}

	public EmvTransRec(String icField) {
		this.icField = icField;

	}

	public String getIcField() {
		return icField;
	}

	public void setIcField(String icField) {
		this.icField = icField;
	}

}