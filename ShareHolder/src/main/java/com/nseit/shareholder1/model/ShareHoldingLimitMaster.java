package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SHAREHOLDING_LIMIT_MASTER")
public class ShareHoldingLimitMaster implements Serializable, Cloneable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "TOTAL_HOLDING_LIMIT")
	private Float totalHoldingLimit;
	
	@Column(name = "PROPOSED_CHANGE")
	private Float proposedChange;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "ACCEPT")
	private String accept;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name="MODIFIED_ON")
	private Timestamp modifiedOn;
	
	 @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
	

}
