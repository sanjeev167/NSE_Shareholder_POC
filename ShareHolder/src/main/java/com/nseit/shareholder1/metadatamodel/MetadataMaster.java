package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBlobType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="SHARE_TRANSFER_METADATA_MASTER")
@TypeDef(name = "jsonb", typeClass = JsonBlobType.class)
public class MetadataMaster implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@Column(name="METADATA")
//	@Lob
	@Type(type = "jsonb")
	private MetadataPojo metadata;
	
	
	@Column(name="METADATA_VERSION")
	private int metadataVersion;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="CREATED_BY")
	private String createdby;
	
//	@Column(name="FILE_PATH")
//	private String filePath;

}
