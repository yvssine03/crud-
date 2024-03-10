package com.employeedao.DAO;

import com.employeedao.Model.Employee;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(Employee employee);
    Employee getEmployee(int id);
    List<Employee> getAllEmployees();
    boolean isEmailUnique(String email);

}
