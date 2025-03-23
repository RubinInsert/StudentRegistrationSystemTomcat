package com.rubinin.DAOs.Semester;

import java.util.List;

import com.rubinin.Semester;

public interface SemesterDAO {
    void addSemester(Semester semester);
    Semester getSemesterByID(int semesterID);
    List<Semester> getAllSemesters();
    void updateSemester(Semester semester);
    void deleteSemester(int semesterID);
}
