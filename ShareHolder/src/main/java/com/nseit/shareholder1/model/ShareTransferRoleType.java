package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="SHARE_TRANSFER_WORKFLOW")
public class ShareTransferRoleType implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name="MAKER")
	private String maker;
	
	@Column(name="CHECKER")
	private String checker;
	
	@Column(name="BATCH_CREATED")
	private String batchCreated;
	
	@Column(name="CREATED_BY")
	private String createdby;
	
	@Column(name="UUID")
	private Long uuid;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMENTS")
	private String comment;
	
//	private Long shareTransferId;
	
	@ManyToOne(targetEntity = ShareTransferMaster.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SHARETRANSFER_ID", referencedColumnName = "ID")
	private ShareTransferMaster shareTransferId;
	
	@Column(name="CREATED_ON", insertable = false, updatable = false)
	private Timestamp createdOn;
	
	 @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
	
}
