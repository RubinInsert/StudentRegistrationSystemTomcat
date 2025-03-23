<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="login-container">
        <h1>Confirmed Enrollment!</h1>
    </div>
    <script>
        <% if (request.getAttribute("loginFailed") != null) { %>
            alert("Login failed!");
        <% } %>
    </script>
</body>
</html>