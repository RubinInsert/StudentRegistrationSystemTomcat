package com.rubinin;

import java.io.Serializable;

public class Semester implements Serializable {
    private String semesterName;
    private Course[] availableCourses;
    // Getters and setters
    public Semester(String name, Course[] courses) {
        this.semesterName = name;
        this.availableCourses = courses;
    }
    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
    public Course[] getAvailableCourses() {
        return availableCourses;
    }
    public void setAvailableCourses(Course[] availableCourses) {
        this.availableCourses = availableCourses;
    }
}