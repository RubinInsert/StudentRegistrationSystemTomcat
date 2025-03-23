package com.rubinin;

import java.io.Serializable;

public class Course implements Serializable {
    private int semesterID;
    private String courseID;
    private int maxCapacity;

    public Course(int semesterID, String courseID, int maxCapacity) {
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.maxCapacity = maxCapacity;
    }

    // Getters and setters
    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}