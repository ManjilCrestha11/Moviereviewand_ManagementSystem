package com.movie.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/MovieSystem";
    private static final String USER = "root"; // 
    private static final String PASSWORD = "ManjilLol12345"; // 

    public static Connection getConnection() {
        Connection connection = null;
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the Movie Database successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed! Check your password or driver.");
            e.printStackTrace();
        }
        return connection;
    }
}