package com.scripbox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scripbox.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

}
