package com.example.demo.DBController;

import com.example.demo.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    private static final String URL = "jdbc:postgresql://localhost:5432/Asmodian";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "qweasdzxc";

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
    public List<User> index(){
        List<User> users = new ArrayList<User>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM userbd ORDER BY id";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                User userr = new User();
                userr.setId(resultSet.getInt("id"));
                userr.setName(resultSet.getString("name"));
                userr.setEmail(resultSet.getString("email"));
                userr.setNumber(resultSet.getString("number"));
                userr.setPassword(resultSet.getString("password"));
                userr.setDoctor(resultSet.getString("doctor"));
                users.add(userr);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
    public void registration(User user){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO userbd (name, email, number, password, doctor) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getNumber());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5,user.getDoctor());


            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}