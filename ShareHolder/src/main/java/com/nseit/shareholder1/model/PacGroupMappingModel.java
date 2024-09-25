package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PAC_GROUP_MAPPING")
public class PacGroupMappingModel implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Column(name = "ACCEPT")
	private String accept;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "PROPOSED_CHANGE")
	private String ProposedChange;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "MODIFIED_ON")
	private Timestamp modifiedOn;
	
	@OneToMany(targetEntity = PacEntityModel.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_NAME")
	private List<PacEntityModel> pacEntityModel;

	
	@Override
    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
//	@Column(name = "PAC_ENTITY")
//	private Long pacEntity;
}
