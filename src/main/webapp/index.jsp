<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <style>
        body {
            background-color: #c9f3c2;
            font-family: 'Georgia', serif;
            color: #4b2e1e;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            text-align: center;
        }

        h2 {
            font-size: 2em;
            color: #5b3921;
            margin-bottom: 20px;
        }

        .link-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        a {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #b05e27;
            color: white;
            font-weight: bold;
            border-radius: 5px;
            font-size: 1.1em;
            transition: background-color 0.3s ease;
            display: inline-block;
        }

        a:hover {
            background-color: #8c5c3d;
        }
    </style>
</head>
<body>

<div class="link-container">
    <!-- Ссылка к книгам -->
    <a href="/books">К книгам</a>

    <!-- Ссылка к людям -->
    <a href="/people">К людям</a>
</div>
</body>
</html>
