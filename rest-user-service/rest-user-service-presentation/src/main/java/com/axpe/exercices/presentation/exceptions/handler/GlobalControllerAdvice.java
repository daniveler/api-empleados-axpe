package com.axpe.exercices.presentation.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;
import com.axpe.exercises.service.exceptions.ErrorMessage;
import com.axpe.exercises.service.exceptions.GetAllFilterException;
import com.axpe.exercices.persistence.enums.ErrorType;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler
{
	// 400 Bad Request
	@ExceptionHandler(GetAllFilterException.class)
	public ResponseEntity<ErrorMessage> handlerNotFound(GetAllFilterException ex)
	{
		ErrorMessage errorMessage = new ErrorMessage(
				"filterException",
				"Filter sent is not valid",
				ErrorType.ERROR,
				ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
		
	// 404 Not Found
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlerNotFound(EmployeeNotFoundException ex)
	{
		ErrorMessage errorMessage = new ErrorMessage(
				"resourcesNotFound",
				"Resources requested were not found",
				ErrorType.ERROR,
				ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		ErrorMessage errorMessage = new ErrorMessage(
				null,
				null,
				ErrorType.ERROR,
				ex.getMessage());
		
		return ResponseEntity.status(status).headers(headers).body(errorMessage);		
	}
}
