package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

//Category details for Share Transfer Request Details
public class CategoryDetails implements Serializable{
	
    private List<CategoryTable> categoryTable;
    
    private String role;
    
    private String name;
    
    private String dpClientId;
    
    private String pan;
    
    private File certifiedPanCard;
    
    private File annexure1;
    
    private File annexure2;

	public List<CategoryTable> getCategoryTable() {
		return categoryTable;
	}

	public void setCategoryTable(List<CategoryTable> categoryTable) {
		this.categoryTable = categoryTable;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDpClientId() {
		return dpClientId;
	}

	public void setDpClientId(String dpClientId) {
		this.dpClientId = dpClientId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public File getCertifiedPanCard() {
		return certifiedPanCard;
	}

	public void setCertifiedPanCard(File certifiedPanCard) {
		this.certifiedPanCard = certifiedPanCard;
	}

	public File getAnnexure1() {
		return annexure1;
	}

	public void setAnnexure1(File annexure1) {
		this.annexure1 = annexure1;
	}

	public File getAnnexure2() {
		return annexure2;
	}

	public void setAnnexure2(File annexure2) {
		this.annexure2 = annexure2;
	}

	
    
    
    


	


}
