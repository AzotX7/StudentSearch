<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление пользователями</title>
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
            padding: 10px 20px;
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
        }
        nav {
            display: flex;
            align-items: center;
        }
        nav a {
            color: #ffffff;
            margin-left: 15px;
            text-decoration: none;
            padding: 10px;
            border: 1px solid transparent;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        nav a:hover {
            background-color: #333;
            border-color: #444;
        }
        .btn-large {
            font-size: 16px; /* Размер шрифта для кнопки "Добавить пользователя" */
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
        .user {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #333;
        }
        .user h3 {
            margin: 0;
            color: #fff;
        }
        .action-buttons {
            display: flex;
            justify-content: flex-start;
            margin-top: 10px;
        }
        .btn-small {
            padding: 5px 0;
            font-size: 14px;
            margin-right: 5px;
            width: 4cm; /* Установлена фиксированная ширина 4 см для обеих кнопок */
            background-color: #007bff; /* Фон кнопки редактирования */
            color: #ffffff; /* Цвет текста кнопки редактирования */
            border: none; /* Убрать рамку */
            border-radius: 5px; /* Округленные края кнопки */
            cursor: pointer; /* Указатель при наведении */
            text-align: center; /* Выравнивание текста по центру */
        }
        .btn-small:hover {
            background-color: #0056b3; /* Цвет кнопки при наведении */
        }
        .search-container {
            margin-bottom: 20px;
            text-align: center;
        }
        .search-container input[type="text"] {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333;
            width: 300px;
            color: #ffffff;
            background-color: #222;
            font-size: 16px;
        }
    </style>
    <script>
        function confirmDelete(userId) {
            return confirm("Вы уверены, что хотите удалить этого пользователя?");
        }
    </script>
</head>
<body>
<header>
    <div class="logo">StudentSearchAdmin</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Материалы</a>
        <a href="${pageContext.request.contextPath}/users/addUser" class="btn-large">Добавить пользователя</a>
        <a href="${pageContext.request.contextPath}/logout">Выход</a>
    </nav>
</header>
<main>
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/users/search" method="get">
            <input type="text" name="query" placeholder="Поиск пользователя...">
            <input type="submit" value="Поиск" class="btn-small">
        </form>
    </div>
    <h2>Все пользователи</h2>

    <c:if test="${not empty noResultsMessage}">
        <div class="no-results-message" style="text-align:center; color: red;">
            <p>${noResultsMessage}</p>
        </div>
    </c:if>

    <c:forEach var="user" items="${users}">
        <div class="user">
            <h3>${user.username}</h3>
            <p>Email: ${user.email}</p>
            <p>Роль: ${user.role}</p>
            <div class="action-buttons">
                <form action="${pageContext.request.contextPath}/users/editUser" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Редактировать" class="btn-small">
                </form>
                <form action="${pageContext.request.contextPath}/users/${user.id}" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="delete">
                    <input type="submit" value="Удалить" class="btn-small" onclick="return confirmDelete(${user.id});">
                </form>
            </div>
        </div>
    </c:forEach>
</main>
</body>
</html>
