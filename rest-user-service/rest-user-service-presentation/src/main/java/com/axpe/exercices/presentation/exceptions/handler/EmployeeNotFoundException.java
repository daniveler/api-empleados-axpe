package com.axpe.exercices.presentation.exceptions.handler;

public class EmployeeNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long id) {
		super("Employee with id: " + id + " was not found");
	}
}
