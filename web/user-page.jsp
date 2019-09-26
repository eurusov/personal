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

        #footer_text {
            margin-top: 36px;
        }

        #table_caption {
            text-align: left;
            text-transform: uppercase;
        }

        #users {
            margin-top: 30px;
            border-collapse: collapse;
            width: 480px;
        }

        #users td, #users th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #users tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #users tr:hover {
            background-color: #ddd;
        }

        #users th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #4CAF50;
            color: white;
        }

        .field_name {
            width: 120px;
        }

        #edit_link {
            float: left;
        }
    </style>
</head>
<body>
<%--<jsp:useBean id="user" scope="request" class="model.User"/>--%>
<jsp:useBean id="loggedUser" scope="session" class="model.User"/>
<header>
    <div class="content">
        <h1 id="header_text">Welcome, <c:out value='${loggedUser.firstName}'/></h1>
        <c:if test="${loggedUser.id != null}">
            <a id="logout_link" class="link_button" href="logout">Logout</a>
        </c:if>
    </div>
</header>
<div class="content">
    <table id="users">
        <caption><p id="table_caption">Your profile details:</p></caption>
        <tr>
            <td class="field_name">E-mail:</td>
            <td><c:out value="${loggedUser.email}"/></td>
        </tr>
        <tr>
            <td class="field_name">First name:</td>
            <td><c:out value="${loggedUser.firstName}"/></td>
        </tr>
        <tr>
            <td class="field_name">Last name:</td>
            <td><c:out value="${loggedUser.lastName}"/></td>
        </tr>
        <tr>
            <td class="field_name">Country:</td>
            <td><c:out value="${loggedUser.country}"/></td>
        </tr>
    </table>
    <a id="edit_link" class="link_button" href="edit?id=<c:out value='${loggedUser.id}' />">Edit profile</a>
</div>
</body>
</html>
