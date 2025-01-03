<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактировать пользователя</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
        }
        h1 {
            margin: 0;
            font-size: 24px;
            text-align: center;
            flex-grow: 1;
        }
        .back-button {
            background-color: #444;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .back-button:hover {
            background-color: #666;
        }
        main {
            padding: 20px;
            max-width: 600px;
            margin: 40px auto;
            background: #222;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        input[type="text"],
        input[type="email"],
        select {
            padding: 10px;
            border: 1px solid #333;
            border-radius: 5px;
            background-color: #333;
            color: #ffffff;
            font-size: 16px;
        }
        input[type="submit"] {
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #666;
            color: #ffffff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearchAdmin</div>
    <form action="${pageContext.request.contextPath}/users" method="get" style="margin-left: 20px;">
        <input type="submit" value="Вернуться к пользователям" class="back-button">
    </form>
</header>
<main>
    <form action="${pageContext.request.contextPath}/users/editUser" method="post">
        <input type="hidden" name="id" value="${user.id}">
        <input type="text" name="username" value="${user.username}" required placeholder="Имя пользователя">
        <input type="email" name="email" value="${user.email}" required placeholder="Email">
        <select name="role">
            <option value="USER" <c:if test="${user.role == 'USER'}">selected</c:if>>Пользователь</option>
            <option value="ADMIN" <c:if test="${user.role == 'ADMIN'}">selected</c:if>>Администратор</option>
        </select>
        <input type="submit" value="Обновить пользователя">
    </form>
</main>
</body>
</html>
