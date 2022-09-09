package com.axpe.exercices.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.service.EmployeeService;
import com.axpe.exercices.service.dto.EmployeeDTO;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class EmployeeController
{
//	@Autowired
//	private final EmployeeRepository employeeRepository;
//	
//	private final EmployeeDTOMapper employeeDTOMapper;
//	
	@Autowired
	private final EmployeeService employeeService;

	@GetMapping("/employees")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@GetMapping("/employees/{employeeId}")
	public ResponseEntity<?> getById(@PathVariable Long employeeId)
	{
		return ResponseEntity.ok(employeeService.getOneEmployee(employeeId));
	}

	@PutMapping("/employees/{employeeId}")
	public ResponseEntity<?> putById(@RequestBody EmployeeDTO newEmployee, @PathVariable Long employeeId)
	{
		employeeService.putOneEmployee(employeeId, newEmployee);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/employees")
	public ResponseEntity<?> post(@RequestBody Employee newEmployee)
	{	
		employeeService.postOneEmployee(newEmployee);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/employees/validate-email")
	public ResponseEntity<?> validateEmail(@RequestParam String email)
	{
		employeeService.validateEmail(email);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/employees/{employeeId}")
	public ResponseEntity<?> deleteById(@PathVariable Long employeeId)
	{
		employeeService.deleteOneEmployee(employeeId);
		return ResponseEntity.noContent().build();
	}
}
