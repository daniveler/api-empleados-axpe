package com.axpe.exercices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;
import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.axpe.exercices.service.enums.FilterTypes;
import com.axpe.exercices.service.enums.FilterTypesExceptions;
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.axpe.exercises.service.classes.email_validation.EmailValidationResponse;
import com.axpe.exercises.service.exceptions.EmailValidationException;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;
import com.axpe.exercises.service.exceptions.GetAllFilterException;
import com.axpe.exercises.service.exceptions.PaginationException;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
public class EmployeeService
{
	
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private EmployeeDTOMapper employeeDTOMapper;
	
	public ResponseBodyMessage getAllEmployees(String filterBy, String filterValue, Integer paginationLimit, Integer paginationOffset)
	{					
		if(paginationLimit == null || paginationLimit <= 0) { paginationLimit = 10; }
		else if(paginationLimit > 50) { paginationLimit = 50; }
		
		if (paginationOffset == null) { paginationOffset = 0; } 
		else if(paginationOffset < 0) { throw new PaginationException(paginationOffset); }
		
		if (filterBy == null || filterBy.isEmpty()) 
		{ 
			throw new GetAllFilterException(FilterTypesExceptions.EMPTYFILTERTYPE, filterBy, filterValue); 
		}
		
		try { FilterTypes.valueOf(filterBy.toUpperCase()); } 
		catch (Exception e) 
		{
			throw new GetAllFilterException(FilterTypesExceptions.WRONGFILTERTYPE, filterBy, filterValue); 
		}
		
		if ((filterValue == null || filterValue.isEmpty()) && !filterBy.toUpperCase().equals("NONE"))
		{ 
			throw new GetAllFilterException(FilterTypesExceptions.EMPTYFILTERVALUE, filterBy, filterValue); 
		}
		
		return filterAndPaginateResponse(filterBy, filterValue, paginationLimit, paginationOffset);
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
	
	public boolean validateEmail(String email) 
	{	
		final String url = "https://api.tomba.io/v1/email-verifier/" + email;
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("content-type", "application/json");
		headers.set("X-Tomba-Key", "ta_c0a1d9fce1cccff4a134badf434cb1ff2cc79");
		headers.set("X-Tomba-Secret", "ts_6bd7ca73-aedf-4410-b434-7ef2ad665ff8");

		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
		
		EmailValidationResponse emailValidationResponse;
		ResponseEntity<EmailValidationResponse> responseEntity;
						
		try 
		{
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, EmailValidationResponse.class);
			emailValidationResponse = responseEntity.getBody();
			
			List<String> validStatusList = Arrays.asList("VALID", "ACCEPT_ALL", "WEBMAIL", "DISPOSABLE");
			
			String status = emailValidationResponse.getData().getEmailAttributes().getStatus();
			boolean regex = emailValidationResponse.getData().getEmailAttributes().isRegex();
			
			if(regex && validStatusList.contains(status.toUpperCase())) 
			{
				Employee employee = employeeRepository.findByEmail(email);
				
				if(employee != null) 
				{
					employee.setEmailVerified(true);
					
					employeeRepository.save(employee);
					
					return true;
				}
				else return false;
				
			}
			else return false;
			
			
		}
		catch (HttpClientErrorException e) {
			throw new EmailValidationException(email);
		}
	}
	
	public void deleteOneEmployee(Long employeeId) 
	{
		employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		employeeRepository.deleteById(employeeId);
	}
	
	private ResponseBodyMessage filterAndPaginateResponse(String filterBy, String filterValue, Integer paginationLimit, Integer paginationOffset)
	{
		ResponseBodyMessage response = new ResponseBodyMessage();
		
		Pageable paging = PageRequest.of(paginationOffset, paginationLimit);
		
		Page<Employee> responsePage;
		
//		if(filterValue == null) { filterValue = ""; }
		
		switch (FilterTypes.valueOf(filterBy.toUpperCase()))
		{
			case NAME: 
				responsePage = employeeRepository.findByFirstNameIgnoreCase(filterValue, paging);
				break;

			case IDENTIFICATIONDOCUMENT:
				responsePage = employeeRepository.findByIdentificationDocumentValueIgnoreCase(filterValue, paging);
				break;
				
			case DEPARTMENT:
				try
				{
					responsePage = employeeRepository.findByDepartmentIgnoreCase(Department.valueOf(filterValue.toUpperCase()), paging);
				} 
				catch (Exception e)
				{
					throw new GetAllFilterException(FilterTypesExceptions.OTHERS, filterBy, filterValue);
				}
	
				break;
			case CONTRACTSTATUS:
				try
				{
					responsePage = employeeRepository.findByContractStatusIgnoreCase(ContractStatus.valueOf(filterValue.toUpperCase()), paging);
				} 
				catch (Exception e)
				{
					throw new GetAllFilterException(FilterTypesExceptions.OTHERS, filterBy, filterValue);
				}
				
				break;
			case NONE:
				responsePage = employeeRepository.findAll(paging);
				break;
			default:
				responsePage = employeeRepository.findAll(paging);
				break;
		}
		
		response.setData(responsePage.getContent());
		Pagination pagination = new Pagination();
		
		pagination.setPageNumber(responsePage.getNumber() + 1);
		pagination.setTotalPages(responsePage.getTotalPages());
		pagination.setTotalElements((int)responsePage.getTotalElements());
		pagination.setOffset(paginationOffset);
		pagination.setLimit(paginationLimit);
				
		String first = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy
				+ "&filterValue=" + filterValue
				+ "&paginationLimit=" + paginationLimit 
				+ "&paginationOffset=" + 0;
		
		String prev = "";
		if(responsePage.hasPrevious()) 
		{
			prev = "http://localhost:8080/rest-user-service/employees/" 
					+ "?filterBy=" + filterBy
					+ "&filterValue=" + filterValue
					+ "&paginationLimit=" + paginationLimit 
					+ "&paginationOffset=" + (paginationOffset - 1);
		}
		
		String self = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy
				+ "&filterValue=" + filterValue
				+ "&paginationLimit=" + paginationLimit 
				+ "&paginationOffset=" + paginationOffset;
		
		String next = "";
		if(responsePage.hasNext()) 
		{
			next = "http://localhost:8080/rest-user-service/employees/" 
					+ "?filterBy=" + filterBy
					+ "&filterValue=" + filterValue
					+ "&paginationLimit=" + paginationLimit 
					+ "&paginationOffset=" + (paginationOffset + 1);
		}
		
		String last = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy
				+ "&filterValue=" + filterValue
				+ "&paginationLimit=" + paginationLimit 
				+ "&paginationOffset=" + (responsePage.getTotalPages() - 1);
		
		
		PaginationLinks links = new PaginationLinks(first, prev, self, next, last);
		
		pagination.setLinks(links);
		
		response.setPagination(pagination);
		
		return response;
	}

}








