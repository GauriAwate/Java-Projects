<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Welcome to Student Management System</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/students">Manage Students</a>
        <a href="${pageContext.request.contextPath}/students/logs">View Logs</a>
    </nav>
</div>
</body>
</html>