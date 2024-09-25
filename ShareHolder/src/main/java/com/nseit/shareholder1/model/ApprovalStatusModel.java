package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalStatusModel {
	private String srNo;
	private String name;
	
	private Integer shares;
}
