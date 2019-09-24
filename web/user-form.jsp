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
</head>
<body>
<%--value="<jsp:useBean id="user" scope="request" type="model.User"/>--%>
<div style="text-align: center;">
    <h1>User Management</h1>
</div>
<div align="center">
    <c:if test="${user != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${user == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${user != null}">
                            Edit User
                        </c:if>
                        <c:if test="${user == null}">
                            Add New User
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${user != null}">
                    <input type="hidden" name="id" value="<c:out value='${user.id}' />"/>
                </c:if>
                <tr>
                    <th>User Email:</th>
                    <td>
                        <input type="text" name="email" size="45"
                               value="<c:out value='${user.email}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td>
                        <input type="text" name="password" size="45"
                               value="<c:out value='${user.password}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>First Name:</th>
                    <td>
                        <input type="text" name="first_name" size="45"
                               value="<c:out value='${user.firstName}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Last Name:</th>
                    <td>
                        <input type="text" name="last_name" size="45"
                               value="<c:out value='${user.lastName}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Country:</th>
                    <td>
                        <input type="text" name="country" size="15"
                               value="<c:out value='${user.country}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
</div>
<div style="text-align: center;">
    <h4>
        <a href="list">List All Users</a>
    </h4>
</div>
</body>
</html>
