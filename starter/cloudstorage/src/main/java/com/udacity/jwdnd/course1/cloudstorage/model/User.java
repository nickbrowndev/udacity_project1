package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {

    private Integer userId;
    private String username;
    private String salt;
    private String password;
    private String firstName;

    public User() {
    }

    private String lastName;

    public User(Integer userId, String username, String encodedSalt,
                String hashedPassword, String firstName,
                String lastName) {
        this.setUserId(userId);
        this.setUsername(username);
        this.setSalt(encodedSalt);
        this.setPassword(hashedPassword);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return this.salt;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

