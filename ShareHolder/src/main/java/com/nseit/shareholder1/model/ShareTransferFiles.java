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
@Table(name="SHARE_TRANSFER_FILE_MASTER")
public class ShareTransferFiles implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	@Column(name="FILE_NAME")
	private String fileName;
	@Column(name="FILE_PATH")
	private String filePath;
	@Column(name="CREATED_BY")
	private String createdby;
	

}
