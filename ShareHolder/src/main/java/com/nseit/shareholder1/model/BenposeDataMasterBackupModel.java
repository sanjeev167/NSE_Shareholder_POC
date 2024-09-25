package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.nseit.shareholder1.modelInterfaces.CheckUserClientEmailAndPhoneInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserClientIdInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserSecurityQuestionInterface;
import com.nseit.shareholder1.modelInterfaces.SetSecurityQuestionsInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BENPOSE_DATA_MASTER_BACKUP")
public class BenposeDataMasterBackupModel implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@Id
	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "SHAREHOLDER_NAME")
	private String shareholdername;
    
	@Column(name = "SHARES")
	private Long shares;

	@Column(name = "EMAIL")
	private String email;
    
	 @Column(name = "PHONE")
	private String phone;
    
	 
	 
	@Column(name = "PAN")
	private String pan;
   
	
	@Column(name = "BANK_NAME")
	private String bankName;

//	@Column(name = "NOM_NAME")
//	private String nominee;

	@Column(name = "IFSC")
	private String ifsc;

	@Column(name = "ACCOUNT_NUM")
	private String accountNum;
  
	
	@Column(name = "PINCODE")
	private String pincode;

	@Column(name = "JOINT_HOLDER_1")
	private String jointHolder1;
	
	@Column(name = "JOINT_HOLDER_2")
	private String jointHolder2;
	
	@Column(name = "JOINT_HOLDER_3")
	private String jointHolder3;
	
	@Column(name = "FATHER_HUSBAND_NAME")
	private String fatherName;
	
	@Column(name = "ADDRESS_LINE_1")
	private String addressLine1;
	
	@Column(name = "ADDRESS_LINE_2")
	private String addressLine2;
	
	@Column(name = "ADDRESS_LINE_3")
	private String addressLine3;
	
	@Column(name = "ADDRESS_LINE_4")
	private String addressLine4;

	@Column(name = "THIRD_HOLDER_PAN")
	private String thirdHolderPan;
	
	@Column(name = "SECOND_HOLDER_PAN")
	private String secondHolderPan;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "OCCUPATION")
	private String occupation;
	
	@Column(name = "BANK_ADDRESS_LINE_1")
	private String bankAddressLine1;
	
	@Column(name = "BANK_ADDRESS_LINE_2")
	private String bankAddressLine2;
	
	@Column(name = "BANK_ADDRESS_LINE_3")
	private String bankAddressLine3;
	
	@Column(name = "BANK_ADDRESS_LINE_4")
	private String bankAddressLine4;
	
	@Column(name = "GAUARIDAN_NAME")
	private String gauaridanName;
	
	@Column(name = "NOM_NAME")
	private String nomName;
	
	@Column(name = "MICR_CODE")
	private String micrCode;
	
	@Column(name = "BANK_ACC_TYPE")
	private String bankAccType;
	
	@Column(name = "BANK_PINCODE")
	private String bankPincode;
	
	@Column(name = "ROWNUMBER")
	private Long rowNumber;
	
	
}
