package com.demo.employeemanager.dto;

import com.demo.employeemanager.model.Employee;

public class EmployeeDTO {
      private Long id;
      private String name;
      private String email;
      private String jobTitle;
      private String phone;
      private String imageUrl;
      private String employeeCode;

      public Long getId() {
            return id;
      }
      public void setId(Long id) {
            this.id = id;
      }
      public String getName() {
            return name;
      }
      public void setName(String name) {
            this.name = name;
      }
      public String getEmail() {
            return email;
      }
      public void setEmail(String email) {
            this.email = email;
      }
      public String getJobTitle() {
            return jobTitle;
      }
      public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
      }
      public String getPhone() {
            return phone;
      }
      public void setPhone(String phone) {
            this.phone = phone;
      }
      public String getImageUrl() {
            return imageUrl;
      }
      public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
      }
      public String getEmployeeCode() {
            return employeeCode;
      }
      public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
      }
      public static Employee getEntity(EmployeeDTO employee){
            Employee emp=new Employee();
            emp.setEmail(employee.getEmail());
            emp.setEmployeeCode(employee.getEmployeeCode());
            emp.setId(employee.getId());
            emp.setImageUrl(employee.getImageUrl());
            emp.setJobTitle(employee.getJobTitle());
            emp.setName(employee.getName());
            emp.setPhone(employee.getPhone());
            return emp;
      }
}
