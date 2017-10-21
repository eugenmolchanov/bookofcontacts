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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css?v=53">
</head>
<body>
<div class="header">
    <div class="main_page" onclick="headerModule.toMainPage()">
        <img src="${pageContext.request.contextPath}/assests/images/book.png">
    </div>
    <div class="messages" onclick="headerModule.toMessagePage()">
        <fmt:message key="messages"/>
    </div>
    <div class="search" onclick="headerModule.toSearchPage()">
        <fmt:message key="search"/>
    </div>
    <c:if test="${applicationScope.alertMessage != null}">
        <div class="alertMessage" onclick="headerModule.showAlertMessage()">
            <img src="${pageContext.request.contextPath}/assests/images/envelope.png"/>
        </div>
    </c:if>
    <div class="en" onclick="headerModule.toEnglish()">
        <img src="${pageContext.request.contextPath}/assests/images/britain_flag.png"/>
    </div>
    <div class="ru" onclick="headerModule.toRussian()">
        <img src="${pageContext.request.contextPath}/assests/images/russia_flag.png"/>
    </div>
    <div class="by" onclick="headerModule.toBelorussian()">
        <img src="${pageContext.request.contextPath}/assests/images/belarus_flag.png"/>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/header_module.js?v=1"></script>
<script>
    var messages = {};
    <c:forEach var="message" items="${requestScope.validationMessages}">
    messages['${message.key}'] = '${message.value}';
    </c:forEach>
</script>
</body>
</html>
