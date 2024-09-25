package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import com.nseit.shareholder1.modelInterfaces.LoginInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LoginRequest implements Serializable {
	
	@NotEmpty(message = "username number is required")
	private String username;
	
	@NotEmpty(message = "password number is required")
	private String password;
//	private String apiKey;
	private boolean istokenExist;
}
