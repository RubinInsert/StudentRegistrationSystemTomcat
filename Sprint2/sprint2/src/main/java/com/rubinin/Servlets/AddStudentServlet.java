package com.rubinin.Servlets;
import java.io.IOException;

import com.rubinin.StudentService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/addStudent.html");
        requestDispatcher.forward(req, resp);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");

        // Retrieve the 'name' parameter from the GET request
        String stdNo = request.getParameter("studentNo");
        String givenNames = request.getParameter("givenNames");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        try {
            // Add student
            StudentService stdServervice = new StudentService();
            stdServervice.addStudent(stdNo, givenNames, lastName, password);
         
            // Forward to success page
            String message = new String("Student was  added!");
            response.getWriter().println(message);

        } catch (Exception e)
        {
            String errorMessage = new String("Student was not added");
            response.getWriter().println(errorMessage);       
        }

    }
}