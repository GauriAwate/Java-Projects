<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Employee List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 50px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #aaa;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #ddd;
        }
        a.button {
            padding: 5px 10px;
            background: #28a745;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
        }
        a.delete {
            background: #dc3545;
        }
    </style>
</head>
<body>
    <h2>Employee List</h2>
    <a href="showForm" class="button">Add New Employee</a>
    <br><br>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Salary</th>
                <th>Department</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.salary}</td>
                    <td>${employee.department}</td>
                    <td>
                        <a href="updateForm?employeeId=${employee.id}" class="button">Edit</a>
                        <a href="delete?employeeId=${employee.id}" class="button delete">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
