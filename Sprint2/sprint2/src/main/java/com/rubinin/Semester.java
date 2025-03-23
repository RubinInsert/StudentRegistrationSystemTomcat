package com.rubinin;

import java.io.Serializable;

public class Semester implements Serializable {
    private int semesterID;
    private int semester;
    private int year;
    private boolean openForEnrolment;

    // Constructors
    public Semester() {}

    public Semester(int semesterID, int semester, int year, boolean openForEnrolment) {
        this.semesterID = semesterID;
        this.semester = semester;
        this.year = year;
        this.openForEnrolment = openForEnrolment;
    }

    // Getters and Setters
    public int getSemesterID() { return semesterID; }
    public void setSemesterID(int semesterID) { this.semesterID = semesterID; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getPublicName() { return "Semester " + semester + " " + year; }

    public boolean isOpenForEnrolment() { return openForEnrolment; }
    public void setOpenForEnrolment(boolean openForEnrolment) { this.openForEnrolment = openForEnrolment; }
}