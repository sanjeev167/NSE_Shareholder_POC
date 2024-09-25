package com.nseit.shareholder1.model;

import java.io.Serializable;

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
@Table(name = "AUTHORISED_LETTER")
public class AuthorisedLetter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "FILE_NAME")
	private String fileName;
	@Column(name = "FILE_PATH")
	private String filePath;
	
	@Column(name="CREATED_BY")
	private String createdby;
	
	//onotoonemapping in broker client Mapping and add collumn in broker client Mapping table need to remove the collumn from broker master
//	@OneToMany(targetEntity = BrokerMaster.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "AUTHORISED_LETTER", referencedColumnName = "ID")
	
	
//	@OneToOne
//	@JoinColumn(name="AUTHORISED_LETTER")
//	private BrokerClientMapping brokerClientMapping;

}
