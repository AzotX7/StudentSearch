<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Избранные материалы</title>
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
    </style>
</head>
<body>

<header>
    <div class="logo">StudentSearch</div>
    <nav>
        <a href="${pageContext.request.contextPath}/materials">Все материалы</a>
        <a href="${pageContext.request.contextPath}/materials/profile">Личный кабинет</a>
        <a href="${pageContext.request.contextPath}/logout">Выход</a>
    </nav>
</header>

<div class="main-title">Избранные материалы</div>

<main>
    <c:choose>
        <c:when test="${not empty favoriteMaterials}">
            <c:forEach var="material" items="${favoriteMaterials}">
                <div class="material-card">
                    <a href="${pageContext.request.contextPath}/materials/view?materialId=${material.id}">
                        <img src="${pageContext.request.contextPath}/photo/${material.photoId}" alt="${material.title}">
                        <div class="material-content">
                            <h3>${material.title}</h3>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Нет избранных материалов.</p>
        </c:otherwise>
    </c:choose>
</main>

</body>
</html>
