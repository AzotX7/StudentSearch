<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #111;
            color: #e0e0e0;
            margin: 0;
            padding: 0;
        }

        header {
            background: #1a1a1a;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
        }

        .container {
            width: 100%;
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            background: #222;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
            box-sizing: border-box;
        }

        h2 {
            text-align: center;
            color: #e0e0e0;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #aaa;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            padding-left: 1cm;
            padding-right: 1cm;
            background-color: #333;
            border: 1px solid #444;
            border-radius: 5px;
            color: #e0e0e0;
            box-sizing: border-box;
        }

        .form-group input::placeholder {
            color: #fff;
        }

        .btn {
            width: 100%;
            padding: 10px;
            background-color: #444; /* Оригинальный зеленый цвет кнопки */
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #666;
        }

        .link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #888;
            text-decoration: none;
            transition: color 0.3s;
        }

        .link:hover {
            color: #bbb;
            text-decoration: underline;
        }
    </style>
</head>
<body>
<header>
    StudentSearch
</header>

<div class="container">
    <h2>Вход</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Логин:</label>
            <input type="text" id="username" name="username" placeholder="Введите логин" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" placeholder="Введите пароль" required>
        </div>
        <button type="submit" class="btn">Войти</button>
    </form>
    <a href="${pageContext.request.contextPath}/register" class="link">Нет аккаунта? Зарегистрируйтесь</a>
</div>
</body>
</html>
