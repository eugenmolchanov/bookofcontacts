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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title><fmt:message key="contacts"/></title>
</head>
<body class="mainBody">
<jsp:include page="header.jsp"/>
<div class="main">
    <form action="/controller?command=deleteContacts" method="post" name="deleteForm">
        <div class="mainCheckbox">
            <input type="checkbox" onclick="mainModule.toggle(this)" id="chooseAll"/>
        </div>
        <input type="submit" value="<fmt:message key="delete"/>" class="deleteButton" onclick="return mainModule.deleteContacts()"/>
        <input type="submit" value="<fmt:message key="send_email"/> "
               formaction="/controller?command=redirect&form=sendEmail" onclick="return mainModule.chooseEmail()"
               class="emailButton"/>
        <c:choose>
            <c:when test="${requestScope.successMessage != null}">
                <div class="messageInfo">
                    <div class="successImage">
                        <img src="${pageContext.request.contextPath}/assests/images/check.png"/></div>
                        ${requestScope.successMessage}</div>
            </c:when>
            <c:when test="${requestScope.warningMessage != null}">
                <div class="messageInfo">
                    <div class="warningImage">
                        <img src="${pageContext.request.contextPath}/assests/images/warning.png"/></div>
                        ${requestScope.warningMessage}</div>
            </c:when>
        </c:choose>
        <div class="createContactButton" title="<fmt:message key="create_contact"/>">
            <img src="${pageContext.request.contextPath}/assests/images/add.png" onclick="headerModule.toCreateContact()">
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th></th>
                <th class="th"><fmt:message key="full_name"/></th>
                <th class="th"><fmt:message key="birthday"/></th>
                <th class="th"><fmt:message key="country"/></th>
                <th class="th"><fmt:message key="city"/></th>
                <th class="th"><fmt:message key="street"/></th>
                <th class="th"><fmt:message key="house_number"/></th>
                <th class="th"><fmt:message key="flat_number"/></th>
                <th class="th"><fmt:message key="postcode"/></th>
                <th class="th"><fmt:message key="employment_place"/></th>
                <th class="th"><fmt:message key="contact_group"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="contact" items="${requestScope.contacts}">
                <tr class="contactRow">
                    <td class="th"><input type="checkbox" name="id" email="${contact.email}" value="${contact.id}"/>
                        <input type="hidden" name="email" id="useEmail_${contact.email}" value=""/>
                    </td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.lastName} ${" "} ${contact.firstName}</a></td>
                    <td onclick="mainModule.findContact(${contact.id})"><fmt:formatDate value="${contact.birthday}" pattern="dd.MM.yyyy"/></td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.country}</td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.city}</td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.street}</td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.houseNumber}</td>
                    <td onclick="mainModule.findContact(${contact.id})"><c:if test="${contact.flatNumber > 0}">${contact.flatNumber}</c:if></td>
                    <td onclick="mainModule.findContact(${contact.id})"><c:if test="${contact.postcode > 0}">${contact.postcode}</c:if></td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.employmentPlace}</td>
                    <td onclick="mainModule.findContact(${contact.id})">${contact.contactGroup}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="mainPagePagination">
        <c:set var="start" value="${requestScope.startContactNumber}"/>
        <c:set var="step" value="${requestScope.quantityOfContacts}"/>
        <c:set var="count" value="${requestScope.numberOfContacts}"/>
        <c:set var="size" value="${fn:length(requestScope.contacts)}"/>
        <c:set var="maxPage"/>
        <c:choose>
            <c:when test="${count / step > 1}">
                <fmt:parseNumber var="maxPage" type="number" value="${count / step + 1}"/>
            </c:when>
            <c:otherwise>
                <c:set var="maxPage" value="${1}"/>
            </c:otherwise>
        </c:choose>

        <div id="pageInfo">
            <c:choose>
                <c:when test="${size != 0}">
                    ${start + 1}
                </c:when>
                <c:otherwise>
                    ${0}
                </c:otherwise>
            </c:choose>
            ${" - "}${start + size}${" "}
            <fmt:message key="from"/>
            ${" "}${count}</div>
        ${"  "}<br>
        <fmt:message key="page"/>${" "}
        <c:if test="${start != 0}">
            <div id="previous"><a
                    href="/controller?command=${requestScope.command}&startContactNumber=${start - step}&quantityOfContacts=${step}">
                <fmt:message key="previous"/></a></div>
        </c:if>
        <div id="pageNumbers">
            <c:forEach var="i" begin="0" end="4">
                <c:if test="${((start + step) / step + i - 2) > 0 && ((start + step) / step + i - 2) <= maxPage}">
                    <c:choose>
                        <c:when test="${i != 2}">
                            <fmt:parseNumber var="page" type="number" value="${(start + step) / step + i - 2}"/>
                            <a href="/controller?command=${requestScope.command}&startContactNumber=${start + step * (i - 2)}&quantityOfContacts=${step}">
                                    ${page}</a> </c:when>
                        <c:otherwise>
                            <fmt:parseNumber var="presentPage" type="number" value="${(start + step) / step}"/>
                            ${presentPage}${" "}
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
        </div>
        <c:if test="${(count - start) gt step}">
            <div id="next"><a
                    href="/controller?command=${requestScope.command}&startContactNumber=${start + step}&quantityOfContacts=${step}"><fmt:message
                    key="next"/></a></div>
        </c:if>
        <div class="rowNumber">
            <fmt:message key="display"/>${" "}
            <c:choose>
                <c:when test="${step == 10}">
                    10${" "}
                </c:when>
                <c:otherwise>
                    <a href="/controller?command=${requestScope.command}&startContactNumber=0&quantityOfContacts=10">10</a>${" "}
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${step == 20}">
                    20${" "}
                </c:when>
                <c:otherwise>
                    <a href="/controller?command=${requestScope.command}&startContactNumber=0&quantityOfContacts=20">20</a>${" "}
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/main_module.js?v=11"></script>
<script>
    var messages = {};
    <c:forEach var="message" items="${requestScope.validationMessages}">
    messages['${message.key}'] = '${message.value}';
    </c:forEach>
</script>
</body>
</html>
