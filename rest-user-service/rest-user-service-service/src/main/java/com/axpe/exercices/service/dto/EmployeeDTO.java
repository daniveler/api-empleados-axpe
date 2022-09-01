package com.axpe.exercices.service.dto;

import java.sql.Date;

import com.axpe.exercices.persistence.entities.IdentificationDocument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO
{
	private int employeeId;
	private String firstName;
	private String surname1;
	private String surname2;
	private String email;
	private String phoneNumber;
	private IdentificationDocument identificationDocument;
	private String nickname;
	private enum department
	{
		DEVELOPMENT, TESTING, DESIGN, MARKETING, MAINTENANCE
	}
	private enum contractStatus
	{
		TRAINEE, TEMPORAL, INDEFINITE
	}
	private Date dateOfBirth;
}
