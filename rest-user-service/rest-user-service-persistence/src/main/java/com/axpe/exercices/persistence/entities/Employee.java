package com.axpe.exercices.persistence.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;
import com.axpe.exercices.persistence.enums.IdentificationDocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "employees")
@DynamicInsert(true)
public class Employee
{
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_employees")
	@SequenceGenerator(name = "sq_employees", sequenceName = "sq_employees", allocationSize = 1)
	
	@NonNull private Long employeeId;
	@NonNull private String firstName;
	private String surname1;
	private String surname2;
	private String email;
	private String phoneNumber;
	@NonNull private String identificationDocumentValue;
	
	@NonNull @Enumerated(EnumType.STRING)
	private IdentificationDocumentType identificationDocumentType;
	
	@NonNull private String nickname;
	@NonNull private String password;
	
	@Enumerated(EnumType.STRING)
	private Department department;
	
	@Enumerated(EnumType.STRING)
	private ContractStatus contractStatus;
	
	private boolean emailVerified;
	private Date dateOfBirth;
	private Date entryDate;
	private Date cancelDate;
	private Date modifiedDate;
}
