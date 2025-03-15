<%@ page import="com.rubinin.Semester" %>
<%@ page import="com.rubinin.Course" %>
<!DOCTYPE html>
<jsp:useBean id="semesterInfo" class="com.rubinin.SemesterInfo" scope="session" />
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
                    String[] semesterNames = semesterInfo.getAvailableSemesterNames();
                    for (String semesterName : semesterNames) {
                %>
                <option value="<%= semesterName %>"><%= semesterName %></option>
                <% 
                    }
                %>
            </select><br><br>
            <input type="submit" value="Continue">
        </form>
    </div>
</body>
</html>