<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="login-container">
        <h1>Confirmed Enrollment!</h1>
        <p>You have successfully enrolled in the following courses: </p>
        <ul>
            <% 
            String selectedCourses = (String) session.getAttribute("selectedCourses"); // Fetch selected courses from session item
            if (selectedCourses != null && !selectedCourses.isEmpty()) {
                String[] courses = selectedCourses.split(","); // Splits string from "SENG1010,SENG2020" to ["SENG1010", "SENG2020"]
                for (String course : courses) {
            %>
                <li><%= course.trim() %></li>
            <% 
                }
            } else { 
            %>
            <li>No courses selected.</li>
            <% 
            } 
            %>
        </ul>
    </div>
    <script>
        <% if (request.getAttribute("loginFailed") != null) { %>
            alert("Login failed!");
        <% } %>
    </script>
</body>
</html>