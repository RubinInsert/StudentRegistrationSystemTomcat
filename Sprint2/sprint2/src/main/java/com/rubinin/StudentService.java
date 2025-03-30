package com.rubinin;

import java.util.List;

import com.rubinin.DAOs.Student.StudentDAO;
import com.rubinin.DAOs.Student.StudentDAOImpl;

public class StudentService {
    
    private StudentDAO studentDAO = new StudentDAOImpl();
   
    /*
     * Business logic to authenticate a student
     * - Returns null if the student is not authenticated,
     * - Otherwise return the student object
     * */
    public Student authenticateStudent(String stdNo, String password)
    {
        Student s  = studentDAO.getStudentByStdNo(stdNo); // Finds the student based on stdNo
        
        if (s!=null)
        {
            PasswordSecurity pSec = new PasswordSecurity();
            if (pSec.verifyPassword(password, s)) // Checks if passwords match
            {
                return s;
            }
        }
        return null;
    }
    public void enrollStudent(Student student, Course course)
    {
        studentDAO.enrollStudent(student, course);
    }
    public Student getStudentByStdNo(String stdNo)
    {
        return studentDAO.getStudentByStdNo(stdNo);
    }
    public void addStudent(String stdNo, String givenNames, String lastName, String password)
    {
        // Generate salt and password hash
        PasswordSecurity pSec = new PasswordSecurity();
        Double salt = pSec.generateSalt();
        String passwordHash = pSec.hashPassword(password, salt);

        // Create student object and add to database
        Student student  = new Student(stdNo, givenNames, lastName, passwordHash, salt);
        System.err.println(student);
        studentDAO.addStudent(student);
    }
    public List<Course> getCoursesByStudent(Student student) {
        return studentDAO.getCoursesByStudent(student);
    }
}