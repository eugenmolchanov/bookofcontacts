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
    <title><fmt:message key="search"/></title>
</head>
<body class="searchBody">
<jsp:include page="header.jsp"/>
<div>${requestScope.message}</div>
<div class="searchForm">
    <form action="/controller" method="post" onsubmit="return search()">
        <input type="hidden" value="search" name="command"/>
        <div class="generalInfo"><h3><fmt:message key="general_info"/></h3></div>
        <div id="full-name">
            <div class="nameMessage" id="firstNameMessage"></div>
            <div class="nameMessage" id="secondNameMessage"></div>
            <div class="nameMessage" id="middleNameMessage"></div>
            <input type="text" name="firstName" id="firstName" placeholder="<fmt:message key="first_name"/>${'*'} "
                   class="form-control"/>
            <input type="text" name="lastName" id="lastName" placeholder="<fmt:message key="last_name"/>${'*'}"
                   class="form-control"/>
            <input type="text" name="middleName" id="middleName" placeholder="<fmt:message key="middle_name"/> "
                   class="form-control"/>
        </div>
        <div id="secondLine">
            <div class="nameMessage" id="birthdayFromMessage"></div>
            <div class="nameMessage" id="birthdayToMessage"></div>
            <div class="nameMessage" id="genderMessage"></div>
            <input type="text" name="birthdayFrom" id="birthdayFrom" onfocus="this.type = 'date'"
                   onblur="if(this.value==''){this.type='text'}"
                   placeholder="<fmt:message key="birthday"/> " class="form-control"/>
            <input type="text" name="birthdayTo" id="birthdayTo" onfocus="this.type = 'date'"
                   onblur="if(this.value==''){this.type='text'}"
                   placeholder="<fmt:message key="birthday"/> " class="form-control"/>
            <select name="gender" id="gender" class="form-control">
                <option class="gender" selected disabled hidden><fmt:message key="gender"/></option>
                <option value="Мужчина"><fmt:message key="male"/></option>
                <option value="Женщина"><fmt:message key="female"/></option>
            </select>
        </div>
        <div id="thirdLine">
            <div class="nameMessage" id="nationalityMessage"></div>
            <div class="nameMessage" id="maritalStatusMessage"></div>
            <div class="nameMessage" id="groupMessage"></div>
            <input type="text" name="nationality" id="nationality" placeholder="<fmt:message key="nationality"/>"
                   class="form-control"/>
            <select name="maritalStatus" id="maritalStatus" class="form-control">
                <option selected disabled hidden><fmt:message key="marital_status"/></option>
                <option value="Не женат"><fmt:message key="not_married_male"/></option>
                <option value="Не замужем"><fmt:message key="not_married_female"/></option>
                <option value="Женат"><fmt:message key="married_male"/></option>
                <option value="Замужем"><fmt:message key="married_female"/></option>
                <option value="Состою в гражданском браке"><fmt:message key="civil_marriage"/></option>
                <option value="Вдовец"><fmt:message key="widower"/></option>
                <option value="Вдова"><fmt:message key="widow"/></option>
            </select>
            <select name="contactGroup" id="contactGroup" class="form-control">
                <option selected disabled hidden><fmt:message key="contact_group"/></option>
                <option value="Семья"><fmt:message key="family"/></option>
                <option value="Друзья"><fmt:message key="friends"/></option>
                <option value="Коллеги"><fmt:message key="colleagues"/></option>
                <option value="Соседи"><fmt:message key="neighbours"/></option>
            </select>
        </div>
        <div class="addressInfo"><h3><fmt:message key="address"/></h3></div>
        <div id="fifthLine">
            <div class="nameMessage" id="countryMessage"></div>
            <div class="nameMessage" id="cityMessage"></div>
            <div class="nameMessage" id="streetMessage"></div>
            <input type="text" name="country" id="country" placeholder="<fmt:message key="country"/> "
                   class="form-control"/>
            <input type="text" name="city" id="city" placeholder="<fmt:message key="city"/> " class="form-control"/>
            <input type="text" name="street" id="street" placeholder="<fmt:message key="street"/> "
                   class="form-control"/>
        </div>
        <div id="sixthLine">
            <div class="nameMessage" id="houseNumberMessage"></div>
            <div class="nameMessage" id="flatNumberMessage"></div>
            <div class="nameMessage" id="postalIndexMessage"></div>
            <input type="text" name="houseNumber" id="houseNumber"
                   placeholder="<fmt:message key="house_number"/> " class="form-control"/>
            <input type="number" min="1" name="flatNumber" id="flatNumber"
                   placeholder="<fmt:message key="flat_number"/> "
                   pattern="[0-9]" title="Only digits" class="form-control"/>
            <input type="number" min="1" name="postalIndex" id="postalIndex"
                   placeholder="<fmt:message key="postcode"/> "
                   pattern="[0-9]" title="Only digits" class="form-control"/>
        </div>
        <div class="searchButtonDiv">
            <input type="submit" value="<fmt:message key="search"/> " onclick="search()" class="searchButton"/>
        </div>
    </form>
</div>
</body>
</html>
