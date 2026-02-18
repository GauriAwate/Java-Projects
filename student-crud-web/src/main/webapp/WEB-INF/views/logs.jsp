<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>View Logs</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <h1>Application Logs</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/students">Back to List</a>
        <a href="${pageContext.request.contextPath}/">Home</a>
    </nav>
    <c:choose>
        <c:when test="${logs.startsWith('Unable to')}">
            <p class="error"><c:out value="${logs}" /></p>
        </c:when>
        <c:otherwise>
            <pre><c:out value="${logs}" /></pre>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>