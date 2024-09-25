package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.nseit.shareholder1.modelInterfaces.CheckUserIdInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "uuid is required")
	List<Integer> uuid = new ArrayList<Integer>();

}
