package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateModel {
	private Integer srNo;
	private String name;
	private String clientId;
	private Integer noOfShares;
	

}
