<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 16.09.2017
  Time: 21:39
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
    <title><fmt:message key="search"/> </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assests/css/style.css?v=3"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=1"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div><p>${requestScope.message}</p></div>
<div class="search">
<form class="searchForm" action="/controller" method="post" onsubmit="return search()">
    <input type="hidden" value="search" name="command"/>
    <label for="firstName"><fmt:message key="first_name"/> </label><br>
    <input type="text" id="firstName" name="firstName" placeholder="<fmt:message key="first_name"/> "/><br>
    <label for="lastName"><fmt:message key="last_name"/> </label><br>
    <input type="text" id="lastName" name="lastName" placeholder="<fmt:message key="last_name"/> "/><br>
    <label for="middleName"><fmt:message key="middle_name"/> </label><br>
    <input type="text" id="middleName" name="middleName" placeholder="<fmt:message key="middle_name"/> "/><br>
    <label for="birthdayFrom"><fmt:message key="birthday"/> </label><br>
    <input type="date" id="birthdayFrom" name="birthdayFrom" placeholder="<fmt:message key="birthday"/> "/><br>
    <label for="birthdayTo"><fmt:message key="birthday"/> </label><br>
    <input type="date" id="birthdayTo" name="birthdayTo" placeholder="<fmt:message key="birthday"/> "/><br>
    <label for="male"><fmt:message key="gender"/> </label><br>
    <input type="radio" id="male" name="gender" value="male"/><fmt:message key="male"/>
    <input type="radio" id="female" name="gender" value="female"/><fmt:message key="female"/> <br>
    <label for="maritalStatus"><fmt:message key="marital_status"/> </label><br>
    <input type="text" id="maritalStatus" name="maritalStatus" placeholder="<fmt:message key="marital_status"/> "/><br>
    <label for="nationality"><fmt:message key="nationality"/> </label><br>
    <input type="text" id="nationality" name="nationality" placeholder="<fmt:message key="nationality"/> "/><br>
    <label for="country"><fmt:message key="country"/> </label><br>
    <input type="text" id="country" name="country" placeholder="<fmt:message key="country"/> "/><br>
    <label for="city"><fmt:message key="city"/> </label><br>
    <input type="text" id="city" name="city" placeholder="<fmt:message key="city"/> "/><br>
    <label for="street"><fmt:message key="street"/> </label><br>
    <input type="text" id="street" name="street" placeholder="<fmt:message key="street"/> "/><br>
    <label for="houseNumber"><fmt:message key="house_number"/> </label><br>
    <input type="text" id="houseNumber" name="houseNumber" placeholder="<fmt:message key="house_number"/> "/><br>
    <label for="flatNumber"><fmt:message key="flat_number"/> </label><br>
    <input type="number" id="flatNumber" name="flatNumber" placeholder="<fmt:message key="flat_number"/> "/><br>
    <label for="postalIndex"><fmt:message key="postcode"/> </label><br>
    <input type="number" id="postalIndex" name="postalIndex" placeholder="<fmt:message key="postcode"/> "/><br>
    <input type="submit" value="<fmt:message key="search"/> " onclick="search()"/>
</form>
</div>
</body>
</html>
