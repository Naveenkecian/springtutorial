package com.demo.employeemanager.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

import org.apache.commons.collections4.iterators.EmptyListIterator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.employeemanager.dto.EmployeeDTO;
import com.demo.employeemanager.exception.UserNotFoundException;
import com.demo.employeemanager.model.Employee;
import com.demo.employeemanager.repo.EmployeeRepo;

import javax.transaction.Transactional;

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
    
    
public List<Employee> ReadDataFromExcel(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException{
		Path tempDir = Files.createTempDirectory("");

		File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
	
		file.transferTo(tempFile);

		Workbook workbook = WorkbookFactory.create(tempFile);
		// Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
        System.out.println("Retrieving Sheets using for-each loop");
        List<Employee> empList=new ArrayList<>();
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
            
            
            for (Row row: sheet) {
            	
    			
            	
            	String name = row.getCell(0).getStringCellValue();
            	String email = row.getCell(1).getStringCellValue();
            	String jobTitle =  row.getCell(2).getStringCellValue();
            	Long phone1 =  (long) row.getCell(3).getNumericCellValue();
            	String phone=phone1.toString();
            	String imageUrl =  row.getCell(4).getStringCellValue();
            	Long employeeCode1 =  (long) row.getCell(5).getNumericCellValue();
            	String employeeCode=employeeCode1.toString();
            	
            	
            	  
            	Employee emp = new Employee();
            	emp.setName(name);
            	emp.setEmail(email);
            	emp.setJobTitle(jobTitle);
            	emp.setPhone(phone);
            	emp.setImageUrl(imageUrl);
            	emp.setEmployeeCode(employeeCode);
            	
            	
            	empList.add(emp);
            	employeeRepo.save(emp);
            	
              
            }
            
        }
		
        
        
		return empList;
	}

}
