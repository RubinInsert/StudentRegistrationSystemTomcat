package com.rubinin;

import java.io.Serializable;

public class Course implements Serializable {
    private int semesterID;
    private String courseID;
    private int maxCapacity;
    private String courseName;
    private int credits;
    public Course(int semesterID, String courseID, int maxCapacity, String courseName, int credits) {
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.maxCapacity = maxCapacity;
        this.courseName = courseName;
        this.credits = credits;
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
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
}