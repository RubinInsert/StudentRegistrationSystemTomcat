package com.rubinin.DAOs.Course;

import java.sql.SQLException;
import java.util.List;
import com.rubinin.Course;
public interface CourseDAO {
void insertCourseOffering(int semesterID, String courseID, int maxCapacity);
void updateCourseOffering(int semesterID, String courseID, int maxCapacity);
void deleteCourseOffering(int semesterID, String courseID);
Course getCourseOffering(int semesterID, String courseID);
List<Course> getAllCourseOfferings();
List<Course> getCoursesBySemesterID(int semesterID);
List<String> getPrerequisites(String courseID);
List<String> getAssumedKnowledge(String courseID);
boolean isCourseFull(Course course);
boolean hasStudentPassedCourse(String studentID, String courseID);
}