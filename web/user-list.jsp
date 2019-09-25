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

        .content {
            width: 1180px;
            margin-left: 120px;
        }

        #footer_text {
            padding: 0;
            margin: 36px 0 0;
        }

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

        #_id {
            width: 3%;
        }

        #_email {
            width: 8%;
        }

        #_psw {
            width: 8%;
        }

        #_fname {
            width: 9%;
        }

        #_lname {
            width: 9%;
        }

        #_country {
            width: 6%;
        }

        #_role {
            width: 4%;
        }

        #_acts {
            width: 7%;
        }

        .act_links:link {
            color: #46c24a;
            text-decoration: none;
        }

        .act_links:visited {
            color: #46c24a;
            text-decoration: none;
        }

        .act_links:hover {
            color: #c71a1a;
            text-decoration: none;
        }

        ul {
            width: 100%;
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 12px 26px;
            text-decoration: none;
        }

        li a:hover {
            background-color: #63e067;
        }

        #logout_link {
            float: right;
            background-color: #48a34b;
        }

        #add_link {
            alignment: right;
        }

        #bottom_links {
            margin: 0;
            padding: 0;
            float: right;
        }
    </style>
</head>

<body>
<footer>
    <div class="content">
        <ul>
            <li><h1 id="footer_text">List of users</h1></li>
            <li id="logout_link"><a href="logout">Logout</a></li>
        </ul>

    </div>
</footer>
<div class="content">
    <table id="users">
        <tr>
            <th id="_id">ID</th>
            <th id="_email">E-mail</th>
            <th id="_psw">Password</th>
            <th id="_fname">First Name</th>
            <th id="_lname">Last Name</th>
            <th id="_country">Country</th>
            <th id="_role">User role</th>
            <th id="_acts">Actions</th>
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
                    <a class="act_links" href="edit?id=<c:out value='${user.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="act_links" href="delete?id=<c:out value='${user.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="bottom_links">
        <a id="add_link" class="act_links" href="new">Add User</a>
    </div>
</div>

</body>
</html>
