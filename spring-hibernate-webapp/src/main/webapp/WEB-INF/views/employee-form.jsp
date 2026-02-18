<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Add / Update Employee</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .form-container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            width: 400px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin: 12px 0 5px;
            color: #555;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: border 0.3s;
        }

        input:focus, select:focus {
            border-color: #4facfe;
            outline: none;
        }

        button {
            width: 100%;
            margin-top: 20px;
            background: #4facfe;
            color: #fff;
            border: none;
            padding: 12px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background: #00c6ff;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Add / Update Employee</h2>
        <form action="saveEmployee" method="post">
            <input type="hidden" name="id" value="${employee.id}" />

            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${employee.name}" required />

            <label for="salary">Salary:</label>
            <input type="number" id="salary" name="salary" value="${employee.salary}" required />

            <label for="department">Department:</label>
            <input type="text" id="department" name="department" value="${employee.department}" required />

            <button type="submit">Save</button>
        </form>
    </div>
</body>
</html>
