package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProposedShareHoldingLimitRequest implements Serializable{
	@NotEmpty(message = "category is required")
	private String category;
	
	@NotNull(message = "proposedChange is required")
	private Float proposedChange;
}
