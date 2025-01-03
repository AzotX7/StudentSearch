<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>StudentSearch Materials</title>
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

        .search-container {
            position: relative;
            display: inline-block;
            margin-right: 15px;
        }

        .search-container input[type="text"] {
            padding: 10px 30px 10px 15px;
            border-radius: 5px;
            border: 1px solid #333;
            font-size: 16px;
            width: 200px;
            color: #ffffff;
            background-color: #222;
            height: 25px;
        }

        .search-container input[type="text"]::placeholder {
            color: #ffffff;
        }

        .filter-icon {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
        }

        .main-title {
            text-align: center;
            margin: 50px 0;
            font-size: 48px;
            font-weight: bold;
            color: #e0e0e0;
        }


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


        .material-actions {
            margin-top: 15px;
            display: flex;
            justify-content: center;
            gap: 10px;
        }


        .btn {
            padding: 10px 15px;
            border-radius: 5px;
            color: #fff;
            background-color: #444;
            font-size: 14px;
            cursor: pointer;
            border: none;
            height: 40px;
            width: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: background-color 0.3s;
            box-sizing: border-box;
        }

        .btn:hover {
            background-color: #666;
        }


        form input[type="submit"] {
            display: none;
        }


        form input[type="text"], form textarea {
            width: 100%;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333;
            background-color: #222;
            color: #ffffff;
        }

        .delete-btn {
            margin: 0;
        }
        .filter-modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
        }

        .filter-modal-content {
            background-color: #1a1a1a;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            border-radius: 5px;
            color: #fff;
        }

        .filter-modal-content h2 {
            margin-top: 0;
        }

        .filter-modal-content label {
            display: block;
            margin-bottom: 10px;
        }

        .filter-modal-content input[type="checkbox"] {
            margin-right: 10px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #fff;
            text-decoration: none;
            cursor: pointer;
        }
        .favorite-star span {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 30px;
            cursor: pointer;
            transition: color 0.3s;
        }

        .favorite-star span.filled {
            color: yellow;
        }

        .material-card {
            position: relative;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <nav>
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/materials/search" method="get" style="display:inline;">
            <input type="text" name="query" placeholder="Найти материал...">
            <span class="filter-icon" onclick="openFilterModal()">🔍</span>
        </form>
    </div>


        <c:if test="${user.role == 'ADMIN' || user.role == 'USER'}">
            <a href="${pageContext.request.contextPath}/materials/add" class="add-material-btn">Добавить материал</a>
        </c:if>

        <c:if test="${user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/users" class="view-users-btn">Все пользователи</a>
        </c:if>

        <a href="${pageContext.request.contextPath}/favorites">Избранное</a>
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
                        <img src="${pageContext.request.contextPath}/photo/${material.photoId}" alt="${material.title}">
                        <div class="material-content">
                            <h3>${material.title}</h3>
                        </div>
                    </a>
                    <div class="favorite-star" data-material-id="${material.id}">
                        <span>★</span>
                    </div>
                            <c:if test="${user.role == 'ADMIN'}">
                                <div class="material-actions">
                                    <a href="${pageContext.request.contextPath}/materials/edit?materialId=${material.id}" class="btn">Изменить</a>
                                    <form action="${pageContext.request.contextPath}/materials/${material.id}" method="post" class="delete-btn">
                                        <input type="hidden" name="materialId" value="${material.id}">
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
<div id="filterModal" class="filter-modal">
    <div class="filter-modal-content">
        <span class="close" onclick="closeFilterModal()">&times;</span>
        <h2>Фильтр по категориям</h2>
        <form action="${pageContext.request.contextPath}/materials/search" method="get">
            <c:forEach var="category" items="${categories}">
                <label>
                    <input type="checkbox" name="categories" value="${category.id}">
                    ${category.name}
                </label>
            </c:forEach>
            <input type="submit" value="Применить фильтр">
        </form>
    </div>
</div>

<script>

    function getCookie(name) {
        const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        return match ? decodeURIComponent(match[2]).split('|') : [];
    }

    function setCookie(name, value, days) {
        let expires = "";
        if (days) {
            const date = new Date();
            date.setTime(date.getTime() + days * 24 * 60 * 60);
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + encodeURIComponent(value) + expires + "; path=/";
    }

    function updateFavoriteStar(materialId, isFavorite) {
        const star = document.querySelector('.favorite-star[data-material-id="' + materialId + '"]');

        if (star) {
               if (isFavorite) {
                   star.classList.add('filled');
               } else {
                   star.classList.remove('filled');
               }

           }
        }


    window.onload = function () {
        const favoriteIds = getCookie('favorites');
        favoriteIds.forEach(materialId => {
            updateFavoriteStar(materialId, true);
        });
    };
    document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.favorite-star').forEach(star => {
        star.addEventListener('click', function () {
            const materialId = this.getAttribute('data-material-id');

            let favoriteIds = getCookie('favorites');
            let isFavorite = favoriteIds.includes(materialId);

            if (favoriteIds.includes(materialId)) {
                favoriteIds = favoriteIds.filter(id => id !== materialId);
                updateFavoriteStar(materialId, false);
            } else {
                favoriteIds.push(materialId);
                updateFavoriteStar(materialId, true);
            }

            setCookie('favorites', favoriteIds.join('|'), 7);
            updateFavoriteStar(materialId, !isFavorite);
        });
    });
    });



    function openFilterModal() {
        document.getElementById('filterModal').style.display = 'block';
    }

    function closeFilterModal() {
        document.getElementById('filterModal').style.display = 'none';
    }

    window.onclick = function(event) {
        var modal = document.getElementById('filterModal');
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }
</script>
</body>
</html>

