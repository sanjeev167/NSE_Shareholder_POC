/*
 * Created on 2024-04-08 ( 22:27:37 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.nse.common.sec.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * JPA entity class for "GroupMstr"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="group_mstr")
@JsonIgnoreProperties({"listOfGroupRole","createdByUser","modifiedByUser",
                       "createdOn","createdBy","modifiedOn","modifiedBy","activeC"})
public class GroupMstr implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer    id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="group_name", nullable=false, length=50)
    private String     groupName ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_on")
    @JsonManagedReference
    private Date       createdOn ;

    @Column(name="created_by")
    @JsonManagedReference
    private Integer    createdBy ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_on")
    @JsonManagedReference
    private Date       modifiedOn ;

    @Column(name="modified_by")
    @JsonManagedReference
    private Integer    modifiedBy ;

    @Column(name="active_c", nullable=false, length=1)
    private String     activeC ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName="id", insertable=false, updatable=false)
    @JsonManagedReference
    private UserReg    createdByUser ; 

    @OneToMany(mappedBy="groupMstr")
    @JsonManagedReference
    private List<GroupRole> listOfGroupRole ; 

    @ManyToOne
    @JoinColumn(name="modified_by", referencedColumnName="id", insertable=false, updatable=false)
    @JsonManagedReference
    private UserReg    modifiedByUser ; 


    /**
     * Constructor
     */
    public GroupMstr() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    public void setGroupName( String groupName ) {
        this.groupName = groupName ;
    }
    public String getGroupName() {
        return this.groupName;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn ;
    }
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedBy( Integer createdBy ) {
        this.createdBy = createdBy ;
    }
    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setModifiedOn( Date modifiedOn ) {
        this.modifiedOn = modifiedOn ;
    }
    public Date getModifiedOn() {
        return this.modifiedOn;
    }

    public void setModifiedBy( Integer modifiedBy ) {
        this.modifiedBy = modifiedBy ;
    }
    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public void setActiveC( String activeC ) {
        this.activeC = activeC ;
    }
    public String getActiveC() {
        return this.activeC;
    }

    //--- GETTERS FOR LINKS
    public UserReg getCreatedByUser() {
        return this.createdByUser;
    } 

    public List<GroupRole> getListOfGroupRole() {
        return this.listOfGroupRole;
    } 

    public UserReg getModifiedByUser() {
        return this.modifiedByUser;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(groupName);
        sb.append("|");
        sb.append(createdOn);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(modifiedOn);
        sb.append("|");
        sb.append(modifiedBy);
        sb.append("|");
        sb.append(activeC);
        return sb.toString(); 
    } 

}
