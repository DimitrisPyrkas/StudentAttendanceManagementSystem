package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String fullName;
    private String email;
    private List<Integer> registeredCoursesIds;

    public Student(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.registeredCoursesIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getRegisteredCoursesIds() {
        return registeredCoursesIds;
    }

    public void setRegisteredCoursesIds(List<Integer> registeredCoursesIds) {
        this.registeredCoursesIds = registeredCoursesIds;
    }
}
