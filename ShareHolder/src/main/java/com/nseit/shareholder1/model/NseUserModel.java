package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.nseit.shareholder1.modelInterfaces.AddNseUserInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "NSE_USER")
public class NseUserModel implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "username may not be null", groups = { AddNseUserInterface.class })
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "FULL_NAME")
	private String full_name;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "KEYCLOAK_ID")
	private String keycloak_id;

	@NotEmpty(message = "role may not be null", groups = { AddNseUserInterface.class })
	@Column(name = "ROLE")
	private String role;

	@Column(name = "PROPOSED_CHANGE")
	private String proposed_change;

	@Column(name = "CREATED_BY")
	private String created_by;

	@Column(name = "MODIFIED_BY")
	private String modified_by;
	
	@Column(name = "MODIFIED_ON")
	private Timestamp modifiedOn;
	
	@Column(name = "ACCEPT")
	private String accept;
	
	 @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }

}
