package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONObject;

import com.nseit.shareholder1.metadatamodel.MetadataPojo;
import com.vladmihalcea.hibernate.type.json.JsonBlobType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="SHARE_TRANSFER_MASTER")
@TypeDef(name = "jsonb", typeClass = JsonBlobType.class)
public class ShareTransferMaster implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name="METADATA")
//	@Lob
	@Type(type = "jsonb")
	private MetadataPojo metadata;
	
	@NotNull(message = "sellerClientId may not be null")
	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid")
	@Column(name="SELLER_CLIENT_ID")
	private String sellerClientId;
	
	@NotNull(message = "sellerName may not be null")
	@Column(name="SELLERNAME")
	private String sellerName;
	
	@NotNull(message = "buyerClientID may not be null")
	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid")
	@Column(name="BUYER_CLIENT_ID")
	private String buyerClientID;
	
	@NotNull(message = "buyerName may not be null")
	@Column(name="BUYERNAME")
	private String buyerName;
	
	@Column(name="PRICE_PER_SHARES")
	private Double pricePerShares;
	
	@Column(name="NO_OF_SHARES")
	private Integer noOfShares;
	
	@Column(name="AMOUNT")
	private Long amount;
	
	@Column(name="PERSON_CONCERT")
	private String personConcert;
	
	
	@Column(name="UUID")
	private Long uuid;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_ON", insertable = false, updatable = false)
	private String createdOn;
	
	@Column(name="DATE_OF_EXECUTION")
	private Timestamp dateOfExecution;
	
	@ManyToOne
	@JoinColumn(name = "BATCH_ID", referencedColumnName = "id")
//	@Column(name = "BATCH_ID")
	private BatchModel batchId;
	
	  @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
	
	
	
	

}
