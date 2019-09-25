<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 19.09.2019
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%request.setCharacterEncoding("UTF-8");%>
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

        input[type=text] {
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

        input[type=text]:hover {
            border: 2px solid #cccccc;
        }

        input[type=text]:focus {
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

<jsp:useBean id="user" scope="request" class="model.User"/>

<c:set var="title" value="Register new user"/>
<c:set var="action" value="insert"/>
<c:if test="${user.id != null}">
    <c:set var="title" value="Edit user"/>
    <c:set var="action" value="update"/>
</c:if>

<footer>
    <h1 class="outer" id="footer_text"><c:out value='${title}'/></h1>
</footer>

<div class="outer">
    <div id="form">
        <form action="<c:out value='${action}'/>" method="post">
            <c:if test="${user.id != null}">
                <input type="hidden" name="id" value="<c:out value='${user.id}'/>"/>
            </c:if>
            <label for="email">E-mail</label>
            <input type="text" id="email" name="email" size="45"
                   value="<c:out value='${user.email}'/>"
            >
            <label for="password">Password</label>
            <input type="text" id="password" name="password" size="45"
                   value="<c:out value='${user.password}'/>"
            >
            <label for="fname">First name</label>
            <input type="text" id="fname" name="first_name" size="45"
                   value="<c:out value='${user.firstName}'/>"
            >
            <label for="lname">Last name</label>
            <input type="text" id="lname" name="last_name" size="45"
                   value="<c:out value='${user.lastName}'/>"
            >
            <label for="country">Country</label>
            <input type="text" id="country" name="country" size="45"
                   value="<c:out value='${user.country}'/>"
            >
            <input type="submit" value="Save">
        </form>
    </div>
</div>
<div class="outer">
    <p>
        <a href="list">List All Users</a>
    </p>
</div>
</body>
</html>
