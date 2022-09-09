package com.axpe.exercices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class EmployeeService
{
	
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private EmployeeDTOMapper employeeDTOMapper;
	
	public List<EmployeeDTO> getAllEmployees()
	{
		List<Employee> employeesList = employeeRepository.findAll();
		
		return employeesList.stream()
				.map(employeeDTOMapper::convertToDto)
				.collect(Collectors.toList());
		
	}
	
	public EmployeeDTO getOneEmployee(Long employeeId)
	{
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		EmployeeDTO employeeDTO = employeeDTOMapper.convertToDto(employee);
		
		return employeeDTO;
	}
	
	public void putOneEmployee(Long employeeId, EmployeeDTO newEmployee) 
	{				
		employeeRepository.findById(employeeId).map(e -> {
			e.setEmployeeId(employeeId);
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

			return employeeRepository.save(e);
			
		}).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
	}
	
	public void postOneEmployee(Employee employee) 
	{
		employeeRepository.save(employee);
	}
	
	public void validateEmail(String email) 
	{
		
	}
	
	public void deleteOneEmployee(Long employeeId) 
	{
		employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		employeeRepository.deleteById(employeeId);
	}
}
