package com.mlxc.parammodel;

import com.mlxc.entity.User;

public class RegisterModel {

    private String userName;
    private String password;
    private String name;
    private String id;
    private int userType;


    public RegisterModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public User createUser() {
        User user = new User();

        user.setName(this.getName());
        user.setPassword(this.getPassword());
        user.setId(this.getId());
        user.setUserName(this.getUserName());
        user.setUserType(this.getUserType());

        return user;
    }

}
