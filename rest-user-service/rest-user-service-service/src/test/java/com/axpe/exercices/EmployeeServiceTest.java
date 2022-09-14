package com.axpe.exercices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.service.EmployeeService;
import com.axpe.exercices.service.dto.EmployeeDTO;

class EmployeeServiceTest
{
	
	EmployeeRepository employeeRepository;
	EmployeeService employeeService;

	@BeforeEach
	void setUp() throws Exception
	{
		this.employeeRepository = mock(EmployeeRepository.class);
		this.employeeService = new EmployeeService(employeeRepository);
	}

	@Test
	void testGetAllEmployees()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetOneEmployee()
	{
		//EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Daniel");
		//when(this.employeeRepository.findById(1L)).thenReturn(employeeDTO);
	}

	@Test
	void testPutOneEmployee()
	{
		fail("Not yet implemented");
	}

	@Test
	void testPostOneEmployee()
	{
		fail("Not yet implemented");
	}

	@Test
	void testValidateEmail()
	{
		fail("Not yet implemented");
	}

	@Test
	void testDeleteOneEmployee()
	{
		fail("Not yet implemented");
	}

}
