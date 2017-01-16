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
    <title><fmt:message key="archive.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@ include file="../WEB-INF/jspf/header.jspf"%>

<main class="container" id="courses">
    <c:forEach var="course" items="${archivedcourses}">
        <div class="courses-view">
            <h3>${course.name}</h3>
            <c:set var="status" value="${course.status.courseStatus}"/>
            <p class="status status-${status}"><fmt:message key="course_status.${status}"/></p>
            <p class="details">${course.description}</p>
            <p class="teacher"><fmt:message key="course_table.teacher"/> ${course.teacher.name} ${course.teacher.surname}</p>
            <c:if test="${user_type eq 'teacher'}">
                <p><b><fmt:message key="course_table.listeners"/></b> ${course.listeners} / ${course.maxListeners}</p>
            </c:if>
            <c:if test="${user_type eq 'student'}">
                <c:forEach var="rating" items="${course.ratingList}">
                    <c:if test="${user.id == rating.student.id}">
                        <p><b><fmt:message key="student_table.rating"/></b> ${rating.rating}</p>
                        <p><b><fmt:message key="student_table.comment"/></b> ${rating.comment}</p>
                    </c:if>
                </c:forEach>
            </c:if>
        </div>
    </c:forEach>
</main>

<%@ include file="../WEB-INF/jspf/footer.jspf"%>

</body>
</html>
