package com.sakshamnegi.yef.blooddonationapp;

public class UserData {

    private String email;
    private String name;
    private String city;


    public UserData(){

    }

    public UserData(String email, String name, String city) {
        this.email = email;
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
}
