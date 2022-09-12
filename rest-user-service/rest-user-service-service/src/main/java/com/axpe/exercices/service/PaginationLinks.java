package com.axpe.exercices.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PaginationLinks
{
	private String first;
	private String prev;
	private String self;
	private String next;
	private String last;
}
