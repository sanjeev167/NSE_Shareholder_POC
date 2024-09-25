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
@Table(name = "CHECKER_MAKER_MASTER")
public class CheckerMakerModel implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="MENUS")
	private String menus;
	
	@Column(name="MAKER")
	private String maker;
	
	@Column(name="CHECKER")
	private String checker;
	
	@Column(name="APPROVER")
	private String approver;
	
	
	
	 @Override
	    public Object clone()
	        throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
}
