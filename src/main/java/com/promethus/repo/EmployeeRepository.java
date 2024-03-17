package com.promethus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promethus.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
