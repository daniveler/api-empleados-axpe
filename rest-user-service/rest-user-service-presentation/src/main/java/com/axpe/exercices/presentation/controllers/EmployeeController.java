package com.axpe.exercices.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.presentation.exceptions.handler.EmployeeNotFoundException;
import com.axpe.exercices.presentation.exceptions.handler.ErrorMessage;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

import com.axpe.exercices.persistence.enums.ErrorType;

@RestController
@RequiredArgsConstructor
public class EmployeeController
{
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public ResponseEntity<?> getAll()
	{
		List<Employee> employeesList = employeeRepository.findAll();

		if (employeesList.isEmpty())
		{
			return ResponseEntity.notFound().build();
		} else
		{
			return ResponseEntity.ok(employeesList);
		}
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getById(@PathVariable Long employeeId)
	{
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));

		return employee;
	}

	@PutMapping("/employees/{employeeId}")
	public ResponseEntity<?> putById(@RequestBody Employee newEmployee, @PathVariable Long employeeId)
	{
		return employeeRepository.findById(employeeId).map(e -> {
			e.setFirstName(newEmployee.getFirstName());
			e.setSurname1(newEmployee.getSurname1());
			e.setSurname2(newEmployee.getSurname2());
			e.setEmail(newEmployee.getEmail());
			e.setPhoneNumber(newEmployee.getPhoneNumber());
			e.setNif(newEmployee.getNif());
			e.setNickname(newEmployee.getNickname());
			e.setPassword(newEmployee.getPassword());
			e.setDepartment(newEmployee.getDepartment());
			e.setContractStatus(newEmployee.getContractStatus());
			e.setDateOfBirth(newEmployee.getDateOfBirth());

			employeeRepository.save(e);

			return ResponseEntity.noContent().build();
		}).orElseGet(() -> {
			return ResponseEntity.notFound().build();
		});
	}

	@PostMapping("/employees")
	public ResponseEntity<?> post(@RequestBody Employee newEmployee)
	{
		Employee employee = employeeRepository.save(newEmployee);

		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}

	@PostMapping("/employees/validate-email")
	public ResponseEntity<?> validateEmail(@RequestParam String email)
	{
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/employees/{employeeId}")
	public ResponseEntity<?> delete(@PathVariable Long employeeId)
	{
		employeeRepository.deleteById(employeeId);

		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlerNotFound(EmployeeNotFoundException ex)
	{
		ErrorMessage errorMessage = new ErrorMessage();
		
		errorMessage.setCode("resourcesNotFound");
		errorMessage.setMessage("Resources requested were not found");
		errorMessage.setType(ErrorType.ERROR);
		errorMessage.setDescription(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
	
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ErrorMessage> handlerNotFound(JsonMappingException ex)
	{
		ErrorMessage errorMessage = new ErrorMessage();
		
		errorMessage.setCode("invalidParameters");
		errorMessage.setMessage("The parameters given are invalid");
		errorMessage.setType(ErrorType.ERROR);
		errorMessage.setDescription(ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
}
