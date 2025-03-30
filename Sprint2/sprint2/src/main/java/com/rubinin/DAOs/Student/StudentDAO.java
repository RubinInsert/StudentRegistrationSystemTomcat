package com.rubinin.DAOs.Student;

import java.util.List;

import com.rubinin.Course;
import com.rubinin.Student;

public interface StudentDAO {
    void addStudent(Student student);
    Student getStudentByStdNo(String stdNo);
    List<Student> getAllStudents();
    void updateStudent(Student student);
    void deleteStudent(String stdNo);
    void enrollStudent(Student student, Course course);
    List<Course> getCoursesByStudent(Student student);
} 