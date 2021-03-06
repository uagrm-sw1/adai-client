package com.example.software_cliente.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lastname")
    @Expose
    private String lastName;

    @SerializedName("gender")
    @Expose
    private boolean gender;

    @SerializedName("date_of_birth")
    @Expose
    private String birthday;

    @SerializedName("password")
    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getYear() {
        return Integer.valueOf(birthday.substring(0, 4));
    }

    public int getMonth() {
        return Integer.valueOf(birthday.substring(5, 7)) - 1;
    }

    public int getDay() {
        return Integer.valueOf(birthday.substring(8));
    }

    public void setBirthday(int year, int month, int day) {
        String birth = year + "-" +
                ((month + 1 < 10) ? "0" + (month + 1) : (month + 1)) + "-" +
                ((day < 10) ? "0" + day : day);
        setBirthday(birth);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
