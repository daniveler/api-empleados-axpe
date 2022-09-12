package com.axpe.exercices.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.axpe.exercices.persistence.entities.Employee;
import com.axpe.exercices.persistence.enums.ContractStatus;
import com.axpe.exercices.persistence.enums.Department;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	Page<Employee> findByFirstName(String firstName, Pageable pageable);

	Page<Employee> findByDepartment(Department department, Pageable pageable);

	Page<Employee> findByContractStatus(ContractStatus contractStatus, Pageable pageable);

	Page<Employee> findByIdentificationDocumentValue(String identificationDocumentValue, Pageable pageable);
}
