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
    <div class="course-container">
        <h1>Course Information</h1>
        
        <form id="courseForm" action="/confirm" method="post">
            <label for="courseCode">Course Code:</label>
            <input type="text" id="courseCode" list="courseCodes" oninput="filterCourses()">
            <datalist id="courseCodes">
            <% 
            String selectedSemester = (String) session.getAttribute("semester");
            Semester chosenSemester = null;
            Course[] courses = null;
            if (selectedSemester != null) {
                chosenSemester = semesterInfo.getSemesterByName(selectedSemester);
                if (chosenSemester != null) {
                    courses = chosenSemester.getAvailableCourses();
                    for (Course course : courses) {
            %>
            <option value="<%= course.getCode() %>"><%= course.getName() %></option>
            <% 
                    }
                }
            }
            %>
            </datalist>
            <button type="button" onclick="addCourse()">Add Course</button>
        
            <table id="courseTable">
                <tr>
                    <th>Course Name</th>
                    <th>Course Code</th>
                    <th>Assumed Knowledge</th>
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
                var assumedKnowledge = '';
                var units = '';

                <% 
                    if (selectedSemester != null && chosenSemester != null) {
                        for (Course course : courses) {
                %>
                if (courseCode === '<%= course.getCode() %>') {
                    courseName = '<%= course.getName() %>';
                    assumedKnowledge = '<%= course.getAssumedKnowledge() %>';
                    units = '<%= course.getUnits() %>';
                }
                <% 
                        }
                    }
                %>

                if (courseName !== '') {
                    var table = document.getElementById('courseTable');
                    var row = table.insertRow();
                    row.insertCell(0).innerHTML = courseName;
                    row.insertCell(1).innerHTML = courseCode;
                    row.insertCell(2).innerHTML = assumedKnowledge;
                    row.insertCell(3).innerHTML = units;

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
