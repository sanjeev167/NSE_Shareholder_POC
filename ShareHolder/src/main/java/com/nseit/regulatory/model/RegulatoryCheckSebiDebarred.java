package com.nseit.regulatory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "REGULATORY_CHECK_SEBI_DEBARRED")
@Qualifier("integrationDataSource")
public class RegulatoryCheckSebiDebarred implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="DEM_PAN_NO")
	private String pan;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="NAME")
	private String name;

}
