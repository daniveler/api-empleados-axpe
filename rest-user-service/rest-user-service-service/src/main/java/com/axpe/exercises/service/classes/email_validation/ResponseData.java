package com.axpe.exercises.service.classes.email_validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseData
{
	@JsonProperty("email")
	private EmailAttributes emailAttributes;
}
