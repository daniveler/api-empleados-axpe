package com.axpe.exercices.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axpe.exercices.persistence.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
