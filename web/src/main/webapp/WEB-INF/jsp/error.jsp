<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 14.09.2017
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title><fmt:message key="error"/> </title>
</head>
<body class="errorBody">
<jsp:include page="header.jsp"/>
<div class="error">${requestScope.message}</div>
</body>
</html>
