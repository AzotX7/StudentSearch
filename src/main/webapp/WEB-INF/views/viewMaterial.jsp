<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${material.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Общий стиль страницы */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #111;
            color: #fff;
        }

        /* Верхняя панель */
        header {
            background: #1a1a1a;
            color: white;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #e0e0e0;
            margin: 0;
        }

        /* Кнопка для возвращения ко всем материалам */
        .back-button {
            padding: 10px 15px;
            border-radius: 5px;
            color: #fff;
            background-color: #444;
            border: none;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #666;
        }

        /* Основная часть */
        main {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            background-color: #222;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
        }

        main img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #333;
        }

        main p {
            font-size: 18px;
            line-height: 1.6;
            color: #e0e0e0;
            margin-bottom: 20px;
        }

        /* Блок комментариев */
        .comments-section {
            margin-top: 30px;
        }

        .comment {
            position: relative;
            padding: 15px;
            margin-bottom: 10px;
            background-color: #333;
            border-radius: 5px;
        }

        .comment-author {
            font-weight: bold;
            color: #e0e0e0;
        }

        .comment-date {
            font-size: 12px;
            color: #888;
        }

        .comment-text {
            margin-top: 5px;
            font-size: 16px;
            color: #ccc;
        }

        .comment-delete-btn {
            margin-top: 10px; /* Отступ сверху */
            background-color: #ff4444; /* Красный цвет для кнопки удаления */
            color: #fff;
            padding: 5px 10px;
            font-size: 12px;
            border: none;
            border-radius: 3px; /* Добавлен радиус для скругления углов */
            cursor: pointer;
            transition: background-color 0.3s;
            display: inline-block;
        }

        .comment-delete-btn:hover {
            background-color: #ff6666;
        }

        /* Форма добавления комментария */
        .add-comment-form {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 10px;
            margin-top: 30px;
            background-color: #222;
            padding: 20px;
            border-radius: 5px;
            border: 1px solid #444;
        }

        .add-comment-form textarea {
            width: 100%; /* Ширина текстового поля */
            padding: 10px;
            background-color: #333;
            border: 1px solid #444;
            border-radius: 5px;
            color: #e0e0e0;
            resize: none; /* Запрет на изменение размера */
            min-height: 100px; /* Увеличена минимальная высота */
            font-size: 18px;
        }

        .add-comment-form textarea::placeholder {
            color: #fff;
            font-size: 18px;
        }

        .add-comment-form button {
            padding: 10px 20px;
            color: #fff;
            background-color: #444;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            outline: none; /* Удаление контуров при нажатии */
        }

        .add-comment-form button:hover {
            background-color: #666;
        }

        /* Блок кнопок для админа */
        .admin-actions {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 20px;
        }

        /* Стиль кнопок "Изменить" и "Удалить" */
        .admin-actions .edit-btn,
        .admin-actions .delete-btn button {
            padding: 10px 15px;
            border-radius: 5px;
            color: #fff;
            font-size: 14px;
            text-align: center;
            background-color: #444;
            border: 1px solid transparent;
            cursor: pointer;
            transition: background-color 0.3s, border-color 0.3s;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .admin-actions .edit-btn:hover,
        .admin-actions .delete-btn button:hover {
            background-color: #666;
            border-color: #777;
        }

        .admin-actions .delete-btn {
            display: inline-flex;
            margin: 0;
            padding: 0;
        }

        .admin-actions .delete-btn button {
            background-color: #444; /* Цвет для кнопки удаления */
            border: none;
            color: #fff;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s, border-color 0.3s;
        }

        .admin-actions .delete-btn button:hover {
            background-color: #666; /* Темнее при наведении */
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <a class="back-button" href="${pageContext.request.contextPath}/materials">Ко всем материалам</a>
</header>

<main>
    <h1>${material.title}</h1>
    <img src="${material.imageURL}" alt="${material.title}">
    <p>${material.content}</p>

    <c:if test="${user.role == 'ADMIN'}">
        <div class="admin-actions">
            <a href="${pageContext.request.contextPath}/materials/edit?materialId=${material.id}" class="edit-btn">Изменить</a>
            <form action="${pageContext.request.contextPath}/materials/${material.id}" method="post" class="delete-btn">
                <input type="hidden" name="_method" value="delete">
                <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить этот материал?');">Удалить</button>
            </form>
        </div>
    </c:if>

    <div class="add-comment-form">
        <form action="${pageContext.request.contextPath}/comments" method="post">
            <input type="hidden" name="materialId" value="${material.id}"> <!-- Передаем идентификатор материала -->
            <textarea name="commentText" required placeholder="Ваш комментарий..."></textarea>
            <button type="submit">Добавить комментарий</button>
        </form>
    </div>

    <div class="comments-section">
        <h2>Комментарии</h2>
        <c:forEach var="comment" items="${comments}">
            <div class="comment">
                <div class="comment-author">${comment.author.username}</div>
                <div class="comment-date">${comment.createdAt}</div>
                <div class="comment-text">${comment.text}</div>
                <c:if test="${user.role == 'ADMIN'}">
                    <form action="${pageContext.request.contextPath}/comments" method="post" class="delete-btn">
                        <input type="hidden" name="action" value="deleteComment"> <!-- Действие удаления -->
                        <input type="hidden" name="commentId" value="${comment.id}"> <!-- ID комментария -->
                        <button type="submit" class="comment-delete-btn" onclick="return confirm('Вы уверены, что хотите удалить этот комментарий?');">Удалить</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</main>
</body>
</html>
