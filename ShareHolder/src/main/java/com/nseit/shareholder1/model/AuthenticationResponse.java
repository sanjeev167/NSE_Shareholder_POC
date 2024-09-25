package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AuthenticationResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5656174699687539383L;
	/**
	 * 
	 */
	
	private Date timeStamp; 
	private String versionNo;
	private String token;
	private String mstrCode;
	private String mstrDescr;
}
