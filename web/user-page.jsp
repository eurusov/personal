<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>User Management Application</title>
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans:300&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: sans-serif;
            margin: 0;
            padding: 0;
        }

        footer {
            text-align: left;
            color: white;
            background-color: #86a1bd;
            margin: 0;
            padding: 1px;
            height: 83px;
        }

        #footer_text {
            margin-top: 36px;
        }

        #table_caption {
            color: slategrey;
            text-align: left;
        }

        .outer {
            margin-left: 120px;
        }

        table {
            width: 34em;
        }

        table, td {
            color: darkslategray;
            background: #edf1f5;
            border: 2px solid white;
            border-collapse: collapse;
            padding: 10px;
            text-align: left;
        }

        .field_name {
            width: 6em;
            color: slategrey;
            background: #edf1f5;
        }

        #link {
            padding-top: 20px;
        }

        a:link, a:visited {
            background-color: #98b7d4;
            color: white;
            padding: 12px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
        }

        a:hover, a:active {
            background-color: #369cf5;
        }
    </style>
</head>
<body>
<jsp:useBean id="user" scope="request" class="model.User"/>
<footer>
    <h1 class="outer" id="footer_text">Welcome, <c:out value='${user.firstName}'/></h1>
</footer>
<div class="outer">
    <table>
        <caption><h3 id="table_caption">Your profile details:</h3></caption>
        <tr>
            <td class="field_name">E-mail:</td>
            <td><c:out value="${user.email}"/></td>
        </tr>
        <tr>
            <td class="field_name">First name:</td>
            <td><c:out value="${user.firstName}"/></td>
        </tr>
        <tr>
            <td class="field_name">Last name:</td>
            <td><c:out value="${user.lastName}"/></td>
        </tr>
        <tr>
            <td class="field_name">Country:</td>
            <td><c:out value="${user.country}"/></td>
        </tr>
    </table>
</div>
<div class="outer" id="link">
    <a href="edit?id=<c:out value='${user.id}' />">Edit profile</a>
</div>
</body>
</html>
