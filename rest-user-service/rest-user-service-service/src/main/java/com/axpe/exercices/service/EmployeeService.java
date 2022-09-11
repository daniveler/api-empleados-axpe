package com.axpe.exercices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder.Case;

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
import com.axpe.exercices.service.mappers.EmployeeDTOMapper;
import com.axpe.exercises.service.exceptions.EmployeeNotFoundException;
import com.jayway.jsonpath.Predicate;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class EmployeeService
{
	
	@Autowired private EmployeeRepository employeeRepository;
	@Autowired private EmployeeDTOMapper employeeDTOMapper;
	
	public ResponseBodyMessage getAllEmployees(String filterBy, String filterValue, String paginationLimit, String paginationOffset)
	{
		List<Employee> employeesList = employeeRepository.findAll();
		List<Employee> filtredList = new ArrayList<>();
		
		ResponseBodyMessage response = new ResponseBodyMessage();
				
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
				filtredList = employeesList.stream()
						.filter(employee -> employee.getDepartment() == Department.valueOf(filterValue.toUpperCase()))
						.collect(Collectors.toList());
				
				break;
			}
			case CONTRACTSTATUS:
			{
				filtredList = employeesList.stream()
						.filter(employee -> employee.getContractStatus() == ContractStatus.valueOf(filterValue.toUpperCase()))
						.collect(Collectors.toList());
				
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
		
		response.setData(filtredList.stream()
				.map(employeeDTOMapper::convertToDto)
				.collect(Collectors.toList()));
		
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
}
