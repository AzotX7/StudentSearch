<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление категориями</title>
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
        }
        h2 {
            text-align: center;
            color: #e0e0e0;
        }
        .category {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #333;
        }
        .category h3 {
            margin: 0;
            color: #fff;
        }
        .btn-small {
            font-size: 14px;
            padding: 5px 10px;
            color: #fff;
            background-color: #d9534f;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-small:hover {
            background-color: #c9302c;
        }
        .btn-large {
            display: block;
            text-align: center;
            margin: 10px auto 20px;
            padding: 10px 20px;
            background-color: #444;
            color: #fff;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
        }
        .btn-large:hover {
            background-color: #666;
        }
    </style>
    <script>
        function confirmDelete(categoryId) {
            return confirm("Вы уверены, что хотите удалить эту категорию?");
        }
    </script>
</head>
<body>
<header>
    <div class="logo">Управление категориями</div>
    <nav>
        <a href="${pageContext.request.contextPath}/users">Пользователи</a>
        <a href="${pageContext.request.contextPath}/materials">Материалы</a>
        <a href="${pageContext.request.contextPath}/logout">Выход</a>
    </nav>
</header>
<main>
    <h2>Все категории</h2>
    <a href="${pageContext.request.contextPath}/categories/add" class="btn-large">Добавить новую категорию</a>
    <c:forEach var="category" items="${categories}">
        <div class="category">
            <h3>${category.name}</h3>
            <form action="${pageContext.request.contextPath}/categories/${category.id}" method="post">
                <input type="hidden" name="_method" value="delete">
                <input type="submit" value="Удалить" class="btn-small" onclick="return confirmDelete(${category.id});">
            </form>
        </div>
    </c:forEach>
</main>
</body>
</html>
