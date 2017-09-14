<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 11.09.2017
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title><fmt:message key="contacts"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assests/css/style.css">
    <script rel="stylesheet" type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js"></script>
</head>
<body>
<div>
<form action="/controller">
    <input type="hidden" name="command" value="deleteContacts">
    <input type="checkbox" onclick="toggle(this)" id="deleteAll"/>
    <input type="submit" value="<fmt:message key="delete"/>" class="delete_button">
    <table>
        <thead>
        <tr>
            <th></th>
            <th><fmt:message key="full_name"/></th>
            <th><fmt:message key="birthday"/></th>
            <th><fmt:message key="country"/></th>
            <th><fmt:message key="city"/></th>
            <th><fmt:message key="street"/></th>
            <th><fmt:message key="house_number"/></th>
            <th><fmt:message key="flat_number"/></th>
            <th><fmt:message key="employment_place"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contact" items="${requestScope.contacts}">
            <tr>
                <td><input type="checkbox" name="id" value="${contact.id}"/></td>
                <td><a href="">${contact.lastName} ${" "} ${contact.firstName}</a></td>
                <td>${contact.birthday}</td>
                <td>${contact.address.country}</td>
                <td>${contact.address.city}</td>
                <td>${contact.address.street}</td>
                <td>${contact.address.houseNumber}</td>
                <td>${contact.address.flatNumber}</td>
                <td>${contact.employmentPlace}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
</div>
</body>
</html>
