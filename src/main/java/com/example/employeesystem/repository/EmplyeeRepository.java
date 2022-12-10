package com.example.employeesystem.repository;

import com.example.employeesystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmplyeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findStudentById (long kw);

}
