package com.axpe.exercices.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Pagination
{
	private int offset;
	private int limit;
	private int pageNumber;
	private int totalPages;
	private int totalElements;
	private String[] links;
}
