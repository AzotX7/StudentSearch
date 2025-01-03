<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить категорию</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #111;
            color: #fff;
        }

        header {
            background: #1a1a1a;
            color: #ffffff;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        nav a {
            color: #ffffff;
            margin-left: 15px;
            text-decoration: none;
            padding: 10px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        nav a:hover {
            background-color: #333;
        }

        main {
            padding: 20px;
            max-width: 800px;
            margin: auto;
            background: #222;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #e0e0e0;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 20px;
        }

        input[type="text"], input[type="submit"] {
            padding: 10px;
            border: 1px solid #333;
            border-radius: 5px;
            font-size: 16px;
        }

        input[type="text"] {
            background-color: #222;
            color: #fff;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .btn-small {
            padding: 5px 15px;
            font-size: 14px;
            color: #fff;
            background-color: #444;
            border-radius: 5px;
            text-decoration: none;
        }

        .btn-small:hover {
            background-color: #666;
        }

        .message {
            text-align: center;
            margin-top: 10px;
        }

        .error {
            color: #ff6666;
        }

        .success {
            color: #66ff66;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearchAdmin</div>
    <nav>
        <a href="${pageContext.request.contextPath}/categories" class="btn-small">Категории</a>
    </nav>
</header>
<main>
    <h2>Добавить категорию</h2>

    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>

    <c:if test="${param.success eq 'true'}">
        <div class="message success">Категория успешно добавлена!</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/categories/add" method="post">
        <label for="categoryName">Название категории:</label>
        <input type="text" id="categoryName" name="categoryName" required>
        <input type="submit" value="Добавить категорию">
    </form>
</main>
</body>
</html>
