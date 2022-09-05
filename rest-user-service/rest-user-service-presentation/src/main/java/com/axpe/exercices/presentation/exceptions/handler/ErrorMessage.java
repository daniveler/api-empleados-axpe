package com.axpe.exercices.presentation.exceptions.handler;

import com.axpe.exercices.persistence.enums.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage
{
	private String code;
	private String message;
	private ErrorType type;
	private String description;
}
