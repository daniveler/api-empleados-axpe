package com.axpe.exercices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;

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

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class EmployeeService
{
	
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private EmployeeDTOMapper employeeDTOMapper;
	
	public ResponseBodyMessage getAllEmployees(String filterBy, String filterValue, String paginationLimit, String paginationOffset)
	{	
		ResponseBodyMessage response = new ResponseBodyMessage();
		
		List<Employee> employeesList = employeeRepository.findAll();		
		List<Employee> filtredList = filterResponse(employeesList, filterBy, filterValue);
		
		Pagination pagination = new Pagination();
		
		response.setData(filtredList.stream()
				.map(employeeDTOMapper::convertToDto)
				.collect(Collectors.toList()));
		
		response.setPagination(pagination);
		
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
		else if(filterValue.isEmpty() && !filterBy.toUpperCase().equals("NONE")) 
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
				// Lanzar excepción de parámetros de filtrado inválidos
				break;
		}
		
		return filtredList;
		
	}
	
	public void paginateResponse() 
	{
		
	}
}
