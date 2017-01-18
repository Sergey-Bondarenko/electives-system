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
    <title><fmt:message key="admin.accounts.title"/></title>
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
        <a class="btn btn-success" href="app?c=add_account&lang=${locale}"><fmt:message key="account_table.add"/></a>
    </div>

    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th><fmt:message key="account_form.name"/></th>
                    <th><fmt:message key="account_form.surname"/></th>
                    <th><fmt:message key="account_form.type"/></th>
                    <th><fmt:message key="account_table.login"/></th>
                    <th><fmt:message key="account_form.edit"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td>${account.name}</td>
                        <td>${account.surname}</td>
                        <td>${account.userType.userType}</td>
                        <td>${account.login}</td>
                        <td>
                            <a class="btn btn-primary" href="app?c=update_account&id=${account.id}&lang=${locale}"><fmt:message key="table.edit"/></a>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal${account.id}"><fmt:message key="table.delete"/></button>
                            <div class="modal fade" id="myModal${account.id}" role="dialog">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title"><fmt:message key="table.delete"/></h4>
                                        </div>
                                        <div class="modal-body">
                                            <p><fmt:message key="account_form.delete_text"/></p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="course_form.cancel"/></button>
                                            <button type="button" class="btn btn-primary"><a href="app?c=delete_account&id=${account.id}&lang=${locale}"><fmt:message key="table.delete"/></a></button>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div><!-- /.modal -->
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="actions">
        <a class="btn btn-success" href="app?c=add_account&lang=${locale}"><fmt:message key="account_table.add"/></a>
    </div>

</main>
<%@include file="../../WEB-INF/jspf/footer.jspf"%>
</body>
</html>
