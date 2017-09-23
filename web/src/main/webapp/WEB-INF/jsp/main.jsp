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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assests/css/style.css?v=2">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=10"></script>
</head>
<body>
<jsp:include page="language.jsp"/>
<div class="main">
    <form action="/controller?command=deleteContacts" method="post" name="deleteForm" onsubmit="return deleteContacts()">
        <input type="checkbox" onclick="toggle(this)" id="chooseAll"/>
        <input type="submit" value="<fmt:message key="delete"/>" class="button" onclick="deleteContacts()"/>
        <a href="/controller?command=redirect&form=createContact"><fmt:message key="create_contact"/></a>
        <a href="/controller?command=redirect&form=search"><fmt:message key="search"/></a>
        <input type="submit" value="<fmt:message key="send_email"/> " formaction="/controller?command=redirect&form=sendEmail" onclick="return chooseEmail()">
        <a href="/controller?command=redirect&form=sendEmail"><fmt:message key="send_email"/></a>
        <%--<input type="button" value="<fmt:message key="create_contact"/>" class="button" onclick="goToCreateForm()"/>--%>
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
                    <td><input type="checkbox" name="id" email="${contact.email}" value="${contact.id}"/>
                        <input type="hidden" name="email" id="useEmail_${contact.email}" value=""/>
                    </td>
                    <td>
                        <a href="controller?command=redirect&form=contact">${contact.lastName} ${" "} ${contact.firstName}</a>
                    </td>
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
<div class="pagination">
    <c:set var="start" value="${requestScope.startContactNumber}"/>
    <c:set var="step" value="${requestScope.quantityOfContacts}"/>
    <c:set var="count" value="${requestScope.numberOfContacts}"/>
    <c:set var="maxPage"/>
    <c:choose>
        <c:when test="${count % step != 0}">
            <c:set var="maxPage" value="${count / step}"/>
             </c:when>
        <c:otherwise>
            <c:set var="maxPage" value="${(count / step) + 1}"/>
        </c:otherwise>
    </c:choose>

    <div id="pageInfo">${start + 1}${" - "}${start + step}${" "}
        <fmt:message key="from"/>
        ${" "}${count}</div>
    <c:if test="${start != 0}">
        <div id="previous"><a
                href="/controller?command=${requestScope.command}&startContactNumber=${start - step}&quantityOfContacts=${step}">
            <fmt:message key="previous"/></a></div>
    </c:if>
    <div id="pageNumbers">
        <c:forEach var="i" begin="0" end="4">
            <c:if test="${((start + step) / step + i - 2) > 0 && ((start + step) / step + i - 2) <= maxPage + 1}">
                <c:choose>
                    <c:when test="${i != 2}">
                        <fmt:parseNumber var = "page" type = "number" value = "${(start + step) / step + i - 2}" />
                        <a href="/controller?command=${requestScope.command}&startContactNumber=${start + step * (i - 2)}&quantityOfContacts=${step}">
                                ${page}</a> </c:when>
                    <c:otherwise>
                        <fmt:parseNumber var = "presentPage" type = "number" value = "${(start + step) / step}" />
                        ${presentPage}${" "}
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </div>
    <c:if test="${(count - start) gt step}">
        <div id="next"><a href="/controller?command=${requestScope.command}&startContactNumber=${start + step}&quantityOfContacts=${step}"><fmt:message key="next"/></a></div>
    </c:if>
</div>
</body>
</html>
