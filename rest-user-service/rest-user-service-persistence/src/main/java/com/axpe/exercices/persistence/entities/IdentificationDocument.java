package com.axpe.exercices.persistence.entities;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor

public class IdentificationDocument
{
	private String value;
	private enum documentType
	{
		NIF, NIE, PASSPORT
	}
}
