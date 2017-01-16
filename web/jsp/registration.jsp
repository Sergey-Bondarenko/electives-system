<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://electives.ua/taglib/auth" %>
<%@ taglib prefix="n" uri="http://electives.ua/taglib/notification" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <title><fmt:message key="auth.page.registration"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@include file="../WEB-INF/jspf/header.jspf"%>

<main class="container">
    <h3><fmt:message key="auth.page.registration"/> </h3>
    <p><fmt:message key="registration.page.message"/> </p>
    <form method="post" class="row">
        <input type="hidden" name="command" value="registration"/>
        <div class="col-md-5">
            <div class="form-group">
                <label for="name"><fmt:message key="account_form.name"/> :</label>
                <input id="name" type="text" class="form-control" name="name" value="${account.name}"/>
            </div>
            <div class="form-group">
                <label for="name"><fmt:message key="account_form.surname"/> :</label>
                <input id="surname" type="text" class="form-control" name="surname" value="${account.surname}"/>
            </div>
            <div class="form-group">
                <label for="login"><fmt:message key="auth.page.login_form.login"/> :</label>
                <input id="login" type="text" class="form-control" name="login"/ value="${account.login}"/>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="auth.page.login_form.password"/>:</label>
                <input id="password" type="password" class="form-control" name="password"/>
            </div>
            <div class="form-group">
                <label for="check_password"><fmt:message key="registration.page.confirm_pass"/>:</label>
                <input id="check_password" type="password" class="form-control" name="check_password"/>
            </div>

            <input class="btn btn-default" name="submit" type="submit" value="<fmt:message key="registration.page.confirm"/>"/>
        </div>
    </form>
</main>

<%@include file="../WEB-INF/jspf/footer.jspf"%>

</body>
</html>
