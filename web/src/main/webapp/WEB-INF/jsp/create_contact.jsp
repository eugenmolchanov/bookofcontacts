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
<%@ taglib prefix="Fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title><fmt:message key="create_contact"/></title>
</head>
<body class="create_contact_body">
<jsp:include page="header.jsp"/>
<%--<div><p>${requestScope.message}</p></div>--%>
<div class="contact_form" id="contact_form">
    <div class="messageInfo">${requestScope.message}</div>
    <form action="/controller?command=createNewContact" method="post" name="createForm"
          onsubmit="return createContact()"
          enctype="multipart/form-data" accept-charset="UTF-8" class="form-inline">
        <div class="contactTitle"><h2><fmt:message key="personal_data"/></h2></div>
        <div class="photoInfo"><h3><fmt:message key="contact_photo"/></h3></div>
        <input type="file" name="image" id="image" class="form-control"/><br>
        <div class="generalInfo"><h3><fmt:message key="general_info"/></h3></div>
        <div id="full-name">
            <div class="nameMessage" id="firstNameMessage"></div>
            <div class="nameMessage" id="secondNameMessage"></div>
            <div class="nameMessage" id="middleNameMessage"></div>
            <input type="text" name="firstName" id="firstName" placeholder="<fmt:message key="first_name"/>${'*'} " required
                   class="form-control"/>
            <input type="text" name="lastName" id="lastName" placeholder="<fmt:message key="last_name"/>${'*'}" required
                   class="form-control"/>
            <input type="text" name="middleName" id="middleName" placeholder="<fmt:message key="middle_name"/> "
                   class="form-control"/>
        </div>
        <div id="secondLine">
            <div class="nameMessage" id="birthdayMessage"></div>
            <div class="nameMessage" id="genderMessage"></div>
            <div class="nameMessage" id="nationalityMessage"></div>
            <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'" onblur="if(this.value==''){this.type='text'}"
                   placeholder="<fmt:message key="birthday"/> " class="form-control"/>
            <select name="gender" id="gender" class="form-control">
                <option class="gender" selected disabled hidden><fmt:message key="gender"/></option>
                <option value="Мужчина"><fmt:message key="male"/> </option>
                <option value="Женщина"><fmt:message key="female"/> </option>
            </select>
            <input type="text" name="nationality" id="nationality" placeholder="<fmt:message key="nationality"/>"
                   class="form-control"/>
        </div>
        <div id="thirdLine">
            <div class="nameMessage" id="maritalStatusMessage"></div>
            <div class="nameMessage" id="websiteMessage"></div>
            <div class="nameMessage" id="emailMessage"></div>
            <select name="maritalStatus" id="maritalStatus" class="form-control">
                <option selected disabled hidden><fmt:message key="marital_status"/></option>
                <option value="Не женат"><fmt:message key="not_married_male"/> </option>
                <option value="Не замужем"><fmt:message key="not_married_female"/> </option>
                <option value="Женат"><fmt:message key="married_male"/> </option>
                <option value="Замужем"><fmt:message key="married_female"/> </option>
                <option value="Состою в гражданском браке"><fmt:message key="civil_marriage"/> </option>
                <option value="Вдовец"><fmt:message key="widower"/> </option>
                <option value="Вдова"><fmt:message key="widow"/> </option>
            </select>
            <input type="url" name="webSite" id="webSite" placeholder="<fmt:message key="website"/> "
                   class="form-control"/>
            <input type="email" name="email" id="email" placeholder="<fmt:message key="email"/> " class="form-control"/>
        </div>
        <div id="fourthLine">
            <div class="nameMessage" id="employmentPlaceMessage"></div>
            <div class="nameMessage" id="contactGroupMessage"></div>
            <br>
            <input type="text" name="employmentPlace" id="employmentPlace"
                   placeholder="<fmt:message key="employment_place"/> " class="form-control"/>
            <select name="contactGroup" id="contactGroup" class="form-control">
                <option selected disabled hidden><fmt:message key="contact_group"/></option>
                <option value="Семья"><fmt:message key="family"/> </option>
                <option value="Друзья"><fmt:message key="friends"/> </option>
                <option value="Коллеги"><fmt:message key="colleagues"/> </option>
                <option value="Соседи"><fmt:message key="neighbours"/> </option>
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
        <div class="phonesInfo"><h3><fmt:message key="phones"/></h3></div>
        <div class="phoneInfo" id="phoneInfo">
            <table class="phoneTable" id="phoneTable">
                <caption class="phoneTitle">
                    <div class="phonePopup">
                        <button type="button" name="phonePopup" onclick="addPhone()" class="btn btn-primary">
                            <fmt:message key="create"/></button>
                        <button type="button" name="deletePhone" onclick="deletePhoneFromTable()"
                                class="btn btn-primary"><fmt:message key="delete"/></button>
                    </div>
                </caption>
                <thead>
                <tr>
                    <th class="phoneCheckbox"></th>
                    <th class="phoneCountryCode"><fmt:message key="country_code"/></th>
                    <th class="phoneOperatorCode"><fmt:message key="operator_code"/></th>
                    <th class="phoneNumber"><fmt:message key="phone_number"/></th>
                    <th class="phoneType"><fmt:message key="type"/></th>
                    <th class="phoneComment"><fmt:message key="comment"/></th>
                </tr>
                </thead>
                <tbody id="phoneRows">

                </tbody>
            </table>

        </div>
        <br>
        <div class="attachmentsInfo"><h3><fmt:message key="attachments"/></h3></div>
        <div class="attachmentInfo" id="attachmentInfo">
            <table class="attachmentTable">
                <caption class="attachmentTitle">
                    <div class="attachmentPopup">
                        <button type="button" name="attachmentPopup" onclick="addAttachments()" class="btn btn-primary">
                            <fmt:message key="create"/></button>
                        <button type="button" name="deleteAtatchment" onclick="deleteAttachmentFromTable()"
                                class="btn btn-primary"><fmt:message key="delete"/></button>
                    </div>
                </caption>
                <thead>
                <tr>
                    <th class="attachmentCheckbox"></th>
                    <th class="attachmentFile"><fmt:message key="attachment"/></th>
                    <th class="attachmentComment"><fmt:message key="comment"/></th>
                </tr>
                </thead>
                <tbody id="attachmentRows">

                </tbody>
            </table>
        </div>
        <br>
        <input type="submit" onclick="createContact()" value="<fmt:message key="create"/> "
               class="btn btn-primary btn-lg btn-block"/>
    </form>
