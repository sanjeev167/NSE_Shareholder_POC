package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareTansferApprovalRejectSubmitRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@NotNull(message = "UUID may not be null")
	private Long uuid;
	
	private String comments;

}
