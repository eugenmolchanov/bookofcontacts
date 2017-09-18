<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 15.09.2017
  Time: 16:52
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
    <title><fmt:message key="create_contact"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assests/css/style.css?v=3">
    <script type="text/javascript" src="${pageContext.request.contextPath}/assests/js/js.js?v=15"></script>
</head>
<body>
<jsp:include page="language.jsp"/>
<div><p>${requestScope.message}</p></div>
<div class="contact_form">
    <form action="/controller?command=createNewContact" method="post" name="createForm" onsubmit="createContact()"
          enctype="multipart/form-data">
        <div class="photoTitle"><fmt:message key="contact_photo"/> </div>
        <label for="image"><fmt:message key="choose_photo"/> </label><br>
        <input type="file" name="image" id="image"/><br>
        <div class="generalInfo"><fmt:message key="general_info"/> </div>
        <label for="firstName"><fmt:message key="first_name"/></label><br>
        <input type="text" name="firstName" id="firstName" placeholder="<fmt:message key="first_name"/> " required/><br>
        <label for="lastName"><fmt:message key="last_name"/> </label><br>
        <input type="text" name="lastName" id="lastName" placeholder="<fmt:message key="last_name"/>" required/><br>
        <label for="middleName"><fmt:message key="middle_name"/> </label><br>
        <input type="text" name="middleName" id="middleName" placeholder="<fmt:message key="middle_name"/> "/><br>
        <label for="birthday"><fmt:message key="birthday"/> </label><br>
        <input type="date" name="birthday" id="birthday" placeholder="<fmt:message key="birthday"/> "/><br>
        <label for="gender"><fmt:message key="gender"/> </label><br>
        <input type="text" name="gender" id="gender" placeholder="<fmt:message key="gender"/> "/><br>
        <label for="nationality"><fmt:message key="nationality"/> </label><br>
        <input type="text" name="nationality" id="nationality" placeholder="<fmt:message key="nationality"/> "/><br>
        <label for="maritalStatus"><fmt:message key="marital_status"/> </label><br>
        <input type="text" name="maritalStatus" id="maritalStatus"
               placeholder="<fmt:message key="marital_status"/> "/><br>
        <label for="webSite"><fmt:message key="website"/> </label><br>
        <input type="url" name="webSite" id="webSite" placeholder="<fmt:message key="website"/> "/><br>
        <label for="email"><fmt:message key="email"/> </label><br>
        <input type="email" name="email" id="email" placeholder="<fmt:message key="email"/> "/><br>
        <label for="employmentPlace"><fmt:message key="employment_place"/> </label><br>
        <input type="text" name="employmentPlace" id="employmentPlace"
               placeholder="<fmt:message key="employment_place"/> "/><br>
        <div class="addressInfo"><fmt:message key="address"/> </div>
        <label for="country"><fmt:message key="country"/> </label><br>
        <input type="text" name="country" id="country" placeholder="<fmt:message key="country"/> "/><br>
        <label for="city"><fmt:message key="city"/> </label><br>
        <input type="text" name="city" id="city" placeholder="<fmt:message key="city"/> "/><br>
        <label for="street"><fmt:message key="street"/> </label><br>
        <input type="text" name="street" id="street" placeholder="<fmt:message key="street"/> "/><br>
        <label for="houseNumber"><fmt:message key="house_number"/> </label><br>
        <input type="text" name="houseNumber" id="houseNumber" placeholder="<fmt:message key="house_number"/> "/><br>
        <label for="flatNumber"><fmt:message key="flat_number"/> </label><br>
        <input type="number" min="1" name="flatNumber" id="flatNumber" placeholder="<fmt:message key="flat_number"/> "
               pattern="[0-9]" title="Only digits"/><br>
        <label for="postalIndex"><fmt:message key="postal_index"/> </label><br>
        <input type="number" min="1" name="postalIndex" id="postalIndex"
               placeholder="<fmt:message key="postal_index"/> "
               pattern="[0-9]" title="Only digits"/><br>
        <div class="phoneInfo"><fmt:message key="phone_list"/> </div>

        <input type="submit" onclick="createContact()" value="<fmt:message key="create"/> ">
    </form>
</div>
</body>
</html>
