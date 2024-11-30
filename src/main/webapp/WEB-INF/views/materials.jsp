<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>StudentSearch Materials</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Общий стиль для страницы */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #111;
            color: #fff;
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

        nav a, .add-material-btn, .view-users-btn {
            margin-left: 15px;
            text-decoration: none;
            color: #ffffff;
            font-size: 16px;
            padding: 10px 15px;
            border: 1px solid transparent;
            border-radius: 5px;
            transition: background-color 0.3s, border-color 0.3s;
        }

        nav a:hover, .add-material-btn:hover, .view-users-btn:hover {
            background-color: #333;
            border-color: #444;
        }

        /* Стили для поля поиска */
        nav input[type="text"] {
            padding: 5px; /* Уменьшен внутренний отступ */
            border-radius: 5px;
            border: 1px solid #333;
            font-size: 14px; /* Уменьшен размер шрифта */
            width: 180px; /* Уменьшена ширина */
            color: #ffffff;
            background-color: #222;
            margin-right: 15px; /* Уменьшен отступ справа */
            height: 22px; /* Уменьшена высота */
            margin-left: -20px;
        }

        nav input[type="text"]::placeholder {
            color: #ffffff; /* Цвет текста внутри поисковика */
        }

        /* Центральное название */
        .main-title {
            text-align: center;
            margin: 50px 0;
            font-size: 48px;
            font-weight: bold;
            color: #e0e0e0;
        }

        /* Основная часть с карточками материалов */
        main {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .material-card {
            background: #222;
            border: 1px solid #444;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.4);
            transition: transform 0.2s;
            text-align: center;
        }

        .material-card:hover {
            transform: translateY(-5px);
        }

        .material-card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }

        .material-content {
            padding: 15px;
            color: #e0e0e0;
        }

        .material-content h3 {
            font-size: 20px;
            margin: 0 0 10px;
            color: #ffffff;
        }

        /* Стили для кнопок редактирования и удаления */
        .material-actions {
            margin-top: 15px;
            display: flex;
            justify-content: center;
            gap: 10px;
        }

        /* Общий стиль для кнопок */
        .btn {
            padding: 10px 15px; /* Внутренние отступы */
            border-radius: 5px; /* Закругление углов */
            color: #fff; /* Цвет текста */
            background-color: #444; /* Темный цвет фона */
            font-size: 14px; /* Размер шрифта */
            cursor: pointer; /* Указатель при наведении */
            border: none; /* Убираем границу */
            height: 40px; /* Фиксированная высота */
            width: 120px; /* Фиксированная ширина */
            display: flex; /* Используем flex для центрирования текста */
            align-items: center; /* Вертикальное центрирование текста */
            justify-content: center; /* Горизонтальное центрирование текста */
            transition: background-color 0.3s; /* Плавный переход фона */
            box-sizing: border-box; /* Включаем padding и border в общую высоту и ширину */
        }

        .btn:hover {
            background-color: #666; /* Цвет фона при наведении */
        }

        /* Убираем стандартные стили для кнопок submit */
        form input[type="submit"] {
            display: none;
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
        }

        /* Убираем отступы у форм */
        .delete-btn {
            margin: 0; /* Убедимся, что у формы нет отступов */
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <nav>
        <form action="${pageContext.request.contextPath}/materials/search" method="get" style="display:inline;">
            <input type="text" name="query" placeholder="Найти материал...">
        </form>

        <c:if test="${user.role == 'ADMIN' || user.role == 'USER'}">
            <a href="${pageContext.request.contextPath}/materials/add" class="add-material-btn">Добавить материал</a>
        </c:if>

        <!-- Кнопка для администратора, чтобы перейти ко всем пользователям -->
        <c:if test="${user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/users" class="view-users-btn">Все пользователи</a>
        </c:if>

        <a href="${pageContext.request.contextPath}/materials/profile">Личный кабинет</a>
        <a href="${pageContext.request.contextPath}/logout">Выход</a>
    </nav>
</header>

<div class="main-title">StudentSearch</div>

<main>
    <c:choose>
        <c:when test="${not empty materials}">
            <c:forEach var="material" items="${materials}">
                <div class="material-card">
                    <a href="${pageContext.request.contextPath}/materials/view?materialId=${material.id}">
                        <img src="${material.imageURL}" alt="${material.title}">
                        <div class="material-content">
                            <h3>${material.title}</h3>
                            <c:if test="${user.role == 'ADMIN'}">
                                <div class="material-actions">
                                    <a href="${pageContext.request.contextPath}/materials/edit?materialId=${material.id}" class="btn">Изменить</a>
                                    <form action="${pageContext.request.contextPath}/materials/${material.id}" method="post" class="delete-btn">
                                        <input type="hidden" name="_method" value="delete">
                                        <button type="submit" class="btn" onclick="return confirm('Вы уверены, что хотите удалить этот материал?');">Удалить</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Материалов, соответствующих запросу, не найдено.</p>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>
