package com.example.demo.DBController;

import java.sql.*;

public class DataBaseController {
    private static final String URL = "jdbc:postgresql://localhost:5432/Asmodian";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "qweasdzxc";

    private String name;
    private String email;

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

    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void testDB (){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM test");
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            setName(resultSet.getString("name"));
            setEmail(resultSet.getString("email"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}