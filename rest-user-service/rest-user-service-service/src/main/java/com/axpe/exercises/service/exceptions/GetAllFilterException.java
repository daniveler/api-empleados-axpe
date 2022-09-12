package com.axpe.exercises.service.exceptions;

import com.axpe.exercices.service.enums.FilterTypesExceptions;

public class GetAllFilterException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private static String selectException(FilterTypesExceptions exceptionType, String filterType, String filterValue) 
	{
		switch(exceptionType) 
		{
			case EMPTYFILTERTYPE:
				return "Field filterType must not be empty. For no filters, use NONE";
			case EMPTYFILTERVALUE:
				return "Field filterValue must not be empty for this filter type";
			case WRONGFILTERTYPE:
				return "Field filterType is invalid. Must be: NAME, IDENTIFICATIONDOCUMENT, DEPARTMENT, CONTRACTSTATUS or NONE";
			case OTHERS:
				return "Filter type: " + filterType + " with value = " + filterValue + "is not valid";
			default:
				return "";
		}
	}

	public GetAllFilterException(FilterTypesExceptions exceptionType, String filterType, String filterValue) {
		super(selectException(exceptionType, filterType, filterValue));
	}
}
