<%@ page import="com.rubinin.Semester" %>
<%@ page import="com.rubinin.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<jsp:useBean id="semesterService" class="com.rubinin.SemesterService" scope="session" />
<jsp:useBean id="courseService" class="com.rubinin.CourseService" scope="session" />
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="course-container">
        <h1>Course Information</h1>
        
        <form id="courseForm" action="/confirm" method="post">
            <label for="courseCode">Course Code:</label>
            <input type="text" id="courseCode" list="courseCodes" oninput="filterCourses()">
            <datalist id="courseCodes">
            <% 
            int selectedSemesterID = -1;
            selectedSemesterID = Integer.parseInt((String) session.getAttribute("semester"));
            Semester chosenSemester = null;
            List<Course> courses = null;
            if (selectedSemesterID != -1) {
                    courses = courseService.getCoursesBySemesterID(selectedSemesterID);
                    for (Course course : courses) {
            %>
            <option value="<%= course.getCourseID() %>"><%= course.getCourseID() %></option>
            <% 
                    }
            }
            %>
            </datalist>
            <button type="button" onclick="addCourse()">Add Course</button>
        
            <table id="courseTable">
                <tr>
                    <th>Course ID</th>
                    <th>Max Capacity</th>
                    <th>Units</th>
                </tr>
            </table>
            <input type="hidden" id="selectedCourses" name="selectedCourses">
            <input type="submit" value="Submit">
        </form>

        <script>
            function addCourse() {
                var courseCode = document.getElementById('courseCode').value;
                var courseName = '';
                var maxCapacity = '';
                var units = '10';

                <% 
                    if (selectedSemesterID != -1) {
                        for (Course course : courses) {
                %>
                if (courseCode === '<%= course.getCourseID() %>') {
                    courseName = '<%= course.getCourseID() %>';
                    maxCapacity = '<%= course.getMaxCapacity() %>';
                }
                <% 
                        }
                    }
                %>

                if (courseName !== '') {
                    var table = document.getElementById('courseTable');
                    var row = table.insertRow();
                    row.insertCell(0).innerHTML = courseCode;
                    row.insertCell(1).innerHTML = maxCapacity;
                    row.insertCell(2).innerHTML = units;

                    var selectedCourses = document.getElementById('selectedCourses').value;
                    var selectedCoursesArray = selectedCourses ? selectedCourses.split(',') : [];
                    selectedCoursesArray.push(courseCode);
                    document.getElementById('selectedCourses').value = selectedCoursesArray.join(',');
                } else {
                    alert('Course not found');
                }
            }

            function filterCourses() {
                // This function can be used to filter the datalist options if needed
            }
        </script>
    </div>
</body>
</html>
