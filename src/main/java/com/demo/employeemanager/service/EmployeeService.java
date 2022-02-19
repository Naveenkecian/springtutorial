package com.demo.employeemanager.service;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.employeemanager.dto.EmployeeDTO;
import com.demo.employeemanager.exception.UserNotFoundException;
import com.demo.employeemanager.model.Employee;
import com.demo.employeemanager.repo.EmployeeRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public EmployeeDTO addEmployee(EmployeeDTO employee) {
        Employee emp=EmployeeDTO.getEntity(employee);
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return Employee.getDto(employeeRepo.save(emp));
    }

    public List<EmployeeDTO> findAllEmployees() {
        List<EmployeeDTO> emdto=new ArrayList<>();
        emdto=employeeRepo.findAll().stream().map((Employee emp)->Employee.getDto(emp)).collect(Collectors.toList());
        return emdto;
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        Employee emp=employee.getEntity(employee);
        return emp.getDto(employeeRepo.save(emp));
    }

    public EmployeeDTO findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id).map((Employee employee)->employee.getDto(employee))
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }
}
