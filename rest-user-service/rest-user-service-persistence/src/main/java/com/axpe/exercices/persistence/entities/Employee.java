package com.axpe.exercices.persistence.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Employee
{
	@Id @GeneratedValue
	private int employeeId;
	
	private String firstName;
	private String surname1;
	private String surname2;
	private String email;
	private String phoneNumber;
	//private IdentificationDocument identificationDocument;
	private String nif;
	private String nickname;
	private String password;
	private enum department
	{
		DEVELOPMENT, TESTING, DESIGN, MARKETING, MAINTENANCE
	}
	private enum contractStatus
	{
		TRAINEE, TEMPORAL, INDEFINITE
	}
	private Date dateOfBirth;
	private Date entryDate;
	private Date cancelDate;
	private Date modifiedDate;
	
	
	
	 
	
	 
}
