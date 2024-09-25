package com.nseit.regulatory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Qualifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "REGULATORY_CHECK_EMPLOYEE")
@Qualifier("integrationDataSource")
public class RegulatoryCheckUpEmployee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ADDED_BY")
	private String addedBy;

	@Column(name = "ADDED_ON")
	private String addedOn;

	@Column(name = "DATABASE_NAME")
	private String dataBaseName;

	@Column(name = "DATEOF")
	private String dateOf;

	@Column(name = "DEPENDENT_NAME")
	private String dependentName;

	@Column(name = "DEPENDENT_PAN")
	private String dependentPan;

	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "EMPLOYEE_PAN")
	private String employeePan;

	@Column(name = "RELATION")
	private String relation;

	@Transient
	private String hhid;

}
