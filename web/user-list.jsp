<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 15.09.2019
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.User" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="new">Add New User</a>
        &nbsp;&nbsp;&nbsp;
        <a href="list">List All Users</a>

    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <%--            <th>ID</th>--%>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <%--        <jsp:useBean id="listUser" scope="request" type="java.util.List"/>--%>
        <%--        <jsp:useBean id="listUser" scope="request" type="java.util.List"/>--%>
        <%--        <jsp:useBean id="listUser" scope="request" type="java.util.List"/>--%>
        <%
            List<User> listUser = (List<User>) request.getAttribute("listUser");
            for (User user : listUser) {%>
        <tr>
            <%--                <td><c:out value="${user.id}"/></td>--%>
            <td><%=user.getFirstName()%>
            </td>
            <td><%=user.getLastName()%>
            </td>
            <td><%=user.getEmail()%>
            </td>
            <td><%=user.getCountry()%>
            </td>
            <td>
                <a href="edit?id=<%=user.getId()%>">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="delete?id=<%=user.getId()%>">Delete</a>
            </td>
        </tr>
        <%}%>
    </table>
</div>
</body>
</html>
