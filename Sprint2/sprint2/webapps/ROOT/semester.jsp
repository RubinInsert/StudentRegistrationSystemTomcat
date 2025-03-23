<%@ page import="com.rubinin.Semester" %>
<%@ page import="com.rubinin.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<jsp:useBean id="semesterService" class="com.rubinin.SemesterService" scope="session" />
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="dropdown-container">
        <h1>Select Option</h1>
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