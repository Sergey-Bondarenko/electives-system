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
                        <td><a class="btn btn-primary" href="app?c=update_account&id=${account.id}&lang=${locale}"><fmt:message key="table.edit"/></a>
                        <a class="btn btn-danger" href="app?c=delete_account&id=${account.id}&lang=${locale}"><fmt:message key="table.delete"/></a></td>
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
