package com.axpe.exercices.service.dto;

import java.sql.Date;

import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;
import com.axpe.exercices.persistence.enums.IdentificationDocumentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO
{
	private Long employeeId;
	private String firstName;
	private String surname1;
	private String surname2;
	private String email;
	private String phoneNumber;
	private String identificationDocumentValue;
	private IdentificationDocumentType identificationDocumentType;	
	private String nickname;
	private String password;
	private Department department;
	private ContractStatus contractStatus;
	private boolean emailVerified;
	private Date dateOfBirth;
	private Date entryDate;
	private Date cancelDate;
	private Date modifiedDate;
}
