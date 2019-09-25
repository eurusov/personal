<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>User Management Application</title>
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans:300&display=swap" rel="stylesheet">
    <%--    <link href="general.css" rel="stylesheet" type="text/css" >--%>
    <%--    <link href="${pageContext.request.contextPath}/general.css" rel="stylesheet" type="text/css">--%>
    <style>
        <%@include file="general.css"%>
        #users {
            margin-top: 30px;
            border-collapse: collapse;
            width: 1180px;
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

        #th_id {
            width: 3%;
        }

        #th_email {
            width: 8%;
        }

        #th_psw {
            width: 8%;
        }

        #th_fname {
            width: 9%;
        }

        #th_lname {
            width: 9%;
        }

        #th_country {
            width: 6%;
        }

        #th_role {
            width: 4%;
        }

        #th_action {
            width: 6%;
        }

        .action_link {
            margin-right: 16px;
            color: #46c24a;
            text-decoration: none;
        }

        .action_link:hover {
            color: #c71a1a;
        }
    </style>
</head>
<body>
<header>
    <div class="content">
        <h1 id="header_text">List of users</h1>
        <a id="logout_link" class="link_button" href="logout">Logout</a>
    </div>
</header>
<div class="content">
    <table id="users">
        <tr>
            <th id="th_id">ID</th>
            <th id="th_email">E-mail</th>
            <th id="th_psw">Password</th>
            <th id="th_fname">First Name</th>
            <th id="th_lname">Last Name</th>
            <th id="th_country">Country</th>
            <th id="th_role">User role</th>
            <th id="th_action">Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td>
                    <a class="action_link" href="edit?id=<c:out value='${user.id}' />">Edit</a>
                    <a class="action_link" href="delete?id=<c:out value='${user.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a id="add_link" class="link_button" href="new">Add User</a>
</div>

</body>
</html>
