package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProposedChangeRequest implements Serializable{
	
	@NotEmpty(message = "username is required")
    private String username;
	
	@NotEmpty(message = "ProposedChange is required")
	private String proposedChange;

}
