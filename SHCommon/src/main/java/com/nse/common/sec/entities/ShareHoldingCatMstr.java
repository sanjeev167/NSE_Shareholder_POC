/*
 * Created on 2024-04-08 ( 22:27:38 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.nse.common.sec.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

/**
 * JPA entity class for "ShareHoldingCatMstr"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="share_holding_cat_mstr", schema="public" )
public class ShareHoldingCatMstr implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long       id ;

    //--- ENTITY DATA FIELDS 
    @Column(name="main_cat_id")
    private Integer    mainCatId ;

    @Column(name="sub_cat_id")
    private Integer    subCatId ;

    @Column(name="flag_id")
    private Integer    flagId ;

    @Column(name="sub_flag_id")
    private Integer    subFlagId ;

    @Column(name="domestic_or_foreign", length=1)
    private String     domesticOrForeign ;

    @Column(name="domestic_details", length=25)
    private String     domesticDetails ;

    @Column(name="foreign_details", length=25)
    private String     foreignDetails ;

    @Column(name="created_by")
    private Integer    createdBy ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_on")
    private Date       createdOn ;

    @Column(name="modified_by")
    private Integer    modifiedBy ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_on")
    private Date       modifiedOn ;

    @Column(name="active_c", length=1)
    private String     activeC ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="shareHoldingCatMstr")
    private List<ShareHoldingLimitPerctge> listOfShareHoldingLimitPerctge ; 

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName="id", insertable=false, updatable=false)
    private UserReg    userReg ; 

    @ManyToOne
    @JoinColumn(name="modified_by", referencedColumnName="id", insertable=false, updatable=false)
    private UserReg    userReg2 ; 


    /**
     * Constructor
     */
    public ShareHoldingCatMstr() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setId( Long id ) {
        this.id = id ;
    }
    public Long getId() {
        return this.id;
    }

    public void setMainCatId( Integer mainCatId ) {
        this.mainCatId = mainCatId ;
    }
    public Integer getMainCatId() {
        return this.mainCatId;
    }

    public void setSubCatId( Integer subCatId ) {
        this.subCatId = subCatId ;
    }
    public Integer getSubCatId() {
        return this.subCatId;
    }

    public void setFlagId( Integer flagId ) {
        this.flagId = flagId ;
    }
    public Integer getFlagId() {
        return this.flagId;
    }

    public void setSubFlagId( Integer subFlagId ) {
        this.subFlagId = subFlagId ;
    }
    public Integer getSubFlagId() {
        return this.subFlagId;
    }

    public void setDomesticOrForeign( String domesticOrForeign ) {
        this.domesticOrForeign = domesticOrForeign ;
    }
    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticDetails( String domesticDetails ) {
        this.domesticDetails = domesticDetails ;
    }
    public String getDomesticDetails() {
        return this.domesticDetails;
    }

    public void setForeignDetails( String foreignDetails ) {
        this.foreignDetails = foreignDetails ;
    }
    public String getForeignDetails() {
        return this.foreignDetails;
    }

    public void setCreatedBy( Integer createdBy ) {
        this.createdBy = createdBy ;
    }
    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedOn( Date createdOn ) {
        this.createdOn = createdOn ;
    }
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setModifiedBy( Integer modifiedBy ) {
        this.modifiedBy = modifiedBy ;
    }
    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedOn( Date modifiedOn ) {
        this.modifiedOn = modifiedOn ;
    }
    public Date getModifiedOn() {
        return this.modifiedOn;
    }

    public void setActiveC( String activeC ) {
        this.activeC = activeC ;
    }
    public String getActiveC() {
        return this.activeC;
    }

    //--- GETTERS FOR LINKS
    public List<ShareHoldingLimitPerctge> getListOfShareHoldingLimitPerctge() {
        return this.listOfShareHoldingLimitPerctge;
    } 

    public UserReg getUserReg() {
        return this.userReg;
    } 

    public UserReg getUserReg2() {
        return this.userReg2;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(mainCatId);
        sb.append("|");
        sb.append(subCatId);
        sb.append("|");
        sb.append(flagId);
        sb.append("|");
        sb.append(subFlagId);
        sb.append("|");
        sb.append(domesticOrForeign);
        sb.append("|");
        sb.append(domesticDetails);
        sb.append("|");
        sb.append(foreignDetails);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdOn);
        sb.append("|");
        sb.append(modifiedBy);
        sb.append("|");
        sb.append(modifiedOn);
        sb.append("|");
        sb.append(activeC);
        return sb.toString(); 
    } 

}
