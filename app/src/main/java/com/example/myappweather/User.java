package com.example.myappweather;

public class User {
    String name, cn, email, pass;

    public User(String name, String cn, String email, String pass) {
        this.name = name;
        this.cn = cn;
        this.email = email;
        this.pass = pass;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getCn() {
        return cn;
    }

    public String getPass() {
        return pass;
    }


}
