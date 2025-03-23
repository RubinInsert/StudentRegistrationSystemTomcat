package com.rubinin;

import java.util.List;
import com.rubinin.DAOs.Course.CourseDAO;
import com.rubinin.DAOs.Course.CourseDAOImpl;
import com.rubinin.Course;

public class CourseService {
    public List<Course> getAllCourses() {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getAllCourseOfferings();
    }
    public List<Course> getCoursesBySemesterID(int semesterID) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getCoursesBySemesterID(semesterID);
    }
    public Course getCourseByCourseID(int semesterID, String courseID) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getCourseOffering(semesterID, courseID);
    }
}
