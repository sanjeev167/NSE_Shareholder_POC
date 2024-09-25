package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import com.nseit.shareholder1.modelInterfaces.GetAllBrokerDetailsInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllBrokerDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid",groups = {GetAllBrokerDetailsInterface.class})
	private String clientId;
	
}
