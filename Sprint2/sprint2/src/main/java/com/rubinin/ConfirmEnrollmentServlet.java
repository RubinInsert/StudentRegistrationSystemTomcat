package com.rubinin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> selectedCoursesStrings = Arrays.asList(selectedCoursesParam.split(","));
        List<Course> selectedCourses = new ArrayList<Course>();
        selectedCoursesStrings.forEach(courseCode -> {
            selectedCourses.add(courseService.getCourseByCourseID(selectedSemesterID, courseCode));
            System.out.println(courseService.getCourseByCourseID(selectedSemesterID, courseCode));
        });
        selectedCourses.forEach(course -> {
            studentService.enrollStudent(student, course);
        });
        //studentService.enrollStudent(, req.getParameter("selectedCourses")));
        try {
            resp.sendRedirect("/confirm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}