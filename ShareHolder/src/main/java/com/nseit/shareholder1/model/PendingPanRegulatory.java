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
@Table(name = "PENDING_PAN_REGULATORY")
public class PendingPanRegulatory {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "PAN")
	private String pan;
	
	@Column(name = "SHARE_TRANSFER_MASTER_ID")
	private Long shareTransferId;
	
	@Column(name = "SHARE_TRANSFER_MASTER_UUID")
	private Long shareTransferUuid;

}
