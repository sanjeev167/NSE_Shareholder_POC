package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

//import org.springframework.data.relational.core.mapping.Column;
//import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SESSION_MASTER")
public class UserToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TOKEN")
	private String token;

//	@Column(name = "CREATED_ON")
//	private Date createdOn;
//
//	@Column(name = "MODIFIED_ON")
//	private Date modifiedOn;

	@Column(name = "USERID")
	private Long userId;

}
