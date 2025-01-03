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
            resize: none;
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
    <h1>Редактировать материал</h1>
    <a href="${pageContext.request.contextPath}/materials" class="back-button">Вернуться к материалам</a>
</header>
<main>
    <form action="${pageContext.request.contextPath}/materials/update" method="post" enctype="multipart/form-data">
        <input type="hidden" name="materialId" value="${material.id}">

        <label for="title">Заголовок:</label>
        <input type="text" id="title" name="title" value="${material.title}" required>

        <label for="content">Содержание:</label>
        <textarea id="content" name="content" required>${material.content}</textarea>

        <label for="file">Изображение:</label>
                <div class="file-input-container">
                    <label for="file" id="file-label" class="file-input-label">Выберите изображение</label>
                    <input type="file" id="file" name="file" required>
                </div>

        <input type="submit" value="Обновить материал">
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
