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
public class PacEntityProposedChange  implements Serializable{
	
	@NotEmpty(message = "GroupId is required")
	private String groupId;
	@NotEmpty(message = "proposedChange is required")
	private String proposedChange;
}
