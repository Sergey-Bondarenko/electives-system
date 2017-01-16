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
    <title><fmt:message key="main.page.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@ include file="../WEB-INF/jspf/header.jspf"%>

<main class="container">

    <h2 class="heading"><fmt:message key="main.page.intro.title"/></h2>
    <p><fmt:message key="main.page.intro.body"/></p>

    <h2 class="heading"><fmt:message key="main.page.how_to"/> </h2>
    <div class="blocks">
        <div class="block block-enter">
            <h2><fmt:message key="block.login.title"/> </h2>
            <img src="${pageContext.servletContext.contextPath}/images/login.png"/>
            <p><fmt:message key="block.login.text"/> </p>
        </div>

        <div class="block block-students">
            <h2><fmt:message key="block.students.title"/> </h2>
            <img src="${pageContext.servletContext.contextPath}/images/students.png"/>
            <p><fmt:message key="block.students.text"/> </p>
        </div>

        <div class="block block-teacher">
            <h2><fmt:message key="block.teachers.title"/> </h2>

            <img src="${pageContext.servletContext.contextPath}/images/teachers.png"/>
            <p><fmt:message key="block.teachers.text"/> </p>
        </div>
    </div>
</main>

<%@ include file="../WEB-INF/jspf/footer.jspf"%>

</body>
</html>