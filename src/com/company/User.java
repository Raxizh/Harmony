package com.company;

import com.company.Security.PasswordSecurity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class User {

    private String username;
    private String password;
    private String email;
    private int ID_number;

    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    //allows the password for the user to be set
    //todo: Make password reset function
    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordSecurity ps = new PasswordSecurity();
        this.password = ps.createPasswordHash(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID_number() {
        return ID_number;
    }

    public void setID_number(int ID_number) {
        this.ID_number = ID_number;
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }



}
