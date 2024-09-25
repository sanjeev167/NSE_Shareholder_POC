package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.relational.core.mapping.Column;
//import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Component;

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
@Table(name="USER_PASSWORD_MASTER")

public class User  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;

	@Column(name="PASS_HASH")
	private String password;
    
//	@CreationTimestamp
//	@Column(name="CREATED_ON", insertable =false)
//	private Timestamp createdOn;
//	
//	@UpdateTimestamp
//	
//	@Column(name="UPDATED_ON")
//	private Timestamp updatedOn;
	

	
	@ManyToOne(targetEntity = UserMaster.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID",referencedColumnName="ID")

	private UserMaster userMaster;
	
	
	
	
	
	
	
	
	
	

	

}
