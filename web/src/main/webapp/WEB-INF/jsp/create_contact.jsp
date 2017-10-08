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
        <input type="button" value="<fmt:message key="choose_photo"/> " class="chooseButton" onclick="addPhoto()"/>
        <div class="nameMessage" id="fotoMessage"></div>
        <div id="image" class="image">

        </div>
        <div class="generalInfo"><h3><fmt:message key="general_info"/></h3></div>
        <div id="full-name">
            <div class="nameMessage" id="firstNameMessage">${requestScope.validation.firstNameMessage}</div>
            <div class="nameMessage" id="secondNameMessage">${requestScope.validation.secondNameMessage}</div>
            <div class="nameMessage" id="middleNameMessage">${requestScope.validation.middleNameMessage}</div>
            <c:choose>
                <c:when test="${requestScope.validation.firstNameMessage == null}">
                    <input type="text" name="firstName" id="firstName"
                           placeholder="<fmt:message key="first_name"/>${'*'} " required
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="firstName" id="firstName"
                           placeholder="<fmt:message key="first_name"/>${'*'} " required
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.secondNameMessage == null}">
                    <input type="text" name="lastName" id="lastName" placeholder="<fmt:message key="last_name"/>${'*'}"
                           required
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="lastName" id="lastName" placeholder="<fmt:message key="last_name"/>${'*'}"
                           required
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.middleNameMessage == null}">
                    <input type="text" name="middleName" id="middleName" placeholder="<fmt:message key="middle_name"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="middleName" id="middleName" placeholder="<fmt:message key="middle_name"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="secondLine">
            <div class="nameMessage" id="birthdayMessage">${requestScope.validation.birthdayMessage}</div>
            <div class="nameMessage" id="genderMessage">${requestScope.validation.genderMessage}</div>
            <div class="nameMessage" id="nationalityMessage">${requestScope.validation.nationalityMessage}</div>
            <c:choose>
                <c:when test="${requestScope.validation.birthdayMessage == null}">
                    <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'"
                           onblur="if(this.value==''){this.type='text'}"
                           placeholder="<fmt:message key="birthday"/> " class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="birthday" id="birthday" onfocus="this.type = 'date'"
                           onblur="if(this.value==''){this.type='text'}"
                           placeholder="<fmt:message key="birthday"/> " class="form-control"
                           style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.genderMessage == null}">
                    <select name="gender" id="gender" class="form-control">
                        <option class="gender" selected disabled hidden><fmt:message key="gender"/></option>
                        <option value="Мужчина"><fmt:message key="male"/></option>
                        <option value="Женщина"><fmt:message key="female"/></option>
                    </select>
                </c:when>
                <c:otherwise>
                    <select name="gender" id="gender" class="form-control" style="border-color: #A94442;">
                        <option class="gender" selected disabled hidden><fmt:message key="gender"/></option>
                        <option value="Мужчина"><fmt:message key="male"/></option>
                        <option value="Женщина"><fmt:message key="female"/></option>
                    </select>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.nationalityMessage == null}">
                    <input type="text" name="nationality" id="nationality"
                           placeholder="<fmt:message key="nationality"/>"
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="nationality" id="nationality"
                           placeholder="<fmt:message key="nationality"/>"
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="thirdLine">
            <div class="nameMessage" id="maritalStatusMessage">${requestScope.validation.maritalStatusMessage}</div>
            <div class="nameMessage" id="employmentPlaceMessage">${requestScope.validation.employmentPlaceMessage}</div>
            <div class="nameMessage" id="contactGroupMessage">${requestScope.validation.contactGroupMessage}</div>
            <c:choose>
                <c:when test="${requestScope.validation.maritalStatusMessage == null}">
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
                </c:when>
                <c:otherwise>
                    <select name="maritalStatus" id="maritalStatus" class="form-control" style="border-color: #A94442;">
                        <option selected disabled hidden><fmt:message key="marital_status"/></option>
                        <option value="Не женат"><fmt:message key="not_married_male"/></option>
                        <option value="Не замужем"><fmt:message key="not_married_female"/></option>
                        <option value="Женат"><fmt:message key="married_male"/></option>
                        <option value="Замужем"><fmt:message key="married_female"/></option>
                        <option value="Состою в гражданском браке"><fmt:message key="civil_marriage"/></option>
                        <option value="Вдовец"><fmt:message key="widower"/></option>
                        <option value="Вдова"><fmt:message key="widow"/></option>
                    </select>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.employmentPlaceMessage == null}">
                    <input type="text" name="employmentPlace" id="employmentPlace"
                           placeholder="<fmt:message key="employment_place"/> " class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="employmentPlace" id="employmentPlace"
                           placeholder="<fmt:message key="employment_place"/> " class="form-control"
                           style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.contactGroupMessage == null}">
                    <select name="contactGroup" id="contactGroup" class="form-control">
                        <option selected disabled hidden><fmt:message key="contact_group"/></option>
                        <option value="Семья"><fmt:message key="family"/></option>
                        <option value="Друзья"><fmt:message key="friends"/></option>
                        <option value="Коллеги"><fmt:message key="colleagues"/></option>
                        <option value="Соседи"><fmt:message key="neighbours"/></option>
                    </select>
                </c:when>
                <c:otherwise>
                    <select name="contactGroup" id="contactGroup" class="form-control" style="border-color: #A94442;">
                        <option selected disabled hidden><fmt:message key="contact_group"/></option>
                        <option value="Семья"><fmt:message key="family"/></option>
                        <option value="Друзья"><fmt:message key="friends"/></option>
                        <option value="Коллеги"><fmt:message key="colleagues"/></option>
                        <option value="Соседи"><fmt:message key="neighbours"/></option>
                    </select>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="fourthLine">
            <div class="nameMessage" id="websiteMessage">${requestScope.validation.websiteMessage}</div>
            <br>
            <c:choose>
                <c:when test="${requestScope.validation.websiteMessage == null}">
                    <input type="url" name="webSite" id="webSite" placeholder="<fmt:message key="website"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="url" name="webSite" id="webSite" placeholder="<fmt:message key="website"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="emailLine">
            <div class="nameMessage" id="emailMessage">${requestScope.validation.emailMessage}</div>
            <br>
            <c:choose>
                <c:when test="${requestScope.validation.emailMessage == null}">
                    <input type="email" name="email" id="email" placeholder="<fmt:message key="email"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="email" name="email" id="email" placeholder="<fmt:message key="email"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="addressInfo"><h3><fmt:message key="address"/></h3></div>
        <div id="fifthLine">
            <div class="nameMessage" id="countryMessage">${requestScope.validation.countryMessage}</div>
            <div class="nameMessage" id="cityMessage">${requestScope.validation.cityMessage}</div>
            <div class="nameMessage" id="streetMessage">${requestScope.validation.streetMessage}</div>
            <c:choose>
                <c:when test="${requestScope.validation.countryMessage == null}">
                    <input type="text" name="country" id="country" placeholder="<fmt:message key="country"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="country" id="country" placeholder="<fmt:message key="country"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.cityMessage == null}">
                    <input type="text" name="city" id="city" placeholder="<fmt:message key="city"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="city" id="city" placeholder="<fmt:message key="city"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.streetMessage == null}">
                    <input type="text" name="street" id="street" placeholder="<fmt:message key="street"/> "
                           class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="street" id="street" placeholder="<fmt:message key="street"/> "
                           class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div id="sixthLine">
            <div class="nameMessage" id="houseNumberMessage">${requestScope.validation.houseNumberMessage}</div>
            <div class="nameMessage" id="flatNumberMessage">${requestScope.validation.flatNumberMessage}</div>
            <div class="nameMessage" id="postalIndexMessage">${requestScope.validation.postalIndexMessage}</div>
            <c:choose>
                <c:when test="${requestScope.validation.houseNumberMessage == null}">
                    <input type="text" name="houseNumber" id="houseNumber"
                           placeholder="<fmt:message key="house_number"/> " class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="houseNumber" id="houseNumber"
                           placeholder="<fmt:message key="house_number"/> " class="form-control"
                           style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.flatNumberMessage == null}">
                    <input type="number" min="1" name="flatNumber" id="flatNumber"
                           placeholder="<fmt:message key="flat_number"/> "
                           pattern="[0-9]" title="Only digits" class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="number" min="1" name="flatNumber" id="flatNumber"
                           placeholder="<fmt:message key="flat_number"/> "
                           pattern="[0-9]" title="Only digits" class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.validation.postalIndexMessage == null}">
                    <input type="number" min="1" name="postalIndex" id="postalIndex"
                           placeholder="<fmt:message key="postcode"/> "
                           pattern="[0-9]" title="Only digits" class="form-control"/>
                </c:when>
                <c:otherwise>
                    <input type="number" min="1" name="postalIndex" id="postalIndex"
                           placeholder="<fmt:message key="postcode"/> "
                           pattern="[0-9]" title="Only digits" class="form-control" style="border-color: #A94442;"/>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="phonesInfo"><h3><fmt:message key="phones"/></h3></div>
        <div class="phoneInfo" id="phoneInfo">
            <table class="phoneTable" id="phoneTable">
                <caption class="phoneTitle">
                    <div class="phonePopup">
                        <button type="button" name="phonePopup" onclick="addPhone()" class="createButton">
                            <fmt:message key="create"/></button>
                        <button type="button" name="deletePhone" onclick="deletePhoneFromTable()"
                                class="deleteButton"><fmt:message key="delete"/></button>
                    </div>
                    <div class="phoneMessage">${requestScope.validation.phoneMessage}</div>
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
                        <button type="button" name="attachmentPopup" onclick="addAttachments()" class="createButton">
                            <fmt:message key="create"/></button>
                        <button type="button" name="deleteAtatchment" onclick="deleteAttachmentFromTable()"
                                class="deleteButton"><fmt:message key="delete"/></button>
                    </div>
                    <div class="attachmentMessage">${requestScope.validation.attachmentMessage}</div>
                </caption>
                <thead>
                <tr>
                    <th class="attachmentCheckbox"></th>
                    <th class="attachmentName"><fmt:message key="title"/></th>
                    <th class="attachmentComment"><fmt:message key="comment"/></th>
                </tr>
                </thead>
                <tbody id="attachmentRows">

                </tbody>
            </table>
        </div>
        <br>
        <input type="submit" onclick="createContact()" value="<fmt:message key="create"/> "
               class="bigCreateButton"/>
    </form>
