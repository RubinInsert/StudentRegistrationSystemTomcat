<%@ page import="com.rubinin.Semester" %>

<%@ page import="com.rubinin.Student" %>
<%@ page import="com.rubinin.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<jsp:useBean id="semesterService" class="com.rubinin.SemesterService" scope="session" />
<jsp:useBean id="studentService" class="com.rubinin.StudentService" scope="session" />
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="dropdown-container">
        <% String studentID = (String) session.getAttribute("username"); 
              Student student = studentService.getStudentByStdNo(studentID);%>
        <h1>Welcome back <%= student.getGivenNames() %>!</h1>
        <h2>Select Option</h2>
        <form action="semester" method="post">
            <label for="semester">Options:</label>
            <select id="semester" name="semester">
                <% 
                    List<Semester> availableSemesters = semesterService.getAllAvailableSemesters();
                    for (Semester semester : availableSemesters) {
                %>
                <option value="<%= semester.getSemesterID() %>"><%= semester.getPublicName() %></option>
                <% 
                    }
                %>
            </select><br><br>
            <input type="submit" value="Continue">
        </form>
    </div>
</body>
</html>