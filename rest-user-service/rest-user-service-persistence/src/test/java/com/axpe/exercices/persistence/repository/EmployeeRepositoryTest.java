package com.axpe.exercices.persistence.repository;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;
import com.axpe.exercices.persistence.enums.IdentificationDocumentType;

class EmployeeRepositoryTest
{
	/*EmployeeRepository employeeRepository;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.employeeRepository = mock(EmployeeRepository.class);	
	}

	@Test
	@DisplayName("Test que evalúa el correcto funcionamiento del método findById")
	void testFindByIdReturnsOk()
	{
		Optional<Employee> employee = createOptionalEmployee();
		
		when(this.employeeRepository.findById(1L)).thenReturn(employee);
		
		assertNotNull(employee);
		
		verify(employeeRepository).findById(1L);
	}
	
	@Test
	void testFindByFirstNameIgnoreCase()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFindByDepartmentIgnoreCase()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFindByContractStatusIgnoreCase()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFindByIdentificationDocumentValueIgnoreCase()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFindByEmail()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFindAll()
	{
		fail("Not yet implemented");
	}

	@Test
	void testSave()
	{
		fail("Not yet implemented");
	}
	
	Optional<Employee> createOptionalEmployee() 
	{
		Employee employee = new Employee();
		
		employee.setEmployeeId(1L);
		employee.setFirstName("Daniel");
		employee.setSurname1("Velerdas");
		employee.setSurname2("Sedano");
		employee.setEmail("daniel01velerdas@gmail.com");
		employee.setPhoneNumber("+34689456123");
		employee.setIdentificationDocumentType(IdentificationDocumentType.NIF);
		employee.setIdentificationDocumentValue("123456789J");
		employee.setNickname("daniveler");
		employee.setPassword("$2a$10$FRDQgZ83i4/E7Edw6cijIu6lRxiBv5GJu5wD8CiRWC19kYTJLMBRe");
		employee.setDepartment(Department.DEVELOPMENT);
		employee.setContractStatus(ContractStatus.INDEFINITE);
		employee.setDateOfBirth(Date.valueOf("1995-06-29"));
		employee.setEmailVerified(false);
		employee.setEntryDate(Date.valueOf("2022-09-02"));
		employee.setCancelDate(null);
		employee.setModifiedDate(null);
		
		Optional<Employee> employeeOptional = Optional.of(employee);
		
		return employeeOptional;
	}*/

}
