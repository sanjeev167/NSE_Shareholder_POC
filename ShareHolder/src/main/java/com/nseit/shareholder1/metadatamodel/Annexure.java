package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class Annexure implements Serializable{
   private List<File> annexure3List;

public List<File> getAnnexure3List() {
	return annexure3List;
}

public void setAnnexure3List(List<File> annexure3List) {
	this.annexure3List = annexure3List;
}
}
