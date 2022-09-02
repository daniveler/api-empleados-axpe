package com.axpe.exercices.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAll()
	{
		if (employeeRepository == null) 
		{
			return ResponseEntity.notFound().build();
		}
		else 
		{
			List<Employee> employeesList = employeeRepository.findAll();
			
			return ResponseEntity.ok(employeesList);
		}
	}
}
