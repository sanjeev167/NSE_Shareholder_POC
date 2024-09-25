package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//@JsonIgnoreProperties(ignoreUnknown = true)
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "VERIFY_QUESTIONS")
public class VerifyQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "clientId may not be null")
	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid")
	@Column(name = "CLIENT_ID")
	private String clientId;
	@Column(name = "QUESTIONS_GROUP1")
	private String questionGroup1;
	@Column(name = "QUESTIONS_GROUP2")
	private String questionGroup2;

}
