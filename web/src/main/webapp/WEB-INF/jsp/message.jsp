<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 27.09.2017
  Time: 20:38
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
    <title><fmt:message key="messages"/></title>
</head>
<body class="messagesBody">
<jsp:include page="header.jsp"/>
<div class="messageDiv">
    <form action="/controller?command=deleteMessages" method="post" name="deleteForm"
          onsubmit="return deleteMessages()">
        <div class="mainCheckbox">
            <input type="checkbox" onclick="toggle(this)" id="chooseAll"/>
        </div>
        <input type="submit" value="<fmt:message key="delete"/>" class="deleteButton" onclick="deleteContacts()"/>
        <input type="submit" value="<fmt:message key="send_email"/> "
               formaction="/controller?command=redirect&form=sendEmail" class="emailButton"/>
        <table class="table table-hover">
            <thead>
            <tr>
                <th class="messageCheckTh"></th>
                <th class="recipientTh"><fmt:message key="recipient"/></th>
                <th class="messageTh"><fmt:message key="message"/></th>
                <th class="dateTh"><fmt:message key="date"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="message" items="${requestScope.messages}">
                <tr class="messageRow">
                    <td>
                        <input type="checkbox" name="id" value="${message.id}"/>
                    </td>
                    <td onclick="findMessage(${message.id})">
                        <c:forEach var="contact" items="${message.addressees}">
                            <span title="${contact.email}">${contact.firstName}${" "}${contact.lastName}${" "}</span>
                        </c:forEach>
                    </td>
                    <td onclick="findMessage(${message.id})">${message.topic}${" "}${message.text}</td>
                    <td onclick="findMessage(${message.id})">${message.sendingDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="messagePagePagination">
        <c:set var="start" value="${requestScope.startMessageNumber}"/>
        <c:set var="step" value="${requestScope.quantityOfMessages}"/>
        <c:set var="count" value="${requestScope.numberOfMessages}"/>
        <c:set var="size" value="${fn:length(requestScope.messages)}"/>
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
        ${"  "}
        <fmt:message key="page"/>${" "}
        <c:if test="${start != 0}">
            <div id="previous"><a
                    href="/controller?command=${requestScope.command}&startMessageNumber=${start - step}&quantityOfMessages=${step}">
                <fmt:message key="previous"/></a></div>
        </c:if>
        <div id="pageNumbers">
            <c:forEach var="i" begin="0" end="4">
                <c:if test="${((start + step) / step + i - 2) > 0 && ((start + step) / step + i - 2) <= maxPage}">
                    <c:choose>
                        <c:when test="${i != 2}">
                            <fmt:parseNumber var="page" type="number" value="${(start + step) / step + i - 2}"/>
                            <a href="/controller?command=${requestScope.command}&startMessageNumber=${start + step * (i - 2)}&quantityOfMessages=${step}">
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
                    href="/controller?command=${requestScope.command}&startMessageNumber=${start + step}&quantityOfMessages=${step}"><fmt:message
                    key="next"/></a></div>
        </c:if>
    </div>
</div>
</body>
</html>
