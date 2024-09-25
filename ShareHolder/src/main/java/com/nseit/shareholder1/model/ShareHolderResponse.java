package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareHolderResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5656174699687539383L;
	/**
	 * 
	 */
	
	private Date timeStamp; 
	private String versionNo;
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Object data;
	private String mstrCode;
	private String mstrDescr;
	private Object error;

}
