package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class NetWorthCibilScore implements Serializable {
	private List<NetWorth> netWorthList;

	public List<NetWorth> getNetWorthList() {
		return netWorthList;
	}

	public void setNetWorthList(List<NetWorth> netWorthList) {
		this.netWorthList = netWorthList;
	}

}
