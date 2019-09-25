<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>User Management Application</title>
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans:300&display=swap" rel="stylesheet">
    <style>
        <%@include file="general.css"%>
        <%@include file="form.css"%>
    </style>
</head>
<body>
<header>
    <div class="content">
        <h1 class="content" id="header_text">Login</h1>
    </div>
</header>

<div class="content">
    <form action="login" method="post">
        <label for="email">E-mail</label>
        <input type="text" id="email" name="email" size="45"
               value="<c:out value='${user.email}' />"
        >
        <label for="password">Password</label>
        <input type="password" id="password" name="password" size="45"
               value="<c:out value='${user.password}' />"
        >
        <input type="submit" value="Login">
    </form>
</div>
<div class="content">
    <p>
        Don’t have an account? <a href="new">Sign up now</a>
        <%--        <a href="list">List All Users</a>--%>
    </p>
</div>
</body>
</html>
