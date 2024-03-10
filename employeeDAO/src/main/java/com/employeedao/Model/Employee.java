package com.employeedao.Model;


import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String department;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", mail='" + mail + '\'' +
                ", editable=" + editable +
                '}';
    }

    private String mail;
    private boolean editable;

    public Employee(int id, String name, String department, String mail) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.mail = mail;
        this.editable = false;
    }
    public Employee(){}
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}