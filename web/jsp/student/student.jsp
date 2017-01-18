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
    <title><fmt:message key="student.account.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/vendor/js/jquery-3.1.1.slim.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/vendor/js/bootstrap.min.js"></script>
</head>
<body>

<%@ include file="../../WEB-INF/jspf/header.jspf"%>

<main class="container">
    <div class="actions">
        <a class="btn btn-primary" href="app?c=courses&lang=${locale}"><fmt:message key="student.account.select_course"/></a>
    </div>
    <div class="row">
        <c:if test="${not empty courses}">
            <c:forEach items="${courses}" var="course" varStatus="loop">
                <div class="col-md-6">
                    <div class="course">
                        <h3>${course.name}
                            <c:if test="${course.status.id == 2}">
                                <span class="open"><fmt:message key="student_table.started"/></span>
                            </c:if>
                        </h3>
                        <div class="inner">
                            <p><b><fmt:message key="course_table.details"/>:</b> ${course.description}</p>
                            <p><b><fmt:message key="course_table.status"/>:</b> ${course.status.courseStatus}</p>
                            <p><b><fmt:message key="course_table.teacher"/>:</b> ${course.teacher.name} ${course.teacher.surname}</p>
                            <p><b><fmt:message key="course_table.listeners"/></b> ${course.listeners} / ${course.maxListeners}</p>
                            <c:forEach items="${course.ratingList}" var="rating">
                                <c:if test="${not empty rating.rating}">
                                    <c:if test="${course.id == rating.course.id}">
                                        <p><b><fmt:message key="student_table.rating"/></b> ${rating.rating}</p>
                                        <p><b><fmt:message key="student_table.comment"/></b> ${rating.comment}</p>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal${course.id}"><fmt:message key="student.account.leave"/></button>
                        <div class="modal fade" id="myModal${course.id}" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title"><fmt:message key="student.account.leave"/></h4>
                                    </div>
                                    <div class="modal-body">
                                        <p><fmt:message key="student.account.leavec"/></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="course_form.cancel"/></button>
                                        <button type="button" class="btn btn-primary"><a href="app?c=leave_course&cid=${course.id}&lang=${locale}"><fmt:message key="student.account.leave"/></a></button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div><!-- /.modal -->
                    </div>
                </div>
                <c:if test="${(loop.index + 1) mod 2 == 0}">
                    <div class="clearfix"></div>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${empty courses}">
        <div class="course">
            <h3><fmt:message key="student.account.no_courses"/></h3>
        </div>
    </c:if>
</main>

<%@ include file="../../WEB-INF/jspf/footer.jspf"%>

</body>
</html>
