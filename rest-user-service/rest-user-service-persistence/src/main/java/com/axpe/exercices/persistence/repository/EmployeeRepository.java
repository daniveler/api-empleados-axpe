package com.axpe.exercices.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	Page<Employee> findByFirstNameIgnoreCase(String firstName, Pageable pageable);

	Page<Employee> findByDepartmentIgnoreCase(Department department, Pageable pageable);

	Page<Employee> findByContractStatusIgnoreCase(ContractStatus contractStatus, Pageable pageable);

	Page<Employee> findByIdentificationDocumentValueIgnoreCase(String identificationDocumentValue, Pageable pageable);
	
	Employee findByEmail(String email);
}
