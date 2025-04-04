package com.rubinin;

import java.util.List;
import com.rubinin.DAOs.Course.CourseDAO;
import com.rubinin.DAOs.Course.CourseDAOImpl;
import com.rubinin.Course;

public class CourseService {
    public List<Course> getAllCoursesInSemester(int semesterID) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getCoursesBySemesterID(semesterID);
    }
    public List<Course> getAvailableCoursesForStudent(String studentID, int semesterID) { // This function is no longer used.
        CourseDAO courseDAO = new CourseDAOImpl();
        List<Course> allCoursesSemester = getCoursesBySemesterID(semesterID);;
        for (Course course : allCoursesSemester) {
            List<String> prerequisites = courseDAO.getPrerequisites(course.getCourseID());
            if (prerequisites != null && !prerequisites.isEmpty()) {
                for (String prerequisite : prerequisites) {
                    if (!courseDAO.hasStudentPassedCourse(studentID, prerequisite)) {
                        allCoursesSemester.remove(course);
                        break;
                    }
                }
            }
        }
        return allCoursesSemester;
    }
    public String hasStudentPassedAssumedKnowledge(String studentID, String courseID) { // Returns course which has not been passed or null.
        CourseDAO courseDAO = new CourseDAOImpl();
        List<String> assumedKnowledge = courseDAO.getAssumedKnowledge(courseID);
        String coursesNotPassed = "";
        if (assumedKnowledge != null && !assumedKnowledge.isEmpty()) {
            for (String assumedCourse : assumedKnowledge) {
                if (!courseDAO.hasStudentPassedCourse(studentID, assumedCourse)) {
                    coursesNotPassed += assumedCourse + ", ";
                }
            }
            return coursesNotPassed.length() > 0 ? coursesNotPassed : null;
        }
        return null;
    }
    public String hasStudentPassedPreReqKnowledge(String studentID, String courseID) { // Returns course which has not been passed or null.
        CourseDAO courseDAO = new CourseDAOImpl();
        List<String> preKnowledge = courseDAO.getPrerequisites(courseID);
        String coursesNotPassed = "";
        if (preKnowledge != null && !preKnowledge.isEmpty()) {
            for (String assumedCourse : preKnowledge) {
                if (!courseDAO.hasStudentPassedCourse(studentID, assumedCourse)) {
                    coursesNotPassed += assumedCourse + ", ";
                }
            }
            return coursesNotPassed.length() > 0 ? coursesNotPassed : null;
        }
        return null;
    }
    public List<Course> getCoursesBySemesterID(int semesterID) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getCoursesBySemesterID(semesterID);
    }
    public Course getCourseByCourseID(int semesterID, String courseID) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.getCourseOffering(semesterID, courseID);
    }
    public boolean isCourseFull(Course course) {
        CourseDAO courseDAO = new CourseDAOImpl();
        return courseDAO.isCourseFull(course);
    }
}
