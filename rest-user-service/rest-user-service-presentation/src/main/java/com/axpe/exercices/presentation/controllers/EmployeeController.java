package com.axpe.exercices.presentation.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

import com.axpe.exercices.persistence.enums.ErrorType;

@RestController
@RequiredArgsConstructor
public class EmployeeController
{
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	private final EmployeeDTOMapper employeeDTOMapper;

	@GetMapping("/employees")
	public ResponseEntity<?> getAll()
	{
		List<Employee> employeesList = employeeRepository.findAll();

		if (employeesList.isEmpty())
		{
			return ResponseEntity.notFound().build();
		} else
		{
			List<EmployeeDTO> dtoList = employeesList.stream()
					.map(employeeDTOMapper::convertToDto)
					.collect(Collectors.toList());
			
			return ResponseEntity.ok(dtoList);
		}
	}

	@GetMapping("/employees/{employeeId}")
	public EmployeeDTO getById(@PathVariable Long employeeId)
	{
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));

		return employeeDTOMapper.convertToDto(employee);
	}

	@PutMapping("/employees/{employeeId}")
	public ResponseEntity<?> putById(@RequestBody EmployeeDTO newEmployee, @PathVariable Long employeeId)
	{
		return employeeRepository.findById(employeeId).map(e -> {
			e.setFirstName(newEmployee.getFirstName());
			e.setSurname1(newEmployee.getSurname1());
			e.setSurname2(newEmployee.getSurname2());
			e.setEmail(newEmployee.getEmail());
			e.setPhoneNumber(newEmployee.getPhoneNumber());
			e.setIdentificationDocumentValue(newEmployee.getIdentificationDocumentValue());
			e.setIdentificationDocumentType(newEmployee.getIdentificationDocumentType());
			e.setNickname(newEmployee.getNickname());
			e.setPassword(newEmployee.getPassword());
			e.setDepartment(newEmployee.getDepartment());
			e.setContractStatus(newEmployee.getContractStatus());
			e.setDateOfBirth(newEmployee.getDateOfBirth());

			employeeRepository.save(e);

			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
	}

	@PostMapping("/employees")
	public ResponseEntity<?> post(@RequestBody Employee newEmployee)
	{
		// TODO: Parece que el fallo es por violación de unicidad de clave primaria.
		// Es decir, al hacer el post, se le está intentando asignar el idEmployee 1
		
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
		employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		employeeRepository.deleteById(employeeId);

		return ResponseEntity.noContent().build();
	}
}
