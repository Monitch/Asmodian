package com.example.demo.Model;

public class User {
    private int id;
    private String name;
    private String email;
    private String number;
    private String password;
    private String doctor;

    public User() {
    }

    public User(int id, String name, String email, String number, String password, String doctor) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.doctor = doctor;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
