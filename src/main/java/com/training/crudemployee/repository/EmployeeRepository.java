package com.training.crudemployee.repository;

import org.springframework.data.repository.CrudRepository;

import com.training.crudemployee.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

}
