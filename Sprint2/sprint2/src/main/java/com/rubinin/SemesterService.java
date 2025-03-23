package com.rubinin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rubinin.DAOs.Semester.SemesterDAO;
import com.rubinin.DAOs.Semester.SemesterDAOImpl;

public class SemesterService implements Serializable {
    public SemesterService() {}
    public List<Semester> getAllAvailableSemesters() {
        SemesterDAO semesterDAO = new SemesterDAOImpl();
        List<Semester> semesters = semesterDAO.getAllSemesters();
        List<Semester> availableSemesters = new ArrayList<>();
        
        for (Semester semester : semesters) {
            if (semester.isOpenForEnrolment()) {
                availableSemesters.add(semester);
            }
        }
        
        return availableSemesters;
    }
    public Semester getSemesterByID(int id) {
        SemesterDAO semesterDAO = new SemesterDAOImpl();
        return semesterDAO.getSemesterByID(id);
    }
}
