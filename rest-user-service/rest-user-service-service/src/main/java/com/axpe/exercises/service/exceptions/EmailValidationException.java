package com.axpe.exercises.service.exceptions;

public class EmailValidationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public EmailValidationException(String email)
	{
		super("Format of email sent: " + email + " is not valid. Please, try again.");
	}
}
