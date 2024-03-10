package com.employeedao.DAO;

import com.employeedao.Model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    DB db = new DB();


    @Override
    public void addEmployee(Employee employee) {
        db=new DB();
        String sql = "INSERT INTO Employee (ID, name, department, mail) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = db.getcon().prepareStatement(sql)) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getDepartment());
            statement.setString(4, employee.getMail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db.closecon();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        db=new DB();
        String sql = "UPDATE Employee SET name = ?, department = ?, mail = ? WHERE ID = ?";
        try (PreparedStatement statement = db.getcon().prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setString(3, employee.getMail());
            statement.setInt(4, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            db.closecon();
        }
    }

    @Override
    public void deleteEmployee(Employee employee) {
        db=new DB();
        String sql = "DELETE FROM Employee WHERE ID = ?";
        try (PreparedStatement statement = db.getcon().prepareStatement(sql)) {
            statement.setInt(1, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closecon();
        }
    }

    @Override
    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM Employee WHERE ID = ?";
        try (PreparedStatement statement = db.getcon().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Employee(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("department"), resultSet.getString("mail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closecon();
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (PreparedStatement statement = db.getcon().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("department"), resultSet.getString("mail")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db.closecon();
        }
        return employees;
    }

    public boolean isEmailUnique(String email) {
        db = new DB();
        String query = "SELECT COUNT(*) FROM Employee WHERE mail = ?";
        try (Connection conn = db.getcon();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Returns true if count is 0, indicating the email is unique
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closecon();
        }
        return false; // Default to false in case of an exception
    }
}
