package com.axpe.exercices.service;

import java.util.List;

import com.axpe.exercices.service.dto.EmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseBodyMessage
{
	private List <EmployeeDTO> data;
	private Pagination pagination;
	
	@Data @NoArgsConstructor @AllArgsConstructor
	private class Pagination
	{
		private int offset;
		private int limit;
		private int pageNumber;
		private int totalPages;
		private int totalElements;
		private String[] links;
	}
}
