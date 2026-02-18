<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Student List</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/students/create">Create New Student</a>
        <a href="${pageContext.request.contextPath}/students/logs">View Logs</a>
        <a href="${pageContext.request.contextPath}/">Home</a>
    </nav>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="student" items="${students}">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/students/${student.id}">View</a>
                    <a href="${pageContext.request.contextPath}/students/edit/${student.id}">Edit</a>
                    <a href="${pageContext.request.contextPath}/students/delete/${student.id}"
                       onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>