<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Мои материалы</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #111; /* Темный фон для соответствия стилю */
            margin: 0;
            padding: 0;
            color: #fff; /* Белый текст для контраста */
        }

        header {
            background: #1a1a1a; /* Темный фон заголовка */
            color: #e0e0e0; /* Светлый текст */
            padding: 20px;
            display: flex; /* Используем flexbox для выравнивания */
            justify-content: space-between; /* Пространство между элементами */
            align-items: center; /* Вертикальное выравнивание */
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4); /* Тень для заголовка */
        }

        .logo {
            font-size: 24px; /* Размер шрифта для названия сайта */
            font-weight: bold; /* Жирный шрифт */
            color: #e0e0e0; /* Цвет текста */
            margin: 0; /* Убираем отступы */
        }

        main {
            max-width: 800px; /* Ширина основного контейнера */
            margin: 40px auto; /* Центрирование на странице */
            padding: 20px;
            background: #222; /* Темный фон для основного блока */
            border-radius: 8px; /* Закругленные углы */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5); /* Тень для основного блока */
            text-align: center;
        }

        .material {
            border: 1px solid #444; /* Темная рамка для материалов */
            padding: 15px; /* Отступ внутри блока материала */
            margin: 10px 0;
            border-radius: 5px; /* Закругленные углы */
            background-color: #333; /* Темный фон для материала */
            color: #e0e0e0; /* Светлый текст */
        }

        h3 {
            margin: 0; /* Убираем отступы */
            color: #white; /* Цвет заголовка материала */
        }

        .button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #444; /* Основной цвет кнопки */
            color: white;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s; /* Плавный переход цвета */
        }

        .button:hover {
            background-color: #666; /* Цвет кнопки при наведении */
        }

        /* Сообщение для отсутствия материалов */
        .empty-message {
            color: #e0e0e0; /* Цвет сообщения */
            font-size: 18px; /* Размер шрифта */
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div> <!-- Название сайта -->
    <h1>Мои материалы</h1> <!-- Заголовок страницы -->
</header>

<main>
    <c:if test="${not empty materials}">
        <c:forEach var="material" items="${materials}">
            <div class="material">
                <h3>${material.title}</h3>
                <p>${material.content}</p>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty materials}">
        <p class="empty-message">У вас пока нет материалов.</p> <!-- Добавлено сообщение о пустом списке -->
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/materials">Перейти ко всем материалам</a>
</main>
</body>
</html>
