<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans:300&display=swap" rel="stylesheet">
    <style>
        body {
            /*font-family: 'Source Sans Pro', sans-serif;*/
            font-family: 'IBM Plex Sans', sans-serif;
            font-size: medium;
            margin: 0;
            padding: 0;
        }

        footer {
            text-align: left;
            color: white;
            background-color: dimgray;
            margin: 0;
            padding: 1px;
            height: 83px;
        }

        #footer_text {
            margin-top: 36px;
        }

        .outer {
            margin-left: 120px;
        }

        #form {
            margin-top: 40px;
            width: 360px;
            border-radius: 6px;
            background-color: #f0f0f0;
            padding: 20px;
        }

        .inpt {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 2px solid #f0f0f0;
            border-radius: 5px;
            -webkit-transition: .8s;
            transition: .8s;
            outline: none;
        }

        .inpt:hover {
            border: 2px solid #cccccc;
        }

        .inpt:focus {
            border: 2px solid #9ce69d;
        }

        input[type=text][disabled] {
            color: #808080;
            background-color: #f7f7f7;
        }

        input[type=submit] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<footer>
    <h1 class="outer" id="footer_text">Login</h1>
</footer>

<div class="outer">
    <div id="form">
        <form action="login" method="post">
            <label for="email">E-mail</label>
            <input class="inpt" type="text" id="email" name="email" size="45"
                   value="<c:out value='${user.email}' />"
            >
            <label for="password">Password</label>
            <input class="inpt" type="password" id="password" name="password" size="45"
                   value="<c:out value='${user.password}' />"
            >
            <input type="submit" value="Login">
        </form>
    </div>
</div>
<div class="outer">
    <p>
        Don’t have an account? <a href="new">Sign up now</a>
        <%--        <a href="list">List All Users</a>--%>
    </p>
</div>
</body>
</html>
