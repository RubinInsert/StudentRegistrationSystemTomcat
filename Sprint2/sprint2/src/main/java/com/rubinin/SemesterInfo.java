package com.rubinin;

import java.io.Serializable;

public class SemesterInfo implements Serializable {
    private Semester[] availableSemesters = {new Semester("Semester 1", new Course[] {
            new Course("Programming Fundamentals", "COMP115", "None", 6),
            new Course("Introduction to Information Technology", "COMP125", "None", 6),
            new Course("Introduction to Algorithms and Data Structures", "COMP225", "COMP115", 6),
            new Course("Introduction to Computer Systems", "COMP229", "COMP115", 6),
            new Course("Introduction to Web Development", "COMP235", "COMP115", 6),
            new Course("Introduction to Database Management", "COMP249", "COMP115", 6),
            new Course("Introduction to Networks and Security", "COMP255", "COMP115", 6),
            new Course("Introduction to Artificial Intelligence", "COMP265", "COMP115", 6),
            new Course("Introduction to Software Engineering", "COMP275", "COMP115", 6),
            new Course("Introduction to Data Science", "COMP285", "COMP115", 6),
            new Course("Introduction to Machine Learning", "COMP295", "COMP115", 6),
            new Course("Introduction to Cybersecurity", "COMP305", "COMP115", 6),
            new Course("Introduction to Cloud Computing", "COMP315", "COMP115", 6),
            new Course("Introduction to Internet of Things", "COMP325", "COMP115", 6),
            new Course("Introduction to Blockchain", "COMP335", "COMP115", 6),
            new Course("Introduction to Quantum Computing", "COMP345", "COMP115", 6),
            new Course("Introduction to Augmented Reality", "COMP355", "COMP115", 6),
            new Course("Introduction to Virtual Reality", "COMP365", "COMP115", 6),
            new Course("Introduction to Cryptography", "COMP375", "COMP115", 6),
            new Course("Introduction to Robotics", "COMP385", "COMP115", 6),
            new Course("Introduction to Biotechnology", "COMP395", "COMP115", 6),
            new Course("Introduction to Nanotechnology", "COMP405", "COMP115", 6),
            new Course("Introduction to Quantum Mechanics", "COMP415", "COMP115", 6),
            new Course("Introduction to General Relativity", "COMP425", "COMP115", 6)})
        
        , new Semester("Semester 2", new Course[] {
            new Course("Advanced Programming", "COMP215", "COMP115", 6),
            new Course("Data Structures and Algorithms", "COMP225", "COMP125", 6),
            new Course("Operating Systems", "COMP235", "COMP225", 6),
            new Course("Database Systems", "COMP245", "COMP125", 6),
            new Course("Computer Networks", "COMP255", "COMP125", 6),
            new Course("Software Engineering", "COMP265", "COMP125", 6),
            new Course("Artificial Intelligence", "COMP275", "COMP125", 6),
            new Course("Machine Learning", "COMP285", "COMP125", 6),
            new Course("Cybersecurity", "COMP295", "COMP125", 6),
            new Course("Cloud Computing", "COMP305", "COMP125", 6),
            new Course("Internet of Things", "COMP315", "COMP125", 6),
            new Course("Blockchain Technology", "COMP325", "COMP125", 6),
            new Course("Quantum Computing", "COMP335", "COMP125", 6),
            new Course("Augmented Reality", "COMP345", "COMP125", 6),
            new Course("Virtual Reality", "COMP355", "COMP125", 6),
            new Course("Cryptography", "COMP365", "COMP125", 6),
            new Course("Robotics", "COMP375", "COMP125", 6),
            new Course("Biotechnology", "COMP385", "COMP125", 6),
            new Course("Nanotechnology", "COMP395", "COMP125", 6),
            new Course("Quantum Mechanics", "COMP405", "COMP125", 6),
            new Course("General Relativity", "COMP415", "COMP125", 6)
        })};
    public SemesterInfo() {}
    public Semester getSemesterByName(String semesterName) {
        for (Semester semester : availableSemesters) {
            if (semester.getSemesterName().equals(semesterName)) {
                return semester;
            }
        }
        return null;
    }
    public String[] getAvailableSemesterNames() {
        String[] semesterNames = new String[availableSemesters.length];
        for (int i = 0; i < availableSemesters.length; i++) {
            semesterNames[i] = availableSemesters[i].getSemesterName();
        }
        return semesterNames;
    }
}
