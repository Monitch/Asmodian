package com.example.demo.DBController;

import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.Model.*;

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
    public void registrationDoctor(String name,String email,String number,String password){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO userbd (name, email, number, password, doctor) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,number);
            preparedStatement.setString(4,password);
            preparedStatement.setString(5,"doctor");
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                currentUser.setNumber(resultSet.getString("number"));
            }
            return currentUser;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return currentUser;
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
    public List<User> findAllPatientOfUser(String name){
        ArrayList<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM userbd WHERE doctor =? ");

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setNumber(resultSet.getString("number"));
                user.setPassword(resultSet.getString("password"));
                user.setDoctor(resultSet.getString("doctor"));
                userList.add(user);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
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
                    connection.prepareStatement("SELECT * FROM userbd WHERE LOWER(name) like ?");

            preparedStatement.setString(1,"%" + name.toLowerCase() + "%");

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

    public ArrayList<ListForDropDown> getAllDoctor(){
        ArrayList<ListForDropDown> doctorList = new ArrayList<>() ;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from userbd where doctor='doctor'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                ListForDropDown listForDropDown = new ListForDropDown();
                listForDropDown.setName(resultSet.getString("name"));
                listForDropDown.setNumber(resultSet.getString("number"));
                doctorList.add(listForDropDown);
            }
            return doctorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return doctorList;
    }

    public ArrayList<ListForDropDown> getAllPatient(String doctor){
        ArrayList<ListForDropDown> doctorList = new ArrayList<>() ;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from userbd where doctor=?");
            preparedStatement.setString(1,doctor);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                ListForDropDown list = new ListForDropDown();
                list.setName(resultSet.getString("name"));
                list.setNumber(resultSet.getString("number"));
                doctorList.add(list);
            }
            return doctorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return doctorList;
    }
    public List<Meeting> getAllMeetingOfCurrentUser(String email){
        List<Meeting> meeting = new ArrayList<Meeting>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM meetingdb WHERE email=? ORDER BY id");

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
                    connection.prepareStatement("SELECT * FROM meetingdb WHERE doctor=? ORDER BY id");

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

    public void updateMeeting(String value, int id){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE meetingdb set approved = ? where id=?");
            preparedStatement.setString(1,value);
            preparedStatement.setInt(2,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public String getUserByNumber(String number){
        String res=null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from userbd where number =?");
            preparedStatement.setString(1,number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                res=resultSet.getString("name");
            }
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;

    }
    public void setDisease(Disease disease){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO disease (name, number, doctor, disease, medicine) values (?,?,?,?,?)");
            preparedStatement.setString(1,disease.getName());
            preparedStatement.setString(2,disease.getNumber());
            preparedStatement.setString(3,disease.getDoctor());
            preparedStatement.setString(4,disease.getDisease());
            preparedStatement.setString(5, disease.getMedicine());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public Disease getDisease(String number){
        Disease disease= new Disease();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM disease where number=?");
            preparedStatement.setString(1,number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                disease.setId(resultSet.getInt("id"));
                disease.setName(resultSet.getString("name"));
                disease.setNumber(resultSet.getString("number"));
                disease.setDoctor(resultSet.getString("doctor"));
                disease.setDisease(resultSet.getString("disease"));
                disease.setMedicine(resultSet.getString("medicine"));
            }
            return disease;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return disease;
    }
    public String checkDisease(String name, String doctor){
        String res = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from disease where name = ? and doctor = ?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,doctor);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                res=resultSet.getString("name");
            }
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
    public void updateDisease(Disease disease){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "update disease set disease = ?, medicine = ? WHERE name = ? and doctor = ?;");
            preparedStatement.setString(1,disease.getDisease());
            preparedStatement.setString(2,disease.getMedicine());
            preparedStatement.setString(3,disease.getName());
            preparedStatement.setString(4,disease.getDoctor());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public String checkInfoAbout(String name){
        String res = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select * from infodb where name = ?");
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                res=resultSet.getString("name");
            }
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public void setInfo(String name, String email, String number,String education, String experience,String awards, String aboutme){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO infodb (name, email, number, education, experience, awards, aboutme) values (?,?,?,?,?,?,?) ;");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,number);
            preparedStatement.setString(4,education);
            preparedStatement.setString(5,experience);
            preparedStatement.setString(6,awards);
            preparedStatement.setString(7,aboutme);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateInfo(String name,String education, String experience,String awards, String aboutme){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "update infodb set education = ?, experience = ?, awards = ?, aboutme = ? WHERE name = ?;");
            preparedStatement.setString(1,education);
            preparedStatement.setString(2,experience);
            preparedStatement.setString(3,awards);
            preparedStatement.setString(4,aboutme);
            preparedStatement.setString(5,name);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Info> getInfoAboutDoctor(String name){
        List<Info> infoList = new ArrayList<Info>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM infoDB WHERE name=? ORDER BY id");

            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Info info = new Info();
                info.setId(resultSet.getInt("id"));
                info.setName(resultSet.getString("name"));
                info.setEmail(resultSet.getString("email"));
                info.setNumber(resultSet.getString("number"));
                info.setEducation(resultSet.getString("education"));
                info.setExperience(resultSet.getString("experience"));
                info.setAwards(resultSet.getString("awards"));
                info.setAboutme(resultSet.getString("aboutme"));
                infoList.add(info);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoList;
    }
}