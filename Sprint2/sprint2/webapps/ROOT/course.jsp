<%@ page import="com.rubinin.Semester" %>
<%@ page import="com.rubinin.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<jsp:useBean id="courseService" class="com.rubinin.CourseService" scope="session" />
<html>
<head>
    <title>Course Information</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script src="scripts/course.js"></script>
</head>
<body>
    <div class="course-container">
        <h1>Course Information</h1>

        <%
            // Fetch the selected semester ID from the session
            int selectedSemesterID = -1;
            List<Course> courses = null;

            try {
                System.out.println("Fetching semester ID from session: " + session.getAttribute("semester"));
                selectedSemesterID = Integer.parseInt((String) session.getAttribute("semester"));
            } catch (NumberFormatException e) {
                selectedSemesterID = -1; // Default to -1 if parsing fails
            }

            // Fetch courses for the selected semester
            if (selectedSemesterID != -1) {
                courses = courseService.getCoursesBySemesterID(selectedSemesterID);
            }
        %>

        <form id="courseForm" action="/confirm" method="post">
            <label for="courseCode">Course Code:</label>
            <input type="text" id="courseCode" list="courseCodes" oninput="filterCourses()">

            <!-- Datalist for course codes -->
            <datalist id="courseCodes">
                <% if (courses != null) { %>
                    <% for (Course course : courses) { %>
                        <option id="<%= course.getCourseID()%>" data-value='{"ID":"<%= course.getCourseID() %>", "name":"<%= course.getCourseName() %>", "maxCapacity":<%= course.getMaxCapacity() %>, "units":<%= course.getCredits() %>}' value="<%= course.getCourseID() %>"></option>
                    <% } %>
                <% } else { %>
                    <option value="" disabled>No courses available</option>
                <% } %>
            </datalist>

            <button type="button" onclick="addCourse()">Add Course</button>

            <!-- Table for selected courses -->
            <table id="courseTable">
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Max Capacity</th>
                    <th>Units</th>
                </tr>
            </table>

            <input type="hidden" id="selectedCourses" name="selectedCourses">
            <input type="submit" value="Submit">

        </form>
    </div>
    <script>
    var chosenCourseUnits = 0;
    function addCourse() {
        var course = {
            ID: null,
            name: null,
            maxCapacity: null,
            units: null
        };
        enteredCourseCode = document.getElementById('courseCode').value;
        var selectedCourse = null;
        var findCourse = document.getElementById(enteredCourseCode);
        if (findCourse) {
            selectedCourse = findCourse.getAttribute('data-value');
        } else {
            alert('Course not found in the list. Please select a valid course.');
            return;
        }
        course = JSON.parse(selectedCourse);
        if (course.ID !== null) {
            if(chosenCourseUnits + parseInt(course.units) > 40) {
                alert('You cannot select more than 40 units in total.');
                return;
            }
            chosenCourseUnits += parseInt(course.units);

            var selectedCourses = document.getElementById('selectedCourses').value;
            var selectedCoursesArray = selectedCourses ? selectedCourses.split(',') : [];
            selectedCoursesArray.push(course.ID);
            document.getElementById('selectedCourses').value = selectedCoursesArray.join(',');



            var table = document.getElementById('courseTable');
            var row = table.insertRow();
            row.insertCell(0).innerHTML = course.ID;
            row.insertCell(1).innerHTML = course.name;
            row.insertCell(2).innerHTML = course.maxCapacity;
            row.insertCell(3).innerHTML = course.units;


        } else {
            alert('Course not found');
        }
    }
</script>
</body>
</html>