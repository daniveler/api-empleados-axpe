package com.axpe.exercises.service.exceptions;

public class PaginationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public PaginationException(Integer offset) {
		super("Invalid value for offset pagination: " + offset); 
	}
}
