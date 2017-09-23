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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assests/css/style.css?v=1">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=3"></script>
</head>
<body>
<jsp:include page="language.jsp"/>
<div class="message">${requestScope.message}</div>
<div class="sendEmail">
    <form class="emailForm" id="emailForm" name="emailForm" action="/controller" method="post"
          onsubmit="return sendEmail()">
        <input type="hidden" name="command" value="sendEmail"/>
        <label for="addressees"><fmt:message key="to_whom"/> </label><br>
        <input type="text" id="addressees" name="addressees"
               value="<c:forEach var="email" items="${requestScope.emails}"><c:if test="${email != \"\"}">${email}${"  "}</c:if></c:forEach>"/><br>
        <label for="topic"><fmt:message key="topic"/> </label><br>
        <input type="text" id="topic" name="topic" placeholder="<fmt:message key="topic"/> "/><br>
        <select id="template" name="template" onchange="chooseTemplate()">
            <option selected disabled><fmt:message key="choose_template"/> </option>
            <option value="birthday"><fmt:message key="birthday_congratulations"/></option>
            <option value="newYear"><fmt:message key="new_year_congratulations"/> </option>
        </select>
    </form>
    <br>
    <label for="message"><fmt:message key="message"/> </label><br>
    <textarea name="message" id="message" form="emailForm" rows="10" cols="100"></textarea><br>
    <input type="submit" onclick="sendEmail() " value="<fmt:message key="send"/> " form="emailForm"/>
</div>
</body>
</html>
