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
    <title><fmt:message key="admin.manager.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/vendor/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.btn-red').click(function(){
                if (confirm ('<fmt:message key="admin.manager.delete"/>')){
                    return true;
                }
                return false;
            })
        });
    </script>
</head>
<body>

<%@ include file="../../WEB-INF/jspf/header.jspf"%>

<main class="container">
    <div class="actions">
        <a class="btn btn-success" href="app?c=add_course&lang=${locale}"><fmt:message key="course_table.add"/></a>
    </div>

    <c:forEach items="${courses}" var="course">
        <div class="row">
            <div class="col-md-6">
                <div class="course">
                    <h3>${course.name}
                        <c:if test="${course.status.id == 1}">
                            <span class="open"><fmt:message key="course_table.open"/></span>
                        </c:if>
                    </h3>
                    <div class="inner">
                        <p><b><fmt:message key="course_table.details"/>:</b> ${course.description}</p>
                        <p><b><fmt:message key="course_table.status"/>:</b> ${course.status.courseStatus}</p>
                        <p><b><fmt:message key="course_table.teacher"/>:</b> ${course.teacher.name} ${course.teacher.surname}</p>
                        <p><b><fmt:message key="course_table.listeners"/></b> ${course.listeners} / ${course.maxListeners}</p>
                    </div>
                    <a class="btn btn-primary" href="app?c=update_course&cid=${course.id}&lang=${locale}"><fmt:message key="table.edit"/></a>
                    <a class="btn btn-danger" href="app?c=delete_course&cid=${course.id}&lang=${locale}"><fmt:message key="table.delete"/></a>
                </div>
            </div>
        </div>
    </c:forEach>

    <div class="actions">
        <a class="btn btn-success" href="app?c=add_course&lang=${locale}"><fmt:message key="course_table.add"/></a>
    </div>
</main>

<%@ include file="../../WEB-INF/jspf/footer.jspf"%>

</body>
</html>