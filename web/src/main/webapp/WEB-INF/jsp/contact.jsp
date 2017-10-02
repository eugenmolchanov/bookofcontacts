<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 15.09.2017
  Time: 17:21
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
    <title><fmt:message key="contact"/></title>
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
        <div class="edit" onclick="edit()">
            <img src="${pageContext.request.contextPath}/assests/images/edit.png"/>
        </div>
        <br>
        <div class="photo">
            <c:choose>
                <c:when test="${requestScope.contact.photo.id != 0}">
                    <img src="/controller?command=displayContactPhoto&id=${requestScope.contact.photo.id}"
                         class="photoImage"/>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/assests/images/profile_photo.png" class="defaultImage"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="fullName" id="fullName">
            <label for="firstName"><fmt:message key="first_name"/> </label><br>
            <input type="text" name="firstName" id="firstName"
                   required value="${requestScope.contact.firstName}"
                   class="form-control" readonly/><br>
            <div class="nameMessage" id="firstNameMessage"></div>
            <br>
            <label for="lastName"><fmt:message key="last_name"/> </label><br>
            <input type="text" name="lastName" id="lastName" required readonly
                   value="${requestScope.contact.lastName}"
                   class="form-control"/><br>
            <div class="nameMessage" id="secondNameMessage"></div>
            <br>
            <label for="middleName"><fmt:message key="middle_name"/> </label><br>
            <input type="text" name="middleName" id="middleName" readonly
                   value="${requestScope.contact.middleName}"
                   class="form-control"/><br>
            <div class="nameMessage" id="middleNameMessage"></div>
            <br>
        </div>
        <div class="secondCol" id="secondCol">
            <label for="birthday"><fmt:message key="birthday"/> </label><br>
            <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'" readonly
                   value="${requestScope.contact.birthday}"
                   onblur="if(this.value==''){this.type='text'}"
                   class="form-control"/><br>
            <div class="nameMessage" id="birthdayMessage"></div>
            <br>
            <label for="gender"><fmt:message key="gender"/> </label><br>
            <select name="gender" id="gender" class="form-control" readonly="">
                <option class="gender" selected disabled hidden>${requestScope.contact.gender}</option>
                <option value="Мужчина"><fmt:message key="male"/></option>
                <option value="Женщина"><fmt:message key="female"/></option>
            </select><br>
            <div class="nameMessage" id="genderMessage"></div>
            <br>
            <label for="nationality"><fmt:message key="nationality"/> </label><br>
            <input type="text" name="nationality" id="nationality" value="${requestScope.contact.nationality}" readonly
                   class="form-control"/><br>
            <div class="nameMessage" id="nationalityMessage"></div>
            <br>
        </div>
        <div class="marStatus" id="marStatus">
            <label for="maritalStatus"><fmt:message key="marital_status"/> </label><br>
            <select name="maritalStatus" id="maritalStatus" class="form-control" readonly="">
                <option selected disabled hidden>${requestScope.contact.maritalStatus}</option>
                <option value="Не женат"><fmt:message key="not_married_male"/></option>
                <option value="Не замужем"><fmt:message key="not_married_female"/></option>
                <option value="Женат"><fmt:message key="married_male"/></option>
                <option value="Замужем"><fmt:message key="married_female"/></option>
                <option value="Состою в гражданском браке"><fmt:message key="civil_marriage"/></option>
                <option value="Вдовец"><fmt:message key="widower"/></option>
                <option value="Вдова"><fmt:message key="widow"/></option>
            </select><br>
            <div class="nameMessage" id="maritalStatusMessage"></div>
        </div>
        <div class="employDiv" id="employDiv">
            <label for="webSite"><fmt:message key="employment_place"/> </label><br>
            <input type="text" name="employmentPlace" id="employmentPlace"
                   value="${requestScope.contact.employmentPlace}" readonly
                   class="form-control"/><br>
            <div class="nameMessage" id="employmentPlaceMessage"></div>
        </div>
        <div class="groupDiv" id="groupDiv">
            <label for="email"><fmt:message key="contact_group"/> </label><br>
            <select name="contactGroup" id="contactGroup" class="form-control" readonly="">
                <option selected disabled hidden>${requestScope.contact.contactGroup}</option>
                <option value="Семья"><fmt:message key="family"/></option>
                <option value="Друзья"><fmt:message key="friends"/></option>
                <option value="Коллеги"><fmt:message key="colleagues"/></option>
                <option value="Соседи"><fmt:message key="neighbours"/></option>
            </select><br>
            <div class="nameMessage" id="contactGroupMessage"></div>
        </div>
        <br>
        <div class="websiteDiv" id="websiteDiv">
            <label for="webSite"><fmt:message key="website"/> </label><br>
            <input type="url" name="webSite" id="webSite" value="${requestScope.contact.webSite}" readonly
                   class="form-control"/><br>
            <div class="nameMessage" id="websiteMessage"></div>
        </div>
        <div class="emailDiv" id="emailDiv">
            <label for="email"><fmt:message key="email"/> </label><br>
            <input type="email" name="email" id="email" value="${requestScope.contact.email}" class="form-control"
                   readonly/><br>
            <div class="nameMessage" id="emailMessage"></div>
        </div>

        <div class="addressInfo"><h3><fmt:message key="address"/></h3></div>
        <div class="countryDiv" id="countryDiv">
            <label for="country"><fmt:message key="country"/> </label><br>
            <input type="text" name="country" id="country" value="${requestScope.contact.country}" readonly
                   class="form-control"/><br>
            <div class="nameMessage" id="countryMessage"></div>
        </div>
        <div class="cityDiv" id="cityDiv">
            <label for="city"><fmt:message key="city"/> </label><br>
            <input type="text" name="city" id="city" class="form-control" value="${requestScope.contact.city}"
                   readonly/><br>
            <div class="nameMessage" id="cityMessage"></div>
        </div>
        <div class="streetDiv" id="streetDiv">
            <label for="street"><fmt:message key="street"/> </label><br>
            <input type="text" name="street" id="street" value="${requestScope.contact.street}" readonly
                   class="form-control"/><br>
            <div class="nameMessage" id="streetMessage"></div>
        </div>
        <div class="houseNumberDiv" id="houseNumberDiv">
            <label for="houseNumber"><fmt:message key="house_number"/> </label><br>
            <input type="text" name="houseNumber" id="houseNumber" class="form-control"
                   value="${requestScope.contact.houseNumber}" readonly/><br>
            <div class="nameMessage" id="houseNumberMessage"></div>
        </div>
        <div class="flatNumberDiv" id="flatNumberDiv">
            <label for="flatNumber"><fmt:message key="flat_number"/> </label><br>
            <input type="number" min="1" name="flatNumber" id="flatNumber" pattern="[0-9]" title="Only digits" readonly
                   value="${requestScope.contact.flatNumber}" class="form-control"/><br>
            <div class="nameMessage" id="flatNumberMessage"></div>
        </div>
        <div class="postcodeDiv" id="postcodeDiv">
            <label for="postalIndex"><fmt:message key="postcode"/> </label><br>
            <input type="number" min="1" name="postalIndex" id="postalIndex" readonly
                   <c:if test="${requestScope.contact.postcode > 0}">value="${requestScope.contact.postcode}" </c:if>
                   pattern="[0-9]" title="Only digits" class="form-control"/><br>
            <div class="nameMessage" id="postalIndexMessage"></div>
        </div>

        <div class="phonesInfo"><h3><fmt:message key="phones"/></h3></div>
        <div class="phoneInfo" id="phoneInfo">
            <table class="phoneTable" id="phoneTable">
                <caption class="phoneTitle">
                    <div class="phoneButtons" id="phoneButtons">
                        <button type="button" name="phonePopup" onclick="addPhone()" class="createButton">
                            <fmt:message key="create"/></button>
                        <button type="button" name="phonePopup"
                                onclick="editPhone()" class="editButton">
                            <fmt:message key="edit"/></button>
                        <button type="button" name="deletePhone" onclick="deletePhoneFromTable()"
                                class="deleteButton"><fmt:message key="delete"/></button>
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
                <c:forEach var="phone" items="${requestScope.contact.phones}">
                    <tr>
                        <td class="phoneCheckboxTd" id="phoneCheckboxTd">
                            <input type="checkbox" name="phoneId" value="${phone.id}"/>
                            <input type="hidden" name="phone" id="id_${phone.id}" value=""/>
                        </td>
                        <td class="phoneTd">
                            <input id="countryCodeId_${phone.id}" value="${phone.countryCode}"
                                   class="phoneCountryCode"/>
                        </td>
                        <td class="phoneTd">
                            <input id="operatorCodeId_${phone.id}" value="${phone.operatorCode}"
                                   class="phoneOperatorCode">
                        </td>
                        <td class="phoneTd">
                            <input id="numberId_${phone.id}" value="${phone.number}" class="phoneNumber">
                        </td>
                        <td class="phoneTd">
                            <input id="typeId_${phone.id}" value="${phone.type}" class="phoneType">
                        </td>
                        <td class="phoneTd">
                            <input id="commentId_${phone.id}" value="${phone.comment}" class="phoneComment">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
        <br>
        <div class="attachmentsInfo"><h3><fmt:message key="attachments"/></h3></div>
        <div class="attachmentInfo" id="attachmentInfo">
            <table class="attachmentTable">
                <caption class="attachmentTitle">
                    <div class="attachmentButtons" id="attachmentButtons">
                        <button type="button" name="attachmentPopup" onclick="addAttachments()" class="createButton">
                            <fmt:message key="create"/></button>
                        <button type="button" name="attachmentPopup" onclick="editAttachment()" class="editButton">
                            <fmt:message key="edit"/></button>
                        <button type="button" name="deleteAttachment" onclick="deleteAttachmentFromTable()"
                                class="deleteButton"><fmt:message key="delete"/></button>
                    </div>
                </caption>
                <thead>
                <tr>
                    <th class="attachmentCheckbox"></th>
                    <th class="attachmentTitle"><fmt:message key="attachment"/></th>
                    <th class="attachmentDate"><fmt:message key="attachment"/></th>
                    <th class="attachmentComment"><fmt:message key="comment"/></th>
                </tr>
                </thead>
                <tbody id="attachmentRows">
                <c:forEach var="attachment" items="${requestScope.contact.attachments}">
                    <tr>
                        <td class="attachmentCheckboxTd" id="attachmentCheckboxTd">
                            <input type="checkbox" name="attachmentId" value="${attachment.id}"/>
                            <input type="hidden" name="attachment" id="id_${attachment.id}" value=""/>
                        </td>
                        <td class="attachmentTd">
                            <a href="/controller?command=downloadAttachment&id=${attachment.id}">${attachment.fileName}</a>
                            <input type="hidden" id="attachmentFileId_${attachment.id}" value="${attachment.fileName}"
                                   class="attachmentTitle"/>
                        </td>
                        <td class="attachmentTd">
                            <input id="attachmentDateId_${attachment.id}" value="${attachment.date}"
                                   class="attachmentDate">
                        </td>
                        <td class="attachmentTd">
                            <input id="attachCommentId_${attachment.id}" value="${attachment.commentary}"
                                   class="attachmentCommentary">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br>
        <input type="submit" onclick="createContact()" value="<fmt:message key="edit"/> "
               class="bigEditButton" id="bigEditButton"/>
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
                <button type="button" id="savePhone" onclick="editPhoneFields()" class="btn btn-primary"><fmt:message
                        key="save"/></button>
                <button type="button" id="cancelPhone" onclick="closePhonePopup()" class="btn btn-success"><fmt:message
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
                <button type="button" id="saveAttachment" onclick="editAttachmentFields()" class="btn btn-primary">
                    <fmt:message key="save"/></button>
                <button type="button" id="cancelAttachment" onclick="closeAttachmentPopup()" class="btn btn-success">
                    <fmt:message
                            key="exit"/></button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
