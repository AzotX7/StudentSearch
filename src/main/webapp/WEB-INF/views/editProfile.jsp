<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактировать профиль</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #111;
            color: #fff;
            margin: 0;
            padding: 0;
        }
        header {
            background: #1a1a1a;
            color: #ffffff;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .logo {
            font-size: 32px;
            font-weight: bold;
        }
        nav {
            display: flex;
            align-items: center;
        }
        nav a {
            margin-left: 15px;
            text-decoration: none;
            color: #ffffff;
            padding: 10px 15px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        nav a:hover {
            background-color: #333;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: #222; /* Цвет фона для контейнера */
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }
        h2 {
            text-align: center;
            color: #e0e0e0; /* Цвет заголовка */
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #bbb;
        }
        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #444;
            border-radius: 4px;
            box-sizing: border-box;
            transition: border-color 0.3s;
            background-color: #222;
            color: #fff;
        }
        input[type="text"]:focus,
        input[type="email"]:focus {
            border-color: #007bff;
            outline: none;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #444;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #666;
        }
        .error-message {
            color: red;
            text-align: center;
            margin: 10px 0;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: white;
            transition: color 0.3s;
        }
        .back-link:hover {
            color: #666;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Ко всем материалам</a>
        <a href="${pageContext.request.contextPath}/logout">Выход</a>
    </nav>
</header>

<div class="container">
    <h2>Редактировать профиль</h2>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/profile/update" method="post">
        <div class="form-group">
            <label for="username">Имя пользователя:</label>
            <input type="text" id="username" name="username" value="${user.username}" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>
        </div>
        <button type="submit">Сохранить изменения</button>
    </form>
    <a class="back-link" href="${pageContext.request.contextPath}/profile">Вернуться в профиль</a>
</div>
</body>
</html>
