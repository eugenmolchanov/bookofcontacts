<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 18.09.2017
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css?v=31">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/bootstrap.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=11"></script>
</head>
<body>
<div class="header">
    <div class="main_page">
        <a href="/controller" tabindex="1"><fmt:message key="to_main_page"/></a>
    </div>
    <div class="messages">
        <a href="/controller" tabindex="1"><fmt:message key="messages"/></a>
    </div>
    <div class="createContact">
        <a href="/controller?command=redirect&form=createContact" tabindex="1"><fmt:message key="create_contact"/></a>
    </div>
    <div class="search">
        <a href="/controller?command=redirect&form=search" tabindex="1"><fmt:message key="search"/></a>
    </div>
    <div class="language">
        <div class="en"><a href="/controller?command=language&language=en_US">En</a></div>
        <div class="ru"><a href="/controller?command=language&language=ru_RU">Ru</a></div>
        <div class="by"><a href="/controller?command=language&language=be_BY">By</a></div>
    </div>
</div>
</body>
</html>
