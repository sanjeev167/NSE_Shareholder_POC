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
@Table(name = "CAF_GENERATION_MASTER")
public class CafGenerationModel implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "CDSL")
	private String cdsl;
	
	@Column(name = "NSDL")
	private String nsdl;
	
	@Column(name = "BENEFICIARY_DETIALS")
	private String beneficiaryDetials;
	
	@Column(name = "SEND_TO_RTA")
	private Timestamp sendToRta;
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	  @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
}