</div>
<div class="phonePopupText" id="phonePopup">
    <div class="phoneImage">
        <img src="${pageContext.request.contextPath}/assests/images/phone.jpg">
    </div>
    <div class="phoneForm" id="phoneForm">
        <form>
            <div class="phoneMessage" id="countryCodeMessage"></div>
            <input type="text" id="countryCode" name="countryCode" placeholder="<fmt:message key="country_code"/> "
                   class="form-control" required/>
            <div class="phoneMessage" id="operatorCodeMessage"></div>
            <input type="number" id="operatorCode" name="operatorCode" placeholder="<fmt:message key="operator_code"/> "
                   class="form-control" required/>
            <div class="phoneMessage" id="numberMessage"></div>
            <input type="number" id="number" name="number" placeholder="<fmt:message key="phone_number"/> "
                   class="form-control" required/>
            <div class="phoneMessage" id="typeMessage"></div>
            <select id="type" name="type" class="form-control" required>
                <option selected disabled hidden><fmt:message key="type"/></option>
                <option value="Рабочий"><fmt:message key="work_phone"/></option>
                <option value="Домашний"><fmt:message key="home_phone"/></option>
                <option value="Сотовый"><fmt:message key="mobile_phone"/></option>
            </select>
            <div class="phoneMessage" id="commentMessage"></div>
            <input type="text" id="comment" name="comment"
                   placeholder="<fmt:message key="comment"/> " class="form-control"/><br>
            <div id="phone_buttons" class="phone_buttons">
                <button type="button" id="savePhone" onclick="addPhoneTable()" class="btn btn-primary"><fmt:message
                        key="save"/></button>
                <button type="button" id="cancelPhone" onclick="addPhone()" class="btn btn-success"><fmt:message
                        key="exit"/></button>
            </div>
        </form>
    </div>
</div>
<div class="attachmentPopupText" id="attachmentPopup">
    <div class="fileImage">
        <img src="${pageContext.request.contextPath}/assests/images/file.jpg">
    </div>
    <div class="attachmentForm" id="attachmentForm">
        <form>
            <div class="attachmentMessage" id="attachmentMessage"></div>
            <input type="file" name="attachment" id="attachment" class="form-control"/>
            <div class="attachmentMessage" id="attachCommentMessage"></div>
            <input type="text" id="attachComment" name="attachComment" placeholder="<fmt:message key="comment"/> "
                   class="form-control"/>
            <div class="attachments_buttons">
                <button type="button" id="saveAttachment" onclick="addAttachmentTable()" class="btn btn-primary">
                    <fmt:message key="save"/></button>
                <button type="button" id="cancelAttachment" onclick="addAttachments()" class="btn btn-success">
                    <fmt:message
                            key="exit"/></button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
