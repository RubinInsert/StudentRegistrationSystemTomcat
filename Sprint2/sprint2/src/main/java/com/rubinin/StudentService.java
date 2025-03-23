package com.rubinin;

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
            if (password.equals(s.getPasswordHash())) // Checks if passwords match
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
}