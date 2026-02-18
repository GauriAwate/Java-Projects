<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>View Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Student Details</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/students">Back to List</a>
        <a href="${pageContext.request.contextPath}/students/logs">View Logs</a>
        <a href="${pageContext.request.contextPath}/">Home</a>
    </nav>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty student}">
        <table>
            <tr>
                <th>ID</th>
                <td>${student.id}</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>${student.name}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${student.email}</td>
            </tr>
        </table>
    </c:if>
</div>
</body>
</html>