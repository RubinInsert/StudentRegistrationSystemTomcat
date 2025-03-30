package com.rubinin.Servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rubinin.Course;
import com.rubinin.CourseService;
import com.rubinin.Student;
import com.rubinin.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ConfirmEnrollmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
        } else {
            resp.getWriter().println("No active session found.");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession(false).setAttribute("selectedCourses", req.getParameter("selectedCourses"));
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        int selectedSemesterID = Integer.parseInt((String) req.getSession(false).getAttribute("semester"));
        Student student = studentService.getStudentByStdNo((String) req.getSession(false).getAttribute("username"));
        String selectedCoursesParam = req.getParameter("selectedCourses");
        if (selectedCoursesParam == null || selectedCoursesParam.isEmpty()) {
            try {
                resp.getWriter().println("No courses selected. Please select at least one course.");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> selectedCoursesStrings = Arrays.asList(selectedCoursesParam.split(","));
        List<Course> selectedCourses = new ArrayList<Course>();
        selectedCoursesStrings.forEach(courseCode -> {
            selectedCourses.add(courseService.getCourseByCourseID(selectedSemesterID, courseCode));
            System.out.println(courseService.getCourseByCourseID(selectedSemesterID, courseCode));
        });
        int totalSelectedCourseUnits = 0;
        for (Course course : selectedCourses) {
            totalSelectedCourseUnits += course.getCredits();
        }
        for(Course course : studentService.getCoursesByStudent(student)) {
            totalSelectedCourseUnits += course.getCredits();
        }
        if(totalSelectedCourseUnits > 40) {
            try {
                resp.getWriter().println("You have exceeded the maximum course units allowed. Please select fewer courses.");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Course course : selectedCourses) {
            for(Course enrolledCourse : studentService.getCoursesByStudent(student)) {
                if(course.getCourseID().equals(enrolledCourse.getCourseID())) {
                    try {
                        resp.getWriter().println("You are already enrolled in " + course.getCourseID() + ". Please select different courses.");
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        selectedCourses.forEach(course -> {
            studentService.enrollStudent(student, course);
        });
       
        try {
            resp.sendRedirect("/confirm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}