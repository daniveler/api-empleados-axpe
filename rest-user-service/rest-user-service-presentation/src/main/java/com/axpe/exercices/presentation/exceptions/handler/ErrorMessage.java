package com.axpe.exercices.presentation.exceptions.handler;

import com.axpe.exercices.persistence.enums.ErrorType;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorMessage
{
	private String code;
	private String message;
	@NonNull
	private ErrorType type;
	private String description;
}
