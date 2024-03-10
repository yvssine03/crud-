package com.employeedao.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB{

    private Connection con;

    public Connection getcon(){
        return con;
    }

    public  DB() {

        String host = "mysql-2fc8ac31-yassineifghane1234-cd8c.a.aivencloud.com";
        String port = "27569";
        String databaseName = "defaultdb";
        String userName = "avnadmin";
        String password = "AVNS_UpOgP0oF5yZGAkghH29";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void closecon(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}