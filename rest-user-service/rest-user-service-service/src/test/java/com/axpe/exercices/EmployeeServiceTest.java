package com.axpe.exercices;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.IdentificationDocumentType;
import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.service.EmployeeService;
import com.axpe.exercices.service.ResponseBodyMessage;
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest
{
	@Mock private EmployeeRepository employeeRepository;
	@Mock private EmployeeDTOMapper employeeDTOMapper;
	
	@InjectMocks
	private EmployeeService employeeService = new EmployeeService(employeeRepository, employeeDTOMapper);

	@Test
	@DisplayName("Get all employees filtred by NONE returns paginated list of employees")
	void testGetAllEmployeesFiltredByNoneReturnsOk()
	{
		//Given
		int paginationOffset = 0;
		int paginationLimit = 10;
		
		org.springframework.data.domain.Pageable paging = PageRequest.of(paginationOffset, paginationLimit);
		
		Employee emp1 = createEmployee(1L);
		Employee emp2 = createEmployee(2L);
		
		EmployeeDTO empDto1 = createEmployeeDTO(1L);
		EmployeeDTO empDto2 = createEmployeeDTO(2L);

//		List<Employee> empList = Arrays.asList(emp1, emp2);
//		List<EmployeeDTO> empDtoList = Arrays.asList(empDto1, empDto2);
		
		//Page<Employee> responsePage = new PageImpl<Employee>(empList);
		Page<Employee> responsePage = mock(Page.class);
		
		lenient().when(employeeRepository.findAll(paging)).thenReturn(responsePage);
		when(employeeDTOMapper.convertToDto(emp1)).thenReturn(empDto1);
		when(employeeDTOMapper.convertToDto(emp2)).thenReturn(empDto2);
		
		//When
		ResponseBodyMessage response = this.employeeService.getAllEmployees("NONE", null, 0, 10);
		
		//Then
		assertNotNull(response);
	}
	
	@Test
	@DisplayName("Get one employee returns the employee")
	void testGetOneEmployeeReturnsOk()
	{
		//Given
		Long employeeId = 1L;
		
		Employee emp = createEmployee(employeeId);
		EmployeeDTO empDto = createEmployeeDTO(employeeId);
		Optional<Employee> empOpt = createOptionalEmployee(employeeId);
		
		when(employeeRepository.findById(employeeId)).thenReturn(empOpt);
		when(employeeDTOMapper.convertToDto(emp)).thenReturn(empDto);
		
		//When
		EmployeeDTO resultDto = this.employeeService.getOneEmployee(employeeId);
		
		//Then
		assertNotNull(resultDto);
		assertEquals(resultDto.getEmployeeId(), empDto.getEmployeeId());
		verify(employeeRepository, times(1)).findById(employeeId);
	}

	@Test
	@DisplayName("Get one employee returns Employee Not Found Exception")
	void testGetOneEmployeeReturnsEmployeeNotFoundException()
	{
		//Given
		Long employeeId = 1L;
		when(this.employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		
		//When
		
		//Then
		assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.getOneEmployee(employeeId));
	}

	@Test
	@DisplayName("Put one employee returns No Content")
	void testPutOneEmployeeReturnsNoContent()
	{
		//Given
		Long employeeId = 1L;
		Optional<Employee> optEmp = createOptionalEmployee(employeeId);
		Employee emp = createEmployee(employeeId);		
		EmployeeDTO newEmpDto = createEmployeeDTO(employeeId);
		
		when(employeeRepository.findById(employeeId)).thenReturn(optEmp);
		lenient().when(employeeDTOMapper.convertToEntity(newEmpDto)).thenReturn(emp);
		lenient().when(employeeRepository.save(emp)).thenReturn(emp);
		
		//When
		this.employeeService.putOneEmployee(employeeId, newEmpDto);
		
		//Then
		assertNotNull(newEmpDto);
		verify(employeeRepository, times(1)).findById(employeeId);
		verify(employeeRepository, times(1)).save(emp);
		
	}
	
	@Test
	@DisplayName("Put one employee returns Employee Not Found Exception")
	void testPutOneEmployeeReturnsEmployeeNotFoundException()
	{
		//Given
		Long employeeId = 1L;
		Employee emp = createEmployee(employeeId);
		EmployeeDTO empDto = createEmployeeDTO(employeeId);
		
		when(this.employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		
		//When
		
		//Then
		assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.putOneEmployee(employeeId, empDto));
		verify(employeeRepository, times(0)).save(emp);
	}

	@Test
	@DisplayName("Post one employee returns Created")
	void testPostOneEmployeeReturnsCreated()
	{
		//Given
		Long employeeId = 1L;
		Employee emp = createEmployee(employeeId);
		
		when(employeeRepository.save(emp)).thenReturn(emp);
		
		//When
		this.employeeService.postOneEmployee(emp);
		
		//Then
		assertNotNull(emp);
		verify(employeeRepository, times(1)).save(emp);
	}

	@Test
	@DisplayName("Validates email returns No Content")
	void testValidateEmailReturnsNoContent()
	{
		//Given
		String email = "prueba@gmail.com";
		Employee emp = createEmployee(1L);
		
		when(employeeRepository.findByEmail(email)).thenReturn(emp);
		
		//When
		boolean correct = this.employeeService.validateEmail(email);
		
		//Then
		assertTrue(correct);
		verify(employeeRepository, times(1)).findByEmail(email);
		verify(employeeRepository, times(1)).save(emp);
	}
	
	@Test
	@DisplayName("Validates email returns Not Found Exception")
	void testValidateEmailReturnsNotFoundException()
	{
		//Given
		String email = "prueba@gmail.com";
		Employee emp = createEmployee(1L);
		
		when(employeeRepository.findByEmail(email)).thenReturn(null);
		
		//When
		boolean correct = this.employeeService.validateEmail(email);
		
		//Then
		assertFalse(correct);
		verify(employeeRepository, times(0)).findByEmail(email);
		verify(employeeRepository, times(0)).save(emp);	
	}

	@Test
	@DisplayName("Deletes an employee and returns No Content")
	void testDeleteOneEmployeeReturnsNoContent()
	{
//		Given
//		Long employeeId = 1L;
//		Employee emp = createEmployee(employeeId);
//		
//		when(employeeRepository.findById(employeeId));
//		//when(employeeRepository.deleteById(employeeId));
//		
//		When
//		
//		
//		Then
	}
	
	@Test
	@DisplayName("Deletes an employee and returns Employee Not Found Exception")
	void testDeleteOneEmployeeReturnsEmployeeNotFoundException()
	{
		//Given
		Long employeeId = 1L;
		
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		
		//When
		
		//Then
		assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.deleteOneEmployee(employeeId));
		verify(employeeRepository, times(0)).deleteById(employeeId);
	}
	
	Employee createEmployee(Long employeeId) 
	{
		Employee employee = new Employee(
				employeeId, 
				"Prueba", 
				"Apellido1", 
				"Apellido2", 
				"prueba@gmail.com", 
				"+34689456123", 
				"123456789J", 
				IdentificationDocumentType.NIF, 
				"prueba", 
				"$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe", 
				null, 
				null, 
				false, 
				null, 
				null, 
				null, 
				null);	
		
		return employee;
	}
	
	EmployeeDTO createEmployeeDTO(Long employeeId) 
	{
		EmployeeDTO employeeDto = new EmployeeDTO(
				employeeId, 
				"Prueba", 
				"Apellido1", 
				"Apellido2", 
				"prueba@gmail.com", 
				"+34689456123", 
				"123456789J", 
				IdentificationDocumentType.NIF, 
				"prueba", 
				"$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe", 
				null, 
				null, 
				false, 
				null, 
				null, 
				null, 
				null);
		
		return employeeDto;
	}
	
	Optional<Employee> createOptionalEmployee(Long employeeId) 
	{		
		Employee employee = new Employee(
				employeeId, 
				"Prueba", 
				"Apellido1", 
				"Apellido2", 
				"prueba@gmail.com", 
				"+34689456123", 
				"123456789J", 
				IdentificationDocumentType.NIF, 
				"prueba", 
				"$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe", 
				null, 
				null, 
				false, 
				null, 
				null, 
				null, 
				null);
		
		employee.setModifiedDate(null);
		
		Optional<Employee> employeeOptional = Optional.of(employee);
		
		return employeeOptional;
	}

}
