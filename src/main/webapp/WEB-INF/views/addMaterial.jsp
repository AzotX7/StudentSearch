<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добавить материал</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Общий стиль для страницы */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #111;
            color: #fff;
            overflow: hidden; /* Убираем возможность прокрутки страницы */
        }

        /* Верхняя навигация */
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

        /* Центральное название */
        .main-title {
            text-align: center;
            margin: 50px 0;
            font-size: 32px; /* Уменьшен размер шрифта */
            font-weight: bold;
            color: #e0e0e0;
        }

        /* Основная часть с формой */
        main {
            max-width: 600px; /* Максимальная ширина формы */
            margin: 20px auto; /* Центрируем форму */
            padding: 20px; /* Отступы внутри формы */
            background-color: #222; /* Цвет фона формы */
            border-radius: 8px; /* Закругление углов формы */
            border: 1px solid #444; /* Цвет границы формы */
        }

        /* Стили для полей ввода */
        form input[type="text"], form textarea {
            width: 100%;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333;
            background-color: #222;
            color: #ffffff;
            box-sizing: border-box; /* Учитываем padding и border в ширину */
        }

        /* Стили для кнопки */
        input[type="submit"] {
            padding: 10px 15px; /* Увеличиваем внутренние отступы */
            border-radius: 5px; /* Закругление углов */
            color: #fff; /* Цвет текста */
            background-color: #444; /* Темный цвет фона кнопки */
            font-size: 16px; /* Размер шрифта */
            cursor: pointer; /* Указатель при наведении */
            border: none; /* Убираем границу */
            height: 40px; /* Фиксированная высота */
            width: 100%; /* Ширина кнопки на всю ширину формы */
            transition: background-color 0.3s; /* Плавный переход фона */
            margin-top: 10px; /* Отступ сверху для кнопки */
            box-sizing: border-box; /* Учитываем padding и border в ширину */
        }

        input[type="submit"]:hover {
            background-color: #666; /* Цвет кнопки при наведении */
        }

        /* Убираем возможность растягивания содержимого */
        textarea {
            resize: none; /* Запрет на изменение размера текстовой области */
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Ко всем материалам</a> <!-- Кнопка для перехода ко всем материалам -->
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
    <form action="${pageContext.request.contextPath}/materials/add" method="post">
        <label for="title">Название:</label>
        <input type="text" id="title" name="title" required>

        <label for="content">Содержимое:</label>
        <textarea id="content" name="content" required></textarea>

        <label for="imageUrl">URL изображения:</label>
        <input type="text" id="imageUrl" name="imageUrl" required>

        <input type="submit" value="Добавить материал"> <!-- Кнопка с нужными стилями -->
    </form>
</main>
</body>
</html>
