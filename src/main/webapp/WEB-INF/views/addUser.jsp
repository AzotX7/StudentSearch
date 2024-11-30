<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить пользователя</title>
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
            flex-grow: 1; /* Позволяет заголовку занимать оставшееся пространство */
            text-align: center;
        }
        .back-button {
            background-color: #444;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s;
        }
        .back-button:hover {
            background-color: #666;
        }
        main {
            padding: 20px;
            max-width: 600px;
            margin: 40px auto; /* Увеличен отступ сверху для лучшего размещения */
            background: #222;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 15px; /* Отступы между элементами формы */
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
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
            background-color: #444;
            color: #ffffff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #666;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearchAdmin</div> <!-- Название добавлено влево -->
    <a href="${pageContext.request.contextPath}/users" class="back-button">Вернуться к пользователям</a> <!-- Кнопка "Вернуться к пользователям" -->
</header>
<main>
    <form action="${pageContext.request.contextPath}/users/addUser" method="post">
        <input type="text" name="username" placeholder="Имя пользователя" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Пароль" required>
        <select name="role">
            <option value="USER">Пользователь</option>
            <option value="ADMIN">Администратор</option>
        </select>
        <input type="submit" value="Добавить пользователя">
    </form>
</main>
</body>
</html>
