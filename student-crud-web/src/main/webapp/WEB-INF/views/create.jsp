<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Create Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Create Student</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/students">Back to List</a>
        <a href="${pageContext.request.contextPath}/students/logs">View Logs</a>
        <a href="${pageContext.request.contextPath}/">Home</a>
    </nav>
    <form:form modelAttribute="student" method="post" action="${pageContext.request.contextPath}/students/create">
        <div>
            <label for="name">Name:</label>
            <form:input path="name" id="name" required="true"/>
        </div>
        <div>
            <label for="email">Email:</label>
            <form:input path="email" id="email" type="email" required="true"/>
        </div>
        <button type="submit">Create</button>
    </form:form>
</div>
</body>
</html>