</div>
<div class="phonePopupText" id="phonePopup">
    <div class="phoneImage">
        <img src="${pageContext.request.contextPath}/assests/images/phone.jpg">
    </div>
    <div class="phoneForm" id="phoneForm">
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
                <option selected disabled><fmt:message key="type"/></option>
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
                <button type="button" id="cancelPhone" onclick="closePhonePopup()" class="btn btn-success"><fmt:message
                        key="exit"/></button>
            </div>

    </div>
</div>
<div class="attachmentPopupText" id="attachmentPopup">
    <div class="fileImage">
        <img src="${pageContext.request.contextPath}/assests/images/file.jpg">
    </div>
    <div class="attachmentForm" id="attachmentForm">
        <form>
            <div class="attachmentMessage" id="attachmentMessage"></div>
            <input type="button" name="attachment" id="attachment" class="chooseButton" onclick="uploadAttachment()"
                   value="<fmt:message key="choose_file"/>"/>
            <div class="attachmentMessage" id="attachTitleMessage"></div>
            <input type="text" id="attachTitle" name="attachTitle" placeholder="<fmt:message key="title"/> "
                   class="form-control"/>
            <div class="attachmentMessage" id="attachCommentMessage"></div>
            <input type="text" id="attachComment" name="attachComment" placeholder="<fmt:message key="comment"/> "
                   class="form-control"/>
            <div class="attachments_buttons">
                <button type="button" id="saveAttachment" onclick="addAttachmentTable()" class="btn btn-primary">
                    <fmt:message key="save"/></button>
                <button type="button" id="cancelAttachment" onclick="closeAttachmentPopup()" class="btn btn-success">
                    <fmt:message
                            key="exit"/></button>
            </div>
        </form>
    </div>
</div>
<div class="photoPopupText" id="photoPopup">
    <div class="photoForm" id="photoForm">
        <div class="photoPathMessage" id="photoPathMessage"></div>
        <input type="button" name="choosePhoto" id="choosePhoto" class="chooseButton" onclick="findPhoto()"
               value="<fmt:message key="find"/>"/>
        <div class="photoPath" id="photoPath"></div>
        <div class="photo_buttons">
            <button type="button" id="savePhoto" onclick="savePhotoFile()" class="btn btn-primary"><fmt:message
                    key="save"/></button>
            <button type="button" id="cancelPhoto" onclick="deletePhoto()" class="btn btn-success">
                <fmt:message
                        key="cancel"/></button>
        </div>
    </div>
</div>
</body>
</html>
