package com.axpe.exercices.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController
{
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAll()
	{
		List<Employee> employeesList = employeeRepository.findAll();
		
		return employeesList;
		
//		if (employeesList.isEmpty()) 
//		{
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.ok(employeesList);
	}
}
