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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css?v=64">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/bootstrap.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=32"></script>
</head>
<body>
<div class="header">
    <div class="main_page" onclick="toMainPage()">
        <fmt:message key="to_main_page"/>
    </div>
    <div class="messages" onclick="toMessagePage()">
        <fmt:message key="messages"/>
    </div>
    <div class="createContact" onclick="toCreateContact()">
        <fmt:message key="create_contact"/>
    </div>
    <div class="search" onclick="toSearchPage()">
        <fmt:message key="search"/>
    </div>
    <div class="en" onclick="toEnglish()">
        En
    </div>
    <div class="ru" onclick="toRussian()">
        Ru
    </div>
    <div class="by" onclick="toBelorussian()">
        By
    </div>
</div>
</body>
</html>
