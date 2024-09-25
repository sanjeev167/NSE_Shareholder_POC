package com.nseit.shareholder1.model;

import java.io.Serializable;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareTransferSearchRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String sellerClientId;
	
	private String sellerName;
	
	private String buyerClientID;
	
	private String buyerName;
	
	private Long uuid;
	
	private String status;
	
	private Integer pageNumber;
	
	private Integer pageSize;

}
