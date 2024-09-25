package com.nseit.shareholder1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CAG_MASTER")
public class CagModel  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "SHAREHOLDER_NAME")
	private String shareholderName;
	
	@Column(name = "NO_OF_SHARES")
	private Long noOfShares;
	


}
