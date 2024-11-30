<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Редактировать материал</title>
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
        label {
            font-size: 16px;
            color: #e0e0e0;
        }
        input[type="text"],
        textarea {
            padding: 10px;
            border: 1px solid #333;
            border-radius: 5px;
            background-color: #333;
            color: #ffffff;
            font-size: 16px;
        }
        textarea {
            resize: none; /* Отключение возможности растягивать поле содержания */
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
    <div class="logo">StudentSearch</div>
    <h1>Редактировать материал</h1>
    <a href="${pageContext.request.contextPath}/materials" class="back-button">Вернуться к материалам</a>
</header>
<main>
    <form action="${pageContext.request.contextPath}/materials/update" method="post">
        <input type="hidden" name="materialId" value="${material.id}">

        <label for="title">Заголовок:</label>
        <input type="text" id="title" name="title" value="${material.title}" required>

        <label for="content">Содержание:</label>
        <textarea id="content" name="content" required>${material.content}</textarea>

        <label for="imageUrl">URL изображения:</label>
        <input type="text" id="imageUrl" name="imageUrl" value="${material.imageURL}">

        <input type="submit" value="Обновить материал">
    </form>
</main>
</body>
</html>
