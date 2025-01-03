<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: #f4f4f4;
        }

        .error-container {
            text-align: center;
            max-width: 600px;
            padding: 40px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
        }

        .error-title {
            font-size: 2em;
            color: #e74c3c;
            margin-bottom: 10px;
        }

        .error-message {
            font-size: 1.2em;
            color: #555;
            margin-bottom: 20px;
        }

        .home-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            font-size: 1em;
            transition: background-color 0.3s;
        }

        .home-link:hover {
            background-color: #2980b9;
        }


        @media (max-width: 600px) {
            .error-container {
                padding: 20px;
            }

            .error-title {
                font-size: 1.5em;
            }

            .error-message {
                font-size: 1em;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="error-title">Упс! Что-то пошло не так.</h1>
        <p class="error-message">
            ${errorMessage != null ? errorMessage : "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже."}
        </p>
        <a href="${pageContext.request.contextPath}/materials" class="home-link">Вернуться на главную</a>
    </div>
</body>
</html>