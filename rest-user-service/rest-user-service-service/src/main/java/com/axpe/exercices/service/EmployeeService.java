package com.axpe.exercices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;
import com.axpe.exercices.persistence.repository.EmployeeRepository;
import com.axpe.exercices.service.dto.EmployeeDTO;
import com.axpe.exercices.service.enums.FilterTypes;
import com.axpe.exercices.service.enums.FilterTypesExceptions;
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;
import com.axpe.exercises.service.exceptions.GetAllFilterException;
import com.axpe.exercises.service.exceptions.GetAllNoContentException;
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
		List<Employee> employeesList = employeeRepository.findAll();		
		List<Employee> filtredList = filterResponse(employeesList, filterBy, filterValue);
		
		if(filtredList.isEmpty()) 
		{
			throw new GetAllNoContentException();
		}
		
		ResponseBodyMessage response = paginateResponse(filterBy, filterValue, paginationLimit, paginationOffset, filtredList);
				
		
		
		return response;
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
	
	public Object validateEmail(String email) throws IOException 
	{	
		final String url = "https://global-email-v4.p.rapidapi.com/v4/WEB/GlobalEmail/doGlobalEmail?"
				+ "email=" + email 
				+ "&opt=VerifyMailbox:Express|VerifyMailbox:ExpressPremium&"
				+ "format=json";
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("X-RapidAPI-Key", "9b3c39653emshd4d390317c7cc52p1edca1jsn773aeec76ac2");
		headers.set("X-RapidAPI-Host", "global-email-v4.p.rapidapi.com");

		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
		
		Object responseObject = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
				
		return responseObject;
	}
	
	public void deleteOneEmployee(Long employeeId) 
	{
		employeeRepository.findById(employeeId)
			.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		
		employeeRepository.deleteById(employeeId);
	}
	
	public List<Employee> filterResponse(List<Employee> employeesList, String filterBy, String filterValue) 
	{
		List<Employee> filtredList = new ArrayList<>();

		if(filterBy.isEmpty()) 
		{
			throw new GetAllFilterException(FilterTypesExceptions.EMPTYFILTERTYPE, filterBy, filterValue);
		}
		else if((filterValue == null || filterValue.isEmpty()) && !filterBy.toUpperCase().equals("NONE")) 
		{
			try { FilterTypes.valueOf(filterBy.toUpperCase()); } 
			catch (Exception e)
			{
				throw new GetAllFilterException(FilterTypesExceptions.WRONGFILTERTYPE, filterBy, filterValue);
			}
			
			throw new GetAllFilterException(FilterTypesExceptions.EMPTYFILTERVALUE, filterBy, filterValue);
		}
		
		switch (FilterTypes.valueOf(filterBy.toUpperCase()))
		{
			case NAME:
			{
				filtredList = employeesList.stream()
					.filter(employee -> employee.getFirstName().toUpperCase().equals(filterValue.toUpperCase()))
					.collect(Collectors.toList());
				
				break;
			}
			case IDENTIFICATIONDOCUMENT:
			{
				filtredList = employeesList.stream()
						.filter(employee -> employee.getIdentificationDocumentValue().toUpperCase() == filterValue.toUpperCase())
						.collect(Collectors.toList());
				
				break;
			}
			case DEPARTMENT:
			{
				try
				{
					filtredList = employeesList.stream()
							.filter(employee -> employee.getDepartment() == Department.valueOf(filterValue.toUpperCase()))
							.collect(Collectors.toList());
				} catch (Exception e)
				{
					throw new GetAllFilterException(FilterTypesExceptions.OTHERS, filterBy, filterValue);
				}
				
				
				break;
			}
			case CONTRACTSTATUS:
			{
				try
				{
					filtredList = employeesList.stream()
							.filter(employee -> employee.getContractStatus() == ContractStatus.valueOf(filterValue.toUpperCase()))
							.collect(Collectors.toList());
				} 
				catch (Exception e)
				{
					throw new GetAllFilterException(FilterTypesExceptions.OTHERS, filterBy, filterValue);
				}
				
				
				break;
			}
			case NONE:
			{
				filtredList = employeesList;
				
				break;
			}
			default:
				break;
		}
		
		return filtredList;
		
	}
	
	public ResponseBodyMessage paginateResponse(String filterBy, String filterValue, Integer limit, Integer offset, List<Employee> filtredList) 
	{
		Pagination pagination = new Pagination();
		
		if(limit == null || limit <= 0) { limit = 10; }
		else if(limit > 50) { limit = 50; }
		
		if (offset == null) { offset = 0; } 
		else if(offset < 0 || offset >= filtredList.size()) { throw new PaginationException(offset); }
		
		if(filterValue == null) { filterValue = ""; }
		
		int totalElements = filtredList.size();
		int totalPages = (int) Math.ceil(Double.valueOf(totalElements)/ Double.valueOf(limit));
		int pageNumber = 1;
		
		pagination.setLimit(limit);
		pagination.setOffset(offset);
		pagination.setTotalElements(totalElements);
		pagination.setTotalPages(totalPages);
		pagination.setPageNumber(pageNumber);
		
		//List<List<Employee>> pagesList = com.google.common.collect.Lists.partition(filtredList, limit);
		
		String first = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy 
				+ "&filterValue=" + filterValue 
				+ "&paginationLimit=" + limit
				+ "&paginationOffset=" + 0;
		
		String prev = "";
		if (pageNumber > 1)
		{
			prev = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy 
				+ "&filterValue=" + filterValue 
				+ "&paginationLimit=" + limit
				+ "&paginationOffset=" + (offset - limit);
		}
		
		String self = "http://localhost:8080/rest-user-service/employees/" 
				+ "?filterBy=" + filterBy 
				+ "&filterValue=" + filterValue 
				+ "&paginationLimit=" + limit
				+ "&paginationOffset=" + offset;
		
		String next = "";
				
		if (pageNumber != totalPages) 
		{
			next = "http://localhost:8080/rest-user-service/employees/" 
					+ "?filterBy=" + filterBy 
					+ "&filterValue=" + filterValue 
					+ "&paginationLimit=" + limit
					+ "&paginationOffset=" + (offset + limit);
		}
		
		String last = "";
		
		if(totalPages == 1) { last = first; }
		if(pageNumber == totalPages) { last = self; }
		else if(totalElements - limit >= 0)
		{
			last = "http://localhost:8080/rest-user-service/employees/" 
					+ "?filterBy=" + filterBy 
					+ "&filterValue=" + filterValue 
					+ "&paginationLimit=" + limit 
					+ "&paginationOffset=" + (totalElements - limit);
		}
			
		
		PaginationLinks links = new PaginationLinks(first, prev, self,  next, last);
		pagination.setLinks(links);
		
		List<Employee> resultList;
//		if(offset > 0) 
//		{ 
//			int fromIndex = offset - 1;
//			int toIndex = fromIndex + limit;
//			
//			if(totalElements < limit) 
//			{
//				
//			}
//			
//			resultList = filtredList.subList(fromIndex, toIndex);
//		}
//		else { resultList = filtredList; }
		
		resultList = filtredList;
		
		List<EmployeeDTO> data = resultList.stream()
				.map(employeeDTOMapper::convertToDto)
				.collect(Collectors.toList());
		
		ResponseBodyMessage response = new ResponseBodyMessage(data, pagination);
		
		return response;
	}
}








