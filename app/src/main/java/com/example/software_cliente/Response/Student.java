package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_profile")
    @Expose
    private int userId;

    @SerializedName("student_name")
    @Expose
    private String name;

    @SerializedName("student_lastname")
    @Expose
    private String lastName;

    @SerializedName("date_of_birth")
    @Expose
    private String birthday;

    @SerializedName("gender")
    @Expose
    private boolean gender;

    boolean expanded = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
