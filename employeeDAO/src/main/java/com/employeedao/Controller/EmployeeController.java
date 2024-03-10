package com.employeedao.Controller;

import com.employeedao.DAO.DB;
import com.employeedao.DAO.EmployeeDAO;
import com.employeedao.DAO.EmployeeDAOImpl;
import com.employeedao.Model.Employee;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class EmployeeController {



    private int currentPage = 1; // Default to the first page
    private int itemsPerPage = 5;
    private ResourceBundle bundle ;
    public int getTotalPages() {
        int totalSize =Employees.size(); // Assuming employees is your list of all items
        double pageSize = itemsPerPage;
        int totalPage = (int) Math.ceil(totalSize / pageSize);
        return totalPage;
    }

    public List<Employee> getPaginatedEmployees() {
        int fromIndex = (currentPage - 1) * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, Employees.size());
        return Employees.subList(fromIndex, toIndex);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void firstPage() {
        currentPage = 1;
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    public void nextPage() {
        int totalPages = (int) Math.ceil((double) Employees.size() / itemsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
        }
    }

    public void lastPage() {
        int totalPages = (int) Math.ceil((double) Employees.size() / itemsPerPage);
        currentPage = totalPages;
    }


    private final EmployeeDAO employeeDAO;

    private Employee newEmployee;

    public Employee getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }

    public boolean isShowAddForm() {
        return showAddForm;
    }

    public void setShowAddForm(boolean showAddForm) {
        this.showAddForm = showAddForm;
    }

    private boolean showAddForm;
    public void toggleAddEmployeeForm() {
        showAddForm = ! showAddForm;
    }


    private List <Employee> Employees;


    public EmployeeController() {
        this.employeeDAO = new EmployeeDAOImpl();
        this.Employees= employeeDAO.getAllEmployees();
        this.newEmployee=new Employee();
    }




    public List<Employee> getEmployees() {

        return Employees;
    }

    public void setEmployees(List<Employee> employees) {
        Employees = employees;
    }
    public void editEmployee(Employee employee) {
        employee.setEditable(true);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;


    public void addEmployee(Employee employee) {
        refresh_bundle();
        if (employeeDAO.isEmailUnique(employee.getMail())) {

            try {

                employeeDAO.addEmployee(employee);
                message = bundle.getString("success_add");
                toggleAddEmployeeForm();
            } catch (Exception e) {
                message = bundle.getString("failed_add") + e.getMessage();
            }
            refresh_list();
        }
        else {  message = bundle.getString("mail_exists");}
    }


    public void saveChanges() {
        for (Employee employee : Employees) {
            if (employee.isEditable()) {
                updateEmployee(employee);
            }
        }
        for (Employee employee : Employees) {
        employee.setEditable(false);
        }
         refresh_list();


    }

    public void updateEmployee(Employee employee) {
        try {
            refresh_bundle();
            employeeDAO.updateEmployee(employee);
            message = bundle.getString("success_update");

        } catch (Exception e) {
            message = bundle.getString("failed_update") + e.getMessage();
        }
        refresh_list();
    }

    public void deleteEmployee(Employee employee) {
        try {
            refresh_bundle();
            employeeDAO.deleteEmployee(employee);
            message = bundle.getString("success_delete");

        } catch (Exception e) {
            message = bundle.getString("failed_delete") + e.getMessage();
        }
        refresh_list();
    }


    public String getMessage() {
        return message;
    }

    public void refresh_list(){
        EmployeeController temp= new EmployeeController();
        this.Employees=temp.getEmployees();
    }
    private String langue = "fr" ;

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
    public void ChangerLangue()  {
        if(this.langue.equals("fr")) {
            this.langue = "en";
        }
        else {
            this.langue = "fr";
        }


        FacesContext.getCurrentInstance().getViewRoot().setLocale( new Locale(langue));


    }

    public void refresh_bundle(){

        Locale currentLocal= FacesContext.getCurrentInstance().getViewRoot().getLocale();

       bundle = ResourceBundle.getBundle("i18n.messages" , currentLocal);

    }

}
