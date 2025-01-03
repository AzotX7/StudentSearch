<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление пользователями</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            font-size: 16px;
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
            position: relative;
        }
        .user h3 {
            margin: 0;
            color: #fff;
        }
        .action-buttons {
            display: flex;
            justify-content: flex-start;
            gap: 10px;
            align-items: flex-end;
            margin-top: auto;
        }

        .search-container {
            margin-bottom: 20px;
            text-align: center;
        }

        .btn-edit, .btn-delete {
            padding: 10px 20px;
            font-size: 14px;
            margin-right: 10px;
            background-color: #444;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            width: 150px;
            height: 40px;
        }

        .btn-edit:hover, .btn-delete:hover {
            background-color: #666;
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
    function deleteUser(userId) {
        if (confirm("Вы уверены, что хотите удалить этого пользователя?")) {
            $.ajax({
                url: '${pageContext.request.contextPath}/users/' + userId,
                type: 'POST',
                data: {
                    '_method': 'delete'
                },
                success: function(response) {


                    if (response === "success") {
                        $('#user-' + userId).remove();
                        alert('Пользователь успешно удален');
                    } else {
                        alert('Произошла ошибка при удалении');
                    }
                },
                error: function(xhr, status, error) {
                    console.log(error);
                    alert('Ошибка при удалении пользователя: ' + error);
                }
            });
        }
    }
</script>
</head>
<body>
<header>
    <div class="logo">StudentSearchAdmin</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Материалы</a>
        <a href="${pageContext.request.contextPath}/categories" class="btn-large">Категории</a>
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
        <div class="user" id="user-${user.id}">
            <h3>${user.username}</h3>
            <p>Email: ${user.email}</p>
            <p>Роль: ${user.role}</p>
            <div class="action-buttons">
                <form action="${pageContext.request.contextPath}/users/editUser" method="get">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Редактировать" class="btn-edit">
                </form>
                <button class="btn-delete" onclick="deleteUser(${user.id});">Удалить</button>
            </div>
        </div>
    </c:forEach>
</main>
</body>
</html>