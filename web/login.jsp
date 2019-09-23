<%--
  Created by IntelliJ IDEA.
  User: eu
  Date: 23.09.2019
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div style="text-align: center;">
    <h1>User Management</h1>
</div>
<div align="center">
    <form action="login" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>E-mail:</th>
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
                <td colspan="2" align="center">
                    <input type="submit" value="Login"/>
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
