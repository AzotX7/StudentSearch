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
            background-color: #111;
            margin: 0;
            padding: 0;
            color: #fff;
        }

        header {
            background: #1a1a1a;
            color: #e0e0e0;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.4);
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #e0e0e0;
            margin: 0;
        }

        main {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            background: #222;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
            text-align: center;
        }

        .material {
            border: 1px solid #444;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
            background-color: #333;
            color: #e0e0e0;
        }

        h3 {
            margin: 0;
            color: #white;
        }

        .button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #444;
            color: white;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .button:hover {
            background-color: #666;
        }


        .empty-message {
            color: #e0e0e0;
            font-size: 18px;
        }
    </style>
</head>
<body>
<header>
    <div class="logo">StudentSearch</div>
    <h1>Мои материалы</h1>
</header>

<main>
    <c:if test="${not empty materials}">
        <c:forEach var="material" items="${materials}">
            <div class="material">
                <h3>${material.title}</h3>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty materials}">
        <p class="empty-message">У вас пока нет материалов.</p>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/materials">Перейти ко всем материалам</a>
</main>
</body>
</html>
