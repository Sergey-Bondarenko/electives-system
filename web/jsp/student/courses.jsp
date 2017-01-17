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
    <title><fmt:message key="courses.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%@ include file="../../WEB-INF/jspf/header.jspf"%>

<main class="container" id="courses">
    <div class="blocks centred">
        <c:forEach var="course" items="${courses}">
            <div class="courses-view">
                <h3>${course.name}</h3>
                <c:set var="status" value="${course.status.courseStatus}"/>
                <p class="status status-${status}"><fmt:message key="course_status.${status}"/></p>
                <p class="details">${course.description}</p>
                <p class="teacher"><b><fmt:message key="course_table.teacher"/></b> ${course.teacher.name} ${course.teacher.surname}</p>
                <p><b><fmt:message key="course_table.listeners"/></b> ${course.listeners} / ${course.maxListeners}</p>
                <c:choose>
                    <c:when test="${course.status.id != 1}">
                        <p class="not-logged"><fmt:message key="course.started"/></p>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${course.listeners >= course.maxListeners}">
                                <p class="not-logged"><fmt:message key="course.full"/></p>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${empty course.ratingList}">
                                        <a class="btn btn-success" href="app?c=apply&id=${course.id}&lang=${locale}"><fmt:message key="course.apply"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="contains" value="false" />
                                        <c:forEach var="rating" items="${course.ratingList}">
                                            <c:if test="${user.id eq rating.student.id}">
                                                <c:set var="contains" value="true" />
                                            </c:if>
                                        </c:forEach>
                                        <c:choose>
                                            <c:when test="${contains eq true}">
                                                <p class="not-logged"><fmt:message key="course.joined"/></p>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-success" href="app?c=apply&id=${course.id}&lang=${locale}"><fmt:message key="course.apply"/></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</main>

<%@ include file="../../WEB-INF/jspf/footer.jspf"%>

</body>
</html>
