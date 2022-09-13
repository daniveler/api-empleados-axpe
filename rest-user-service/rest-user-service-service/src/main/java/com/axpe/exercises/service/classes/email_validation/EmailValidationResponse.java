package com.axpe.exercises.service.classes.email_validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmailValidationResponse
{
	@JsonProperty("data")
	private ResponseData data;
}
