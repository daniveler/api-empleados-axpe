package com.axpe.exercises.service.exceptions;

public class EmployeeNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long id) {
		super("Employee with employeeId = " + id + " was not found");
	}
}
