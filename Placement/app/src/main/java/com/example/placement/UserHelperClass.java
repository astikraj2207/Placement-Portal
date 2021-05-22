package com.example.placement;

public class UserHelperClass {
    String username;
    String password;
    String name;
    int isStudent;

    public UserHelperClass(String username, String password,String name, int isStudent) {
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
        this.name = name;
    }

    public UserHelperClass() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudent() {
        return isStudent;
    }

    public void setStudent(int student) {
        isStudent = student;
    }
}
