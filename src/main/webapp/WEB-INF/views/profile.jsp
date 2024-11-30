<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
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
        main {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .profile-info {
            font-size: 18px;
            margin-bottom: 20px;
            padding: 15px;
            background: #222; /* Цвет фона для блока информации профиля */
            border-radius: 8px;
        }
        .profile-info p {
            margin: 10px 0;
            color: #fff;
        }
        .profile-actions a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            background-color: #444;
            color: white;
            border-radius: 5px;
            text-decoration: none;
        }
        .profile-actions a:hover {
            background-color: #666;
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

<main>
    <div class="profile-info">
        <p><strong>Имя пользователя:</strong> ${user.username}</p>
        <p><strong>Роль:</strong> ${user.role}</p>
        <p><strong>Email:</strong> ${user.email}</p>
    </div>
    <div class="profile-actions">
        <a href="${pageContext.request.contextPath}/myMaterials">Мои материалы</a>
        <a href="${pageContext.request.contextPath}/profile/edit">Редактировать профиль</a>
    </div>
</main>
</body>
</html>
