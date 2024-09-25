package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ForeignResidentCalModel {

	private String typeOfStatus;
	private Long totalShares;
	private Double percentageShares;
}
