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
        #bottom_link {
            width: 160px;
            float: left;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<jsp:useBean id="user" scope="request" class="model.User"/>
<jsp:useBean id="loggedUser" scope="session" class="model.User"/>

<c:set var="title" value="Register new user"/>
<c:set var="action" value="insert"/>
<c:set var="btm_text" value="Back to Login"/>
<c:if test="${user.id != null}">
    <c:set var="title" value="Edit user"/>
    <c:set var="action" value="edit"/>
</c:if>
<c:if test="${loggedUser.role != null}">
    <c:set var="btm_text" value="User info"/>
</c:if>
<c:if test="${loggedUser.role.equals('admin')}">
    <c:set var="btm_text" value="User list"/>
</c:if>

<header>
    <div class="content">
        <h1 id="header_text"><c:out value='${title}'/></h1>
        <c:if test="${loggedUser.id != null}">
            <a id="logout_link" class="link_button" href="logout">Logout</a>
        </c:if>
    </div>
</header>

<div class="content">
    <form action="<c:out value='${action}'/>" method="post">
        <c:if test="${user.id != null}">
            <input type="hidden" name="id" value="<c:out value='${user.id}'/>"/>
            <input type="hidden" name="role" value="<c:out value='${user.role}'/>"/>
        </c:if>
        <label>E-mail
            <input type="text" name="email" size="60"
                   value="<c:out value='${user.email}'/>">
        </label>
        <label>Password
            <input type="text" name="password" size="60"
                   value="<c:out value='${user.password}'/>">
        </label>
        <label>First name
            <input type="text" name="first_name" size="60"
                   value="<c:out value='${user.firstName}'/>">
        </label>
        <label>Last name
            <input type="text" name="last_name" size="60"
                   value="<c:out value='${user.lastName}'/>">
        </label>
        <label>Country
            <input type="text" name="country" size="60"
                   value="<c:out value='${user.country}'/>">
        </label>
        <input type="submit" value="save">
    </form>
    <a id="bottom_link" class="link_button" href="list"><c:out value="${btm_text}"/></a>
</div>
</body>
</html>
