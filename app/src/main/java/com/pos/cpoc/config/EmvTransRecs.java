
package com.pos.cpoc.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("transrecs")
public class EmvTransRecs {
	@XStreamImplicit(itemFieldName = "transrec")
	private List<EmvTransRec> transrec;

	public List<EmvTransRec> getTransRecList() {
		return transrec;
	}

	public void setTransRecList(List<EmvTransRec> transrec) {
		this.transrec = transrec;
	}
}