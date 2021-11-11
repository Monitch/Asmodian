package com.example.demo.DBController;

import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.Model.Meeting;
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
    public List<Meeting> getAllMeetingOfCurrentUser(String email){
        List<Meeting> meeting = new ArrayList<Meeting>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM meetingdb WHERE email=?");

            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Meeting meet = new Meeting();
                meet.setId(resultSet.getInt("id"));
                meet.setName(resultSet.getString("name"));
                meet.setEmail(resultSet.getString("email"));
                meet.setDoctor(resultSet.getString("doctor"));
                meet.setApproved(resultSet.getString("approved"));
                meet.setDate(resultSet.getString("date"));
                meeting.add(meet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meeting;
    }
    public List<Meeting> getAllMeetingOfCurrentDoctor(String name){
        List<Meeting> meeting = new ArrayList<Meeting>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM meetingdb WHERE doctor=?");

            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Meeting meet = new Meeting();
                meet.setId(resultSet.getInt("id"));
                meet.setName(resultSet.getString("name"));
                meet.setEmail(resultSet.getString("email"));
                meet.setDoctor(resultSet.getString("doctor"));
                meet.setApproved(resultSet.getString("approved"));
                meet.setDate(resultSet.getString("date"));
                meeting.add(meet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meeting;
    }
    public void addNewMeeting(String name, String email, String doctor, String date){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO meetingdb (name, email, doctor, approved, date) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,doctor);
            preparedStatement.setString(4,"waiting");
            preparedStatement.setString(5,date);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User findById(int id){
        User user = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userbd WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            user = new User();

            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setNumber(resultSet.getString("number"));
            user.setPassword(resultSet.getString("password"));
            user.setDoctor(resultSet.getString("doctor"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
    public List<User> findByName(String name){
        List<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userbd WHERE name like ?");

            preparedStatement.setString(1,"%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

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
    public CurrentUser logIn(String email, String password){
        CurrentUser currentUser = new CurrentUser();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from userbd where email = ? and password = ?");
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                currentUser.setEmail(resultSet.getString("email"));
                currentUser.setDoctor(resultSet.getString("doctor"));
                currentUser.setName(resultSet.getString("name"));
            }
            return currentUser;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return currentUser;
    }
    public ArrayList<String> getAllDoctor(){
        ArrayList<String> doctorList = new ArrayList<>() ;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from userbd where doctor='doctor'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                 doctorList.add(resultSet.getString("name"));
            }
            return doctorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return doctorList;
    }


}