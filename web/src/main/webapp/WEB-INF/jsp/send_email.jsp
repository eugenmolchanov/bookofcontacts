<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 17.09.2017
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title><fmt:message key="send_email"/></title>
</head>
<body class="sendEmailBody">
<jsp:include page="header.jsp"/>
<div class="sendEmail">
    <div class="message">${requestScope.messageText}</div>
    <form class="emailForm" id="emailForm" name="emailForm" action="/controller" method="post"
          onsubmit="return sendEmail()">
        <input type="hidden" name="command" value="sendEmail"/>
        <c:if test="${requestScope.message != null}">
            <input type="button" value="<fmt:message key="send_email"/> "
                   onclick="toEmailForm()" class="emailButton"/><br><br>
        </c:if>
        <label for="addressees"><fmt:message key="to_whom"/> </label><br>
        <input type="text" id="addressees" name="addressees"
               value="<c:forEach var="email" items="${requestScope.emails}"><c:if test="${email != \"\"}">${email}${"  "}</c:if></c:forEach>"
               class="form-control"/>
        <div class="nameMessage" id="addresseesMessage">${requestScope.validation.addresseesMessage}</div><br><br>
        <label for="topic"><fmt:message key="topic"/> </label><br>
        <input type="text" id="topic" name="topic" placeholder="<fmt:message key="topic"/> " class="form-control"
               value="${requestScope.message.topic}"/><br>
        <c:if test="${requestScope.message == null}">
            <select id="template" name="template" onchange="chooseTemplate()" class="form-control">
                <option selected disabled hidden><fmt:message key="choose_template"/></option>
                <option value="birthday"><fmt:message key="birthday_congratulations"/></option>
                <option value="newYear"><fmt:message key="new_year_congratulations"/></option>
            </select>
        </c:if>
    </form>
    <br>
    <label for="message"><fmt:message key="message"/> </label><br>
    <textarea name="message" id="message" form="emailForm" rows="10" cols="100"
              class="form-control">${requestScope.message.text}</textarea>
    <div class="nameMessage" id="textMessage">${requestScope.validation.textMessage}</div><br>
    <c:if test="${requestScope.message == null}">
        <input type="submit" onclick="sendEmail() " value="<fmt:message key="send"/> " form="emailForm"
               class="emailSubmit"/>
    </c:if>
</div>
</body>
</html>
