package com.axpe.exercices.persistence.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

enum ContractStatus
{
	INDEFINITE, TRAINEE, TEMPORAL
}

enum Department
{
	DEVELOPMENT, TESTING, DESIGN, MARKETING, MAINTENANCE
}

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "employees")
@DynamicInsert(true)
public class Employee
{
	@Id @GeneratedValue
	private Long employeeId;
	
	private String firstName;
	private String surname1;
	private String surname2;
	private String email;
	private String phoneNumber;
	//private IdentificationDocument identificationDocument;
	private String nif;
	private String nickname;
	private String password;
	private Department department;
	private ContractStatus contractStatus;
	private boolean isEmailVerified;
	private Date dateOfBirth;
	private Date entryDate;
	private Date cancelDate;
	private Date modifiedDate;
	 
}
