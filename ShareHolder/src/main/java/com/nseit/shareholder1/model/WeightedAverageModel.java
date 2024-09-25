package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeightedAverageModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "year is required")
	private Integer year;
	
	@NotEmpty(message = "month is required")
	private Integer month;
	
	@NotEmpty(message = "period is required")
	private Integer period;

}
