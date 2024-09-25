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
@Table(name = "SHARE_HOLDING_PATTERN")
public class ShareHoldingPattern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SR_NO")
	private int srNo;

	@Column(name = "CATEGORY_OF_SHAREHOLDER")
	private String category;

	@Column(name = "NO_OF_SHARES")
	private Integer noOfShares;

	@Column(name = "PERCENTAGE")
	private Float percentage;

	@Column(name = "SUB_CATEGORY_ONE")
	private String subCategoryOne;

	@Column(name = "SUB_CATEGORY_TWO")
	private String subCategoryTwo;

}
