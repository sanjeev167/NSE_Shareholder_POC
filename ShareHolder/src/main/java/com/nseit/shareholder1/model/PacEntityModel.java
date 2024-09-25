package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PAC_ENTITY")
public class PacEntityModel implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "name is required")
	@Column(name = "NAME")
	private String name;
	
	@NotNull(message = "percentageHolding is required")
	@Column(name = "PERCENTAGE_HOLDING")
	private Float percentageHolding;
	

//	@NotEmpty(message = "groupId is required")
	@Column(name = "GROUP_ID")
	private String groupId;
//	@OneToMany(targetEntity = PacGroupMappingModel.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "GROUP_NAME", referencedColumnName = "GROUP_ID")
//	private List<PacGroupMappingModel> groupId;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "CATEGORY")
	private String category;  
	
	@Column(name="CREATED_BY")
	private String createdby;
	

	
	@Override
    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
}
