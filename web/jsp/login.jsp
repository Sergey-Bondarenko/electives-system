<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://electives.ua/taglib/auth" %>
<%@ taglib prefix="n" uri="http://electives.ua/taglib/notification" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:account(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="auth.page.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@include file="../WEB-INF/jspf/header.jspf"%>

<main class="container">
    <h3><fmt:message key="auth.page.title"/> </h3>
    <p><fmt:message key="auth.page.message"/> </p>
    <form method="post" class="row">
        <input type="hidden" name="command" value="login"/>
        <div class="col-md-5">
            <div class="form-group">
                <label for="login"><fmt:message key="auth.page.login_form.login"/> :</label>
                <input id="login" type="text" class="form-control" name="login"/>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="auth.page.login_form.password"/>:</label>
                <input id="password" type="password" class="form-control" name="password"/>
            </div>
            <input type="submit" class="btn btn-default" value="<fmt:message key="auth.page.login_form.submit"/>"/>
        </div>
    </form>
</main>

<%@include file="../WEB-INF/jspf/footer.jspf"%>
</body>
</html>