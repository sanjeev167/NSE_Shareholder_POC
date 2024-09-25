package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nseit.shareholder1.modelInterfaces.CheckUserIdInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "USER_MASTER")
public class UserMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "username number is required",groups = {CheckUserIdInterface.class})
	@Column(name = "USERNAME")
	private String username;

	@NotEmpty(message = "email number is required")
	@Column(name = "EMAIL")
	private String email;

	@NotEmpty(message = "pan number is required")
	@Column(name = "PAN")
	private String pan;

	@NotEmpty(message = "Phone number is required")
	@Column(name = "PHONE")
	private String phone;

	@NotEmpty(message = "fullName number is required")
	@Column(name = "FULL_NAME")
	private String fullName;
//
//	@Column(name = "LAST_NAME")
//	private String lastName;

	@Column(name = "ENABLED")
	private String enable;

//	@Column(name = "CREATED_ON")
//	private Date createdDtm;
//
//	@Column(name = "UPDATED_ON")
//	private Date updatedDtm;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

//	@Column(name = "NSE_USER")
//	private char nseUser;

	@OneToMany(targetEntity = UserToken.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private List<UserToken> userToken;
	
	@Column(name="KEYCLOAK_ID")
	private String keycloakId;

	@Transient
	private String clientId;
	


}
