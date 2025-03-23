package com.rubinin;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentService studentService = new StudentService();
        Student validatedStudent = studentService.authenticateStudent(username, password);
        if (validatedStudent != null) {
            out.println("Login successful!");
            // Store username in the session
            req.getSession().setAttribute("username", username);
            // Create a Java Bean object for semester information
            // SemesterInfo semesterInfo = new SemesterInfo();
            // semesterInfo.setSemester("Fall 2023");
            // semesterInfo.setCourses(Arrays.asList("SENG2050", "COMP1010", "MATH2001"));

            // Set the bean in the request scope
            //req.setAttribute("semesterInfo", semesterInfo);
            // Forward to the semester JSP page
            try {
                resp.sendRedirect("/semester");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("loginFailed", true);
            try {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }   
    }
}