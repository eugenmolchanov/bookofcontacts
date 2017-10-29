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
<div class="contact_form" id="contact_form">
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
    <div class="messageInfo">${requestScope.message}</div>
    <form action="/controller?command=editContact" method="post" name="createForm"
          onsubmit="return contactModule.contactValidation()"
          enctype="multipart/form-data" accept-charset="UTF-8" class="form-inline">
        <input type="hidden" name="contactId" value="${requestScope.contact.id}">
        <input type="hidden" name="photoId" value="${requestScope.contact.photo.id}">
        <div class="contactTitle"><fmt:message key="personal_data"/></div>
        <div class="edit" onclick="contactModule.edit()" title="<fmt:message key="edit"/>">
            <img src="${pageContext.request.contextPath}/assests/images/edit.png"/>
        </div>
        <br>
        <div class="photo" id="image">
            <c:choose>
                <c:when test="${requestScope.contact.photo.pathToFile != null}">
                    <img src="/controller?command=displayContactPhoto&id=${requestScope.contact.photo.id}"
                         class="photoImage" id="photoImage" onclick="contactModule.addPhoto()"/>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/assests/images/profile_photo.png" class="defaultImage"
                         id="defaultImage"
                         onclick="contactModule.addPhoto()"/>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="fotoMessage"></div>
        </div>
        <div class="fullName" id="fullName">
            <label for="firstName"><fmt:message key="first_name"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.firstNameMessage == null}">
                    <input type="text" name="firstName" id="firstName"
                           value="${requestScope.contact.firstName}"
                           class="form-control" readonly/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="firstName" id="firstName"
                           value="${requestScope.contact.firstName}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="firstNameMessage">${requestScope.validation.firstNameMessage}</div>
            <br>
            <label for="lastName"><fmt:message key="last_name"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.secondNameMessage == null}">
                    <input type="text" name="lastName" id="lastName" readonly
                           value="${requestScope.contact.lastName}"
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="lastName" id="lastName"
                           value="${requestScope.contact.lastName}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="secondNameMessage">${requestScope.validation.secondNameMessage}</div>
            <br>
            <label for="middleName"><fmt:message key="middle_name"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.middleNameMessage == null}">
                    <input type="text" name="middleName" id="middleName" readonly
                           value="${requestScope.contact.middleName}"
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="middleName" id="middleName"
                           value="${requestScope.contact.middleName}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="middleNameMessage">${requestScope.validation.middleNameMessage}</div>
            <br>
        </div>
        <div class="secondCol" id="secondCol">
            <label for="birthday"><fmt:message key="birthday"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.birthdayMessage == null}">
                    <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'" readonly
                           onblur="if(this.value==''){this.type='text'}" value="${requestScope.contact.birthday}"
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'"
                           onblur="if(this.value==''){this.type='text'}"
                           value="${requestScope.contact.birthday}" class="form-control"
                           style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="birthdayMessage">${requestScope.validation.birthdayMessage}</div>
            <br>
            <label for="gender"><fmt:message key="gender"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.genderMessage == null}">
                    <select name="gender" id="gender" class="form-control" readonly>
                        <option class="gender" disabled selected hidden>${requestScope.contact.gender}</option>
                        <option value="M"><fmt:message key="male"/></option>
                        <option value="F"><fmt:message key="female"/></option>
                    </select><br>
                </c:when>
                <c:otherwise>
                    <select name="gender" id="gender" class="form-control" style="border-color: #A94442;">
                        <option class="gender" disabled selected hidden>${requestScope.contact.gender}</option>
                        <option value="M"><fmt:message key="male"/></option>
                        <option value="F"><fmt:message key="female"/></option>
                    </select><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="genderMessage">${requestScope.validation.genderMessage}</div>
            <br>
            <label for="nationality"><fmt:message key="nationality"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.nationalityMessage == null}">
                    <input type="text" name="nationality" id="nationality" value="${requestScope.contact.nationality}"
                           readonly
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="nationality" id="nationality" value="${requestScope.contact.nationality}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="nationalityMessage">${requestScope.validation.nationalityMessage}</div>
            <br>
        </div>
        <div class="marStatus" id="marStatus">
            <label for="maritalStatus"><fmt:message key="marital_status"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.maritalStatusMessage == null}">
                    <select name="maritalStatus" id="maritalStatus" class="form-control" readonly>
                        <option selected disabled hidden>${requestScope.contact.maritalStatus}</option>
                        <option value="NMM"><fmt:message key="not_married_male"/></option>
                        <option value="NMF"><fmt:message key="not_married_female"/></option>
                        <option value="MM"><fmt:message key="married_male"/></option>
                        <option value="MF"><fmt:message key="married_female"/></option>
                        <option value="CM"><fmt:message key="civil_marriage"/></option>
                        <option value="WM"><fmt:message key="widower"/></option>
                        <option value="WF"><fmt:message key="widow"/></option>
                    </select><br>
                </c:when>
                <c:otherwise>
                    <select name="maritalStatus" id="maritalStatus" class="form-control"
                            style="border-color: #A94442;">
                        <option selected disabled hidden>${requestScope.contact.maritalStatus}</option>
                        <option value="NMM"><fmt:message key="not_married_male"/></option>
                        <option value="NMF"><fmt:message key="not_married_female"/></option>
                        <option value="MM"><fmt:message key="married_male"/></option>
                        <option value="MF"><fmt:message key="married_female"/></option>
                        <option value="CM"><fmt:message key="civil_marriage"/></option>
                        <option value="WM"><fmt:message key="widower"/></option>
                        <option value="WF"><fmt:message key="widow"/></option>
                    </select><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="maritalStatusMessage">${requestScope.validation.maritalStatusMessage}</div>
        </div>
        <div class="employDiv" id="employDiv">
            <label for="employmentPlace"><fmt:message key="employment_place"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.employmentPlaceMessage == null}">
                    <input type="text" name="employmentPlace" id="employmentPlace"
                           value="${requestScope.contact.employmentPlace}" readonly
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="employmentPlace" id="employmentPlace"
                           value="${requestScope.contact.employmentPlace}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="employmentPlaceMessage">${requestScope.validation.employmentPlaceMessage}</div>
        </div>
        <div class="groupDiv" id="groupDiv">
            <label for="contactGroup"><fmt:message key="contact_group"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.contactGroupMessage == null}">
                    <select name="contactGroup" id="contactGroup" class="form-control" readonly>
                        <option selected disabled hidden>${requestScope.contact.contactGroup}</option>
                        <option value="FAM"><fmt:message key="family"/></option>
                        <option value="FR"><fmt:message key="friends"/></option>
                        <option value="COL"><fmt:message key="colleagues"/></option>
                        <option value="NB"><fmt:message key="neighbours"/></option>
                    </select><br>
                </c:when>
                <c:otherwise>
                    <select name="contactGroup" id="contactGroup" class="form-control" style="border-color: #A94442;">
                        <option selected disabled hidden>${requestScope.contact.contactGroup}</option>
                        <option value="FAM"><fmt:message key="family"/></option>
                        <option value="FR"><fmt:message key="friends"/></option>
                        <option value="COL"><fmt:message key="colleagues"/></option>
                        <option value="NB"><fmt:message key="neighbours"/></option>
                    </select><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="contactGroupMessage">${requestScope.validation.contactGroupMessage}</div>
        </div>
        <br>
        <div class="websiteDiv" id="websiteDiv">
            <label for="webSite"><fmt:message key="website"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.websiteMessage == null}">
                    <input type="text" name="webSite" id="webSite" value="${requestScope.contact.webSite}" readonly
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="url" name="webSite" id="webSite" value="${requestScope.contact.webSite}"
                           style="border-color: #A94442;"
                           class="form-control"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="websiteMessage">${" "}${requestScope.validation.websiteMessage}</div>
        </div>
        <div class="emailDiv" id="emailDiv">
            <label for="email"><fmt:message key="email"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.emailMessage == null}">
                    <input type="text" name="email" id="email" value="${requestScope.contact.email}"
                           class="form-control"
                           readonly/><br>
                </c:when>
                <c:otherwise>
                    <input type="email" name="email" id="email" value="${requestScope.contact.email}"
                           class="form-control" style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="emailMessage">${requestScope.validation.emailMessage}</div>
        </div>
        <div class="addressInfo"><h3><fmt:message key="address"/></h3></div>
        <div class="countryDiv" id="countryDiv">
            <label for="country"><fmt:message key="country"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.countryMessage == null}">
                    <input type="text" name="country" id="country" value="${requestScope.contact.country}" readonly
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="country" id="country" value="${requestScope.contact.country}"
                           style="border-color: #A94442;"
                           class="form-control"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="countryMessage">${requestScope.validation.countryMessage}</div>
        </div>
        <div class="cityDiv" id="cityDiv">
            <label for="city"><fmt:message key="city"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.cityMessage == null}">
                    <input type="text" name="city" id="city" class="form-control" value="${requestScope.contact.city}"
                           readonly/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="city" id="city" class="form-control" value="${requestScope.contact.city}"
                           style="border-color: #A94442;"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="cityMessage">${requestScope.validation.cityMessage}</div>
        </div>
        <div class="streetDiv" id="streetDiv">
            <label for="street"><fmt:message key="street"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.streetMessage == null}">
                    <input type="text" name="street" id="street" value="${requestScope.contact.street}" readonly
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="street" id="street" value="${requestScope.contact.street}"
                           style="border-color: #A94442;" class="form-control"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="streetMessage">${requestScope.validation.streetMessage}</div>
        </div>
        <div class="houseNumberDiv" id="houseNumberDiv">
            <label for="houseNumber"><fmt:message key="house_number"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.houseNumberMessage == null}">
                    <input type="text" name="houseNumber" id="houseNumber" class="form-control"
                           value="${requestScope.contact.houseNumber}" readonly/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="houseNumber" id="houseNumber" class="form-control"
                           style="border-color: #A94442;" value="${requestScope.contact.houseNumber}"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="houseNumberMessage">${requestScope.validation.houseNumberMessage}</div>
        </div>
        <div class="flatNumberDiv" id="flatNumberDiv">
            <label for="flatNumber"><fmt:message key="flat_number"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.flatNumberMessage == null}">
                    <input type="text" name="flatNumber" id="flatNumber" readonly
                           <c:if test="${requestScope.contact.flatNumber > 0}">value="${requestScope.contact.flatNumber}"</c:if>
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="flatNumber" id="flatNumber"
                           style="border-color: #A94442;"
                           <c:if test="${requestScope.contact.flatNumber > 0}">value="${requestScope.contact.flatNumber}"</c:if>
                           class="form-control"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="flatNumberMessage">${requestScope.validation.flatNumberMessage}</div>
        </div>
        <div class="postcodeDiv" id="postcodeDiv">
            <label for="postalIndex"><fmt:message key="postcode"/> </label><br>
            <c:choose>
                <c:when test="${requestScope.validation.postalIndexMessage == null}">
                    <input type="text" name="postalIndex" id="postalIndex" readonly
                           <c:if test="${requestScope.contact.postcode > 0}">value="${requestScope.contact.postcode}" </c:if>
                           class="form-control"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="postalIndex" id="postalIndex"
                           style="border-color: #A94442;"
                           <c:if test="${requestScope.contact.postcode > 0}">value="${requestScope.contact.postcode}" </c:if>
                           class="form-control"/><br>
                </c:otherwise>
            </c:choose>
            <div class="nameMessage" id="postalIndexMessage">${requestScope.validation.postalIndexMessage}</div>
        </div>

        <div class="phonesInfo"><h3><fmt:message key="phones"/></h3></div>
        <div class="phoneInfo" id="phoneInfo">
            <div class="phoneMessageValidation">${requestScope.validation.phoneMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.phoneInvalidMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.countryCodeMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.operatorCodeMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.phoneNumberMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.phoneTypeMessage}</div>
            <div class="phoneMessageValidation">${requestScope.validation.phoneCommentMessage}</div>
            <div class="phoneButtons" id="phoneButtons">
                <button type="button" name="phonePopup" onclick="contactModule.addPhone()" class="createButton">
                    <fmt:message key="create"/></button>
                <button type="button" name="phonePopup"
                        onclick="contactModule.editPhone()" class="editButton">
                    <fmt:message key="edit"/></button>
                <button type="button" name="deletePhone" onclick="contactModule.deletePhoneFromTable()"
                        class="deleteButton"><fmt:message key="delete"/></button>
            </div>
            <table class="phoneTable" id="phoneTable">
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
                            <input type="hidden" name="phoneId" value="${phone.id}"/>
                        </td>
                        <td class="phoneTd">
                            <input type="text" name="countryCode" id="countryCodeId_${phone.id}"
                                   value="${phone.countryCode}" class="phoneCountryCode" readonly/>
                        </td>
                        <td class="phoneTd">
                            <input type="text" name="operatorCode" id="operatorCodeId_${phone.id}"
                                   value="${phone.operatorCode}" class="phoneOperatorCode" readonly/>
                        </td>
                        <td class="phoneTd">
                            <input type="text" name="number" id="numberId_${phone.id}" value="${phone.number}"
                                   class="phoneNumber" readonly/>
                        </td>
                        <td class="phoneTd">
                            <input type="text" name="type" id="typeId_${phone.id}" value="${phone.type}"
                                   class="phoneType" readonly/>
                        </td>
                        <td class="phoneTd">
                            <input type="text" name="comment" id="commentId_${phone.id}" value="${phone.comment}"
                                   class="phoneComment" readonly/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="hidden" id="clickEdit" value="false"/>
        </div>
        <br>
        <div class="attachmentsInfo"><h3><fmt:message key="attachments"/></h3></div>
        <div class="attachmentInfo" id="attachmentInfo">
            <div class="attachmentMessageValidation">${requestScope.validation.attachmentMessage}</div>
            <div class="attachmentMessageValidation">${requestScope.validation.attachmentInvalidMessage}</div>
            <div class="attachmentMessageValidation">${requestScope.validation.addAttachMessage}</div>
            <div class="attachmentMessageValidation">${requestScope.validation.attachTitleMessage}</div>
            <div class="attachmentMessageValidation">${requestScope.validation.attachCommentMessage}</div>
            <div class="attachmentButtons" id="attachmentButtons">
                <button type="button" name="attachmentPopup" onclick="contactModule.addAttachments()"
                        class="createButton">
                    <fmt:message key="create"/></button>
                <button type="button" name="attachmentPopup" onclick="contactModule.editAttachment()"
                        class="editButton">
                    <fmt:message key="edit"/></button>
                <button type="button" name="deleteAttachment" onclick="contactModule.deleteAttachmentFromTable()"
                        class="deleteButton"><fmt:message key="delete"/></button>
            </div>
            <table class="attachmentTable">
                <thead>
                <tr>
                    <th class="attachmentCheckbox"></th>
                    <th class="attachmentName"><fmt:message key="title"/></th>
                    <th class="attachmentDate"><fmt:message key="date"/></th>
                    <th class="attachmentComment"><fmt:message key="comment"/></th>
                </tr>
                </thead>
                <tbody id="attachmentRows">
                <c:forEach var="attachment" items="${requestScope.contact.attachments}">
                    <tr>
                        <td class="attachmentCheckboxTd" id="attachmentCheckboxTd">
                            <input type="checkbox" name="attachmentId" value="${attachment.id}"/>
                            <input type="hidden" name="attachId" value="${attachment.id}"/>
                        </td>
                        <td class="attachmentTitleTd">
                            <a href="/controller?command=downloadAttachment&id=${attachment.id}"
                               id="hrefId_${attachment.id}">${attachment.fileName}</a>
                            <input type="hidden" name="attachTitle" id="attachmentFileId_${attachment.id}"
                                   value="${attachment.fileName}" class="attachmentTitle" readonly/>
                        </td>
                        <td class="attachmentTd" name="date">
                            <input id="attachmentDateId_${attachment.id}"
                                   value="<fmt:formatDate value="${attachment.date}" pattern="dd.MM.yyyy HH:mm"/>"
                                   class="attachmentDate" readonly>
                        </td>
                        <td class="attachmentTd">
                            <input type="text" name="attachComment" id="attachCommentId_${attachment.id}"
                                   value="${attachment.commentary}" class="attachmentCommentary" readonly/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br>
        <div class="bigEditButtonDiv">
            <input type="submit" value="<fmt:message key="edit"/>"
                   class="bigEditButton" id="bigEditButton"/>
        </div>
        <input type="hidden" name="currentGender" value="${requestScope.currentGender}">
        <input type="hidden" name="currentMaritalStatus" value="${requestScope.currentMaritalStatus}">
        <input type="hidden" name="currentContactGroup" value="${requestScope.currentContactGroup}">
    </form>
</div>
<div class="phonePopupText" id="phonePopup">
    <div class="phoneImage">
        <img src="${pageContext.request.contextPath}/assests/images/phone.jpg">
    </div>
    <div class="phoneForm" id="phoneForm">
        <div class="phoneMessage" id="countryCodeMessage"></div>
        <input type="text" id="countryCode" name="countryCode" placeholder="<fmt:message key="country_code"/> "
               class="form-control"/>
        <div class="phoneMessage" id="operatorCodeMessage"></div>
        <input type="text" id="operatorCode" name="operatorCode" placeholder="<fmt:message key="operator_code"/> "
               class="form-control"/>
        <div class="phoneMessage" id="numberMessage"></div>
        <input type="text" id="number" name="number" placeholder="<fmt:message key="phone_number"/> "
               class="form-control"/>
        <div class="phoneMessage" id="typeMessage"></div>
        <select id="type" name="type" class="form-control">
            <option selected disabled hidden><fmt:message key="type"/></option>
            <option value="<fmt:message key="work_phone"/>"><fmt:message key="work_phone"/></option>
            <option value="<fmt:message key="home_phone"/>"><fmt:message key="home_phone"/></option>
            <option value="<fmt:message key="mobile_phone"/>"><fmt:message key="mobile_phone"/></option>
        </select>
        <div class="phoneMessage" id="commentMessage"></div>
        <input type="text" id="comment" name="comment"
               placeholder="<fmt:message key="comment"/> " class="form-control"/><br>
        <div id="phone_buttons" class="phone_buttons">
            <button type="button" id="savePhone" onclick="contactModule.addPhoneTable()" class="btn btn-success">
                <fmt:message
                        key="save"/></button>
            <button type="button" id="cancelPhone" onclick="contactModule.closePhonePopup()" class="btn btn-primary">
                <fmt:message
                        key="cancel"/></button>
        </div>

    </div>
</div>
<div class="attachmentPopupText" id="attachmentPopup">
    <div class="attachmentRestriction" id="attachmentRestriction"><fmt:message key="restriction.attachment"/></div>
    <div class="fileImage">
        <img src="${pageContext.request.contextPath}/assests/images/file.jpg">
    </div>
    <div class="attachmentForm" id="attachmentForm">
        <form>
            <div class="attachmentMessage" id="attachmentMessage"></div>
            <input type="button" name="attachment" id="attachment" class="chooseButton"
                   onclick="contactModule.uploadAttachment()"
                   value="<fmt:message key="find"/>"/>
            <div class="attachmentMessage" id="attachTitleMessage"></div>
            <input type="text" id="attachTitle" name="attachTitle" placeholder="<fmt:message key="title"/> "
                   class="form-control">
            <div class="attachmentMessage" id="attachCommentMessage"></div>
            <input type="text" id="attachComment" name="attachComment" placeholder="<fmt:message key="comment"/> "
                   class="form-control"/>
            <div class="attachments_buttons">
                <button type="button" id="saveAttachment" onclick="contactModule.addAttachmentTable()"
                        class="btn btn-success">
                    <fmt:message key="save"/></button>
                <button type="button" id="cancelAttachment" onclick="contactModule.closeAttachmentPopup()"
                        class="btn btn-primary">
                    <fmt:message
                            key="cancel"/></button>
            </div>
        </form>
    </div>
</div>
<div class="photoPopupText" id="photoPopup">
    <div class="photoForm" id="photoForm">
        <div class="photoRestriction"><fmt:message key="restriction.photo"/></div>
        <div class="photoPathMessage" id="photoPathMessage"></div>
        <input type="button" name="choosePhoto" id="choosePhoto" class="chooseButton"
               onclick="contactModule.findPhoto()"
               value="<fmt:message key="find"/>"/>
        <div class="photoPath" id="photoPath"></div>
        <div class="photo_buttons">
            <button type="button" id="savePhoto" onclick="contactModule.savePhotoFile()" class="btn btn-success">
                <fmt:message
                        key="save"/></button>
            <button type="button" id="cancelPhoto" onclick="contactModule.deletePhoto()" class="btn btn-primary">
                <fmt:message
                        key="cancel"/></button>
        </div>
    </div>
</div>
<input type="hidden" id="page" value="edit"/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/assests/js/create_contact_module.js?v=9"></script>
</body>
</html>
