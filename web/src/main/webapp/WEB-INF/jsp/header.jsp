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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css?v=96">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/bootstrap.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=51"></script>
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
    <c:if test="${applicationScope.alertMessage != null}">
        <div class="alertMessage" onclick="showAlertMessage()">
            <img src="${pageContext.request.contextPath}/assests/images/envelope.png"/>
        </div>
    </c:if>
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
<div class="alertMessageArrow" id="alertMessageArrow"></div>
<c:if test="${applicationScope.alertMessage != null}">
    <div class="alertMessageText" id="alertMessagePopup">
        <fmt:message key="birthday"/>${"!"}<br>
        <c:forEach var="contact" items="${applicationScope.alertMessage}">
            <a href="controller?command=displayContact&id=${contact.id}">${contact.lastName} ${" "} ${contact.firstName}</a><br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
