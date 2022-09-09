package com.axpe.exercices.service.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.jayway.jsonpath.spi.mapper.MappingException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeDTOMapper
{
	private final ModelMapper modelMapper;
	
	public EmployeeDTO convertToDto(Employee employee)
	{
		return modelMapper.map(employee, EmployeeDTO.class);
	}
	
	public Employee convertToEntity(EmployeeDTO employeeDTO)
	{
		return modelMapper.map(employeeDTO, Employee.class);
	}
}
