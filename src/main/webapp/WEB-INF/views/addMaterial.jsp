<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добавить материал</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #111;
            color: #fff;
            overflow: hidden;
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
            font-size: 16px;
            padding: 10px 15px;
            border: 1px solid transparent;
            border-radius: 5px;
            transition: background-color 0.3s, border-color 0.3s;
        }

        nav a:hover {
            background-color: #333;
            border-color: #444;
        }


        .main-title {
            text-align: center;
            margin: 50px 0;
            font-size: 32px;
            font-weight: bold;
            color: #e0e0e0;
        }


        main {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #222;
            border-radius: 8px;
            border: 1px solid #444;
        }


        form input[type="text"], form textarea {
            width: 100%;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333;
            background-color: #222;
            color: #ffffff;
            box-sizing: border-box;
        }


        input[type="submit"] {
            padding: 10px 15px;
            border-radius: 5px;
            color: #fff;
            background-color: #444;
            font-size: 16px;
            cursor: pointer;
            border: none;
            height: 40px;
            width: 100%;
            transition: background-color 0.3s;
            margin-top: 10px;
            box-sizing: border-box;
        }

        input[type="submit"]:hover {
            background-color: #666;
        }


        textarea {
            resize: none;
        }

        .file-input-container {
            position: relative;
            width: 100%;
            height: 40px;
            margin-bottom: 10px;
        }

        .file-input-label {
            display: block;
            width: 100%;
            height: 100%;
            padding: 10px;
            border: 1px solid #333;
            border-radius: 5px;
            background-color: #222;
            color: #fff;
            text-align: left;
            cursor: pointer;
            box-sizing: border-box;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .file-input-label:hover {
            background-color: #444;
        }

        input[type="file"] {
            position: absolute;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }

    </style>

</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Ко всем материалам</a>
        <c:if test="${not empty user}">
            <a href="${pageContext.request.contextPath}/materials/profile">Личный кабинет</a>
            <a href="${pageContext.request.contextPath}/logout">Выход</a>
        </c:if>
        <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/login.jsp">Вход</a>
        </c:if>
    </nav>
</header>

<div class="main-title">Добавление нового материала</div>

<main>
    <form action="${pageContext.request.contextPath}/materials/add" method="post" enctype="multipart/form-data">
        <label for="title">Название:</label>
        <input type="text" id="title" name="title" required>

        <label for="content">Содержимое:</label>
        <textarea id="content" name="content" required></textarea>

        <label for="file">Изображение:</label>
        <div class="file-input-container">
            <label for="file" id="file-label" class="file-input-label">Выберите изображение</label>
            <input type="file" id="file" name="file" required>
        </div>

        <label>Категории:</label>
        <c:forEach var="category" items="${categories}">
            <div>
                <input type="checkbox" id="category_${category.id}" name="categories" value="${category.id}">
                <label for="category_${category.id}">${category.name}</label>
            </div>
        </c:forEach>
        <input type="submit" value="Добавить материал">
    </form>
</main>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const fileInput = document.getElementById("file");
        const fileLabel = document.getElementById("file-label");

        if (fileInput && fileLabel) {
            fileInput.addEventListener("change", function () {
                if (fileInput.files.length > 0) {
                const fileName = fileInput.files[0].name;
                fileLabel.textContent = fileName;
                } else {
                    fileLabel.textContent = "Выберите изображение";
                }
            });
        } else {
            console.error("Элементы fileInput или fileLabel не найдены!");
        }
    });
</script>
</body>
</html>
