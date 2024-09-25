package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mimps2Model {
	private Integer srNo;
	private String buyerName;
	private String seller;
	
	private Integer shares;
	private Double percentageShare;
}
