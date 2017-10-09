/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
function toggle(source) {
    checkboxes = document.getElementsByName('id');
    for (var i in checkboxes) {
        checkboxes[i].checked = source.checked;
    }
}

function edit() {
    document.getElementById("firstName").removeAttribute("readonly");
    document.getElementById("lastName").removeAttribute("readonly");
    document.getElementById("middleName").removeAttribute("readonly");
    document.getElementById("birthday").removeAttribute("readonly");
    document.getElementById("gender").removeAttribute("readonly");
    document.getElementById("nationality").removeAttribute("readonly");
    document.getElementById("maritalStatus").removeAttribute("readonly");
    document.getElementById("employmentPlace").removeAttribute("readonly");
    document.getElementById("contactGroup").removeAttribute("readonly");
    document.getElementById("webSite").removeAttribute("readonly");
    document.getElementById("email").removeAttribute("readonly");
    document.getElementById("country").removeAttribute("readonly");
    document.getElementById("city").removeAttribute("readonly");
    document.getElementById("street").removeAttribute("readonly");
    document.getElementById("houseNumber").removeAttribute("readonly");
    document.getElementById("flatNumber").removeAttribute("readonly");
    document.getElementById("postalIndex").removeAttribute("readonly");
    document.getElementById("image").style.pointerEvents = "auto";
    document.getElementById("phoneButtons").style.visibility = "visible";
    document.getElementById("attachmentButtons").style.visibility = "visible";
    document.getElementById("bigEditButton").style.visibility = "visible";
    var photoImage = document.getElementById("photoImage");
    if (photoImage != null) {
        photoImage.style.cursor = "pointer";
    }
    var defaultImage = document.getElementById("defaultImage");
    if (defaultImage != null) {
        defaultImage.style.cursor = "pointer";
    }
    var phoneCheckBoxes = document.getElementsByName("phoneId");
    for (var j = 0; j < phoneCheckBoxes.length; j++) {
        phoneCheckBoxes[j].style.visibility = "visible";
    }
    var attachmentCheckboxes = document.getElementsByName("attachmentId");
    for (var i = 0; i < attachmentCheckboxes.length; i++) {
        attachmentCheckboxes[i].style.visibility = "visible";
    }
}

function deleteContacts() {
    var dataIsValid = true;
    var dataForDelete = 0;
    var elms = document.querySelectorAll("[name='id']");
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked && elms[i] != 0) {
            dataForDelete++;
        }
    if (dataForDelete > 0) {
        return dataIsValid;
    }
    return !dataIsValid;
}

function deleteMessages() {
    var dataIsValid = true;
    var dataForDelete = 0;
    var elms = document.querySelectorAll("[name='id']");
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked && elms[i] != 0) {
            dataForDelete++;
        }
    if (dataForDelete > 0) {
        return dataIsValid;
    }
    return !dataIsValid;
}

function sendEmail() {
    var dataIsValid = true;
    cleanMessageValidation();
    var addressees = document.getElementById('addressees').value;
    var topic = document.getElementById('topic').value;
    var message = document.getElementById('message').value;
    if (addressees) {
        var emails = addressees.split(" ");
        for (var i = 0; i < emails.length; i++) {
            if (emails[i].length > 255 || !/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(emails[i])) {
                document.getElementById('addresseesMessage').innerHTML = 'Invalid email';
                document.getElementById('addressees').style.borderColor = "#A94442";
                dataIsValid = false;
            }
        }
    } else {
        document.getElementById('addresseesMessage').innerHTML = 'Must be filled';
        document.getElementById('addressees').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (!message) {
        document.getElementById('textMessage').innerHTML = 'Must be filled';
        document.getElementById('message').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    return dataIsValid;
}
function cleanMessageValidation() {
    document.getElementById('addresseesMessage').innerHTML = document.getElementById('textMessage').innerHTML = '';
    document.getElementById('addressees').style.borderColor = document.getElementById('message').style.borderColor = "";
}

function chooseTemplate() {
    var template = document.getElementById("template").value;
    if (template == 'birthday') {
        document.getElementById("message").value = "Уважаемый, $contact.firstName$! Поздравляю Вас с Днем Рождения и хочу пожелать Вам здоровья, успехов, процветания и " +
            "достижения всех намеченных целей, чтобы на Вашем жизненном пути никогда не возникали непроходимые преграды,чтобы всегда и везде сопутствовала Вам удача и " +
            "светило над головой ясное небо!";
    }
    if (template == 'newYear') {
        document.getElementById("message").value = "$contact.firstName$! Искренне поздравляю Вас с Новым годом! Желаю Вам в грядущем году быть в окружении исключительно " +
            "положительных и доброжелательных людей, переживать только приятные эмоции, радоваться каждому прожитому дню, дарить радость и улыбки окружающим. И пусть этот " +
            "Новый год станет для Вас особенным.";
    }
}

function toMainPage() {
    window.location = "http://localhost:8080/controller";
}

function toEmailForm() {
    window.location = "http://localhost:8080/controller?command=redirect&form=sendEmail";
}

function toCreateContact() {
    window.location = "http://localhost:8080/controller?command=redirect&form=createContact";
}

function toSearchPage() {
    window.location = "http://localhost:8080/controller?command=redirect&form=search";
}

function toMessagePage() {
    window.location = "http://localhost:8080/controller?command=showMessages";
}

function toEnglish() {
    window.location = "http://localhost:8080/controller?command=language&language=en_US";
}

function toRussian() {
    window.location = "http://localhost:8080/controller?command=language&language=ru_RU";
}

function toBelorussian() {
    window.location = "http://localhost:8080/controller?command=language&language=be_BY";
}

function findMessage(id) {
    window.location = "http://localhost:8080/controller?command=getMessage&id=" + id;
}

function cleanContactValidation() {
    document.getElementById("firstNameMessage").innerHTML = document.getElementById("secondNameMessage").innerHTML = document.getElementById("middleNameMessage").innerHTML =
        document.getElementById("birthdayMessage").innerHTML = document.getElementById("nationalityMessage").innerHTML = document.getElementById("websiteMessage").innerHTML =
            document.getElementById("emailMessage").innerHTML = document.getElementById("employmentPlaceMessage").innerHTML = document.getElementById("countryMessage").innerHTML =
                document.getElementById("cityMessage").innerHTML = document.getElementById("streetMessage").innerHTML = document.getElementById("houseNumberMessage").innerHTML =
                    document.getElementById("flatNumberMessage").innerHTML = document.getElementById("postalIndexMessage").innerHTML = "";
    document.getElementById('firstName').style.borderColor = document.getElementById('lastName').style.borderColor = document.getElementById('middleName').style.borderColor =
        document.getElementById('birthday').style.borderColor = document.getElementById('nationality').style.borderColor = document.getElementById('webSite').style.borderColor =
            document.getElementById('email').style.borderColor = document.getElementById('employmentPlace').style.borderColor = document.getElementById('country').style.borderColor =
                document.getElementById('city').style.borderColor = document.getElementById('street').style.borderColor = document.getElementById('houseNumber').style.borderColor =
                    document.getElementById('flatNumber').style.borderColor = document.getElementById('postalIndex').style.borderColor = "#ccc";
}

function cleanSerchValidation() {
    document.getElementById("firstNameMessage").innerHTML = document.getElementById("secondNameMessage").innerHTML = document.getElementById("middleNameMessage").innerHTML =
        document.getElementById("birthdayToMessage").innerHTML = document.getElementById("birthdayFromMessage").innerHTML = document.getElementById("nationalityMessage").innerHTML =
            document.getElementById("countryMessage").innerHTML = document.getElementById("cityMessage").innerHTML = document.getElementById("streetMessage").innerHTML =
                document.getElementById("houseNumberMessage").innerHTML = document.getElementById("flatNumberMessage").innerHTML = document.getElementById("postalIndexMessage").innerHTML = "";
    document.getElementById('firstName').style.borderColor = document.getElementById('lastName').style.borderColor = document.getElementById('middleName').style.borderColor =
        document.getElementById('birthdayTo').style.borderColor = document.getElementById('birthdayFrom').style.borderColor = document.getElementById('nationality').style.borderColor =
            document.getElementById('country').style.borderColor = document.getElementById('city').style.borderColor = document.getElementById('street').style.borderColor =
                document.getElementById('houseNumber').style.borderColor = document.getElementById('flatNumber').style.borderColor =
                    document.getElementById('postalIndex').style.borderColor = "#ccc";
}

function contactValidation() {
    cleanContactValidation();
    var dataIsValid = true;
    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var middleName = document.getElementById('middleName').value;
    var birthday = document.getElementById('birthday').value;
    var nationality = document.getElementById('nationality').value;
    var webSite = document.getElementById('webSite').value;
    var email = document.getElementById('email').value;
    var employmentPlace = document.getElementById('employmentPlace').value;
    var contactGroup = document.getElementById('contactGroup').value;
    var country = document.getElementById('country').value;
    var city = document.getElementById('city').value;
    var street = document.getElementById('street').value;
    var houseNumber = document.getElementById('houseNumber').value;
    var flatNumber = document.getElementById('flatNumber').value;
    var postalIndex = document.getElementById('postalIndex').value;

    if (firstName == "" || firstName == null || firstName.length > 255 || (firstName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("firstNameMessage").innerHTML = "Only letters";
        document.getElementById('firstName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (lastName == "" || lastName == null || lastName.length > 255 || (lastName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("secondNameMessage").innerHTML = "Only letters";
        document.getElementById('lastName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (middleName.length > 255 || (middleName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("middleNameMessage").innerHTML = "Only letters";
        document.getElementById('middleName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    var today = new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
    if (/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(birthday.toString()) || new Date(birthday).getTime() > new Date(today).getTime()) {
        document.getElementById("birthdayMessage").innerHTML = "Date format DD/MM/YYYY";
        document.getElementById('birthday').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (nationality.length > 255 || (nationality.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("nationalityMessage").innerHTML = "Only letters";
        document.getElementById('nationality').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (employmentPlace.length > 255 || /[~@#$%^&/.*()_+|?><":}!№;,\s]/.test(employmentPlace.toString())) {
        document.getElementById("employmentPlaceMessage").innerHTML = "Only letters";
        document.getElementById('employmentPlace').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (webSite) {
        if (webSite.length > 255 || !/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z‌​]{2,6}\b([-a-zA-Z0-9‌​@:%_\+.~#?&=]*)/.test(webSite)) {
            document.getElementById("websiteMessage").innerHTML = "Invalid website";
            document.getElementById('webSite').style.borderColor = "#A94442";
            dataIsValid = false;
        }
    }
    if (email) {
        if (email.length > 255 || !/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)) {
            document.getElementById("emailMessage").innerHTML = "Invalid email";
            document.getElementById('email').style.borderColor = "#A94442";
            dataIsValid = false;
        }
    }
    if (country.length > 255 || (country.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("countryMessage").innerHTML = "Only letters";
        document.getElementById('country').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (city.length > 255 || (city.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("cityMessage").innerHTML = "Only letters";
        document.getElementById('city').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (street.length > 255 || (street.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("streetMessage").innerHTML = "Only letters";
        document.getElementById('street').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (houseNumber.length > 255 || (houseNumber.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("houseNumberMessage").innerHTML = "Only letters";
        document.getElementById('houseNumber').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (flatNumber.length > 255 || (flatNumber.toString()).search(/[^\d]/) != -1) {
        document.getElementById("flatNumberMessage").innerHTML = "Only numbers";
        document.getElementById('flatNumber').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (postalIndex.length > 255 || (postalIndex.toString()).search(/[^\d]/) != -1) {
        document.getElementById("postalIndexMessage").innerHTML = "Only numbers";
        document.getElementById('postalIndex').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    return dataIsValid;
}

function search() {
    cleanSerchValidation();
    var dataIsValid = true;
    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var middleName = document.getElementById('middleName').value;
    var birthdayFrom = document.getElementById('birthdayFrom').value;
    var birthdayTo = document.getElementById('birthdayTo').value;
    var gender = document.getElementById('gender').value;
    var nationality = document.getElementById('nationality').value;
    var maritalStatus = document.getElementById('maritalStatus').value;
    var country = document.getElementById('country').value;
    var city = document.getElementById('city').value;
    var street = document.getElementById('street').value;
    var houseNumber = document.getElementById('houseNumber').value;
    var flatNumber = document.getElementById('flatNumber').value;
    var postalIndex = document.getElementById('postalIndex').value;
    if (firstName.length > 255 || (firstName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("firstNameMessage").innerHTML = "Only letters";
        document.getElementById('firstName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (lastName.length > 255 || (lastName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("secondNameMessage").innerHTML = "Only letters";
        document.getElementById('lastName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (middleName.length > 255 || (middleName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("middleNameMessage").innerHTML = "Only letters";
        document.getElementById('middleName').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    var today = new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
    if (/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(birthdayFrom.toString()) || new Date(birthdayFrom).getTime() > new Date(today).getTime() ||
        new Date(birthdayFrom).getTime() > new Date(birthdayTo).getTime()) {
        document.getElementById("birthdayFromMessage").innerHTML = "Date format DD/MM/YYYY";
        document.getElementById('birthdayFrom').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(birthdayTo.toString()) || new Date(birthdayTo).getTime() > new Date(today).getTime() ||
        new Date(birthdayFrom).getTime() > new Date(birthdayTo).getTime()) {
        document.getElementById("birthdayToMessage").innerHTML = "Date format DD/MM/YYYY";
        document.getElementById('birthdayTo').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (nationality.length > 255 || (nationality.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("nationalityMessage").innerHTML = "Only letters";
        document.getElementById('nationality').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (country.length > 255 || (country.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("countryMessage").innerHTML = "Only letters";
        document.getElementById('country').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (city.length > 255 || (city.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("cityMessage").innerHTML = "Only letters";
        document.getElementById('city').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (street.length > 255 || (street.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("streetMessage").innerHTML = "Only letters";
        document.getElementById('street').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (houseNumber.length > 255 || (houseNumber.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
        document.getElementById("houseNumberMessage").innerHTML = "Only letters";
        document.getElementById('houseNumber').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (flatNumber.length > 255 || (flatNumber.toString()).search(/[^\d]/) != -1) {
        document.getElementById("flatNumberMessage").innerHTML = "Only numbers";
        document.getElementById('flatNumber').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (postalIndex.length > 255 || (postalIndex.toString()).search(/[^\d]/) != -1) {
        document.getElementById("postalIndexMessage").innerHTML = "Only numbers";
        document.getElementById('postalIndex').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    return dataIsValid;
}

function chooseEmail() {
    var dataIsValid = true;
    var dataForDelete = 0;
    var elms = document.getElementsByName("id");
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked && elms[i] != 0) {
            var email = elms[i].getAttribute("email");
            document.getElementById("useEmail_".concat(email)).value = email;
            dataForDelete++;
        }
    if (dataForDelete > 0) {
        return dataIsValid;
    }
    return !dataIsValid;
}

function addPhone() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("phonePopup");
    popup.classList.toggle("show");
}

function closePhonePopup() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("phonePopup");
    popup.classList.toggle("show");
    cleanPhonePopup();
}

function closeAttachmentPopup() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("attachmentPopup");
    popup.classList.toggle("show");
    document.getElementById('attachment').setAttribute('type', 'button');
    cleanAttachmentPopup();
}

function cleanPhonePopup() {
    document.getElementById('countryCode').value = document.getElementById('operatorCode').value = document.getElementById('number').value =
        document.getElementById('type').value = document.getElementById('comment').value = "";
    cleanPhoneValidation();
}

function cleanPhoneValidation() {
    document.getElementById('countryCodeMessage').innerHTML = document.getElementById('operatorCodeMessage').innerHTML = document.getElementById('numberMessage').innerHTML =
        document.getElementById('typeMessage').innerHTML = document.getElementById('commentMessage').innerHTML = "";
    document.getElementById('countryCode').style.borderColor = document.getElementById('operatorCode').style.borderColor = document.getElementById('number').style.borderColor =
        document.getElementById('type').style.borderColor = document.getElementById('comment').style.borderColor = "";
}

function cleanAttachmentValidation() {
    document.getElementById('attachmentMessage').innerHTML = document.getElementById('attachTitleMessage').innerHTML = document.getElementById('attachCommentMessage').innerHTML =
        "";
    document.getElementById('attachTitle').style.borderColor = document.getElementById('attachComment').style.borderColor = "";
}

function cleanAttachmentPopup() {
    document.getElementById('attachTitle').value = document.getElementById('attachComment').value = "";
    cleanAttachmentValidation()
}

function validatePhone(countryCode, operatorCode, number, type, comment) {
    var dataIsValid = true;
    if (!countryCode || countryCode.length > 255 || countryCode.toString().search(/[^\d]/) != -1) {
        document.getElementById('countryCodeMessage').innerHTML = "Only digits";
        document.getElementById('countryCode').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (!operatorCode || operatorCode.length > 255 || operatorCode.toString().search(/[^\d]/) != -1) {
        document.getElementById('operatorCodeMessage').innerHTML = "Only digits";
        document.getElementById('operatorCode').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (!number || number.length > 255 || number.toString().search(/[^\d]/) != -1) {
        document.getElementById('numberMessage').innerHTML = "Only digits";
        document.getElementById('number').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (!type || (type != "Рабочий" && type != "Домашний" && type != "Сотовый")) {
        document.getElementById('typeMessage').innerHTML = "Choose type";
        document.getElementById('type').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (comment.length > 255) {
        document.getElementById('commentMessage').innerHTML = "Must be less than 256";
        document.getElementById('comment').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    return dataIsValid;
}

function addPhoneTable() {
    cleanPhoneValidation();
    var countryCodeName = "countryCode";
    var operatorCodeName = "operatorCode";
    var numberName = "number";
    var typeName = "type";
    var commentName = "comment";
    var countryCode = document.getElementById(countryCodeName).value;
    var operatorCode = document.getElementById(operatorCodeName).value;
    var number = document.getElementById(numberName).value;
    var type = document.getElementById(typeName).value;
    var comment = document.getElementById(commentName).value;
    var body = document.getElementById("phoneRows");
    if (validatePhone(countryCode, operatorCode, number, type, comment) == false) {
        return;
    }
    if (document.getElementById('clickEdit') && document.getElementById('clickEdit').value == 'true') {
        editPhoneFields();
        return;
    }
    var tr = document.createElement("tr");
    var checkTd = document.createElement("td");
    checkTd.setAttribute("style", "width: 3%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var checkInput = document.createElement("input");
    checkInput.setAttribute("type", "checkbox");
    checkInput.setAttribute("name", "phoneId");
    checkTd.appendChild(checkInput);
    tr.appendChild(checkTd);

    var countryCodeTd = document.createElement("td");
    countryCodeTd.setAttribute("style", "width: 11%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var countryCodeInput = document.createElement("input");
    countryCodeInput.setAttribute("readonly", "readonly");
    countryCodeInput.setAttribute("type", "text");
    countryCodeInput.setAttribute("name", countryCodeName);
    countryCodeInput.setAttribute("style", "border: none; width: 100%");
    countryCodeInput.setAttribute("value", countryCode);
    countryCodeTd.appendChild(countryCodeInput);
    tr.appendChild(countryCodeTd);

    var operatorCodeTd = document.createElement("td");
    operatorCodeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var operatorCodeInput = document.createElement("input");
    operatorCodeInput.setAttribute("readonly", "readonly");
    operatorCodeInput.setAttribute("type", "text");
    operatorCodeInput.setAttribute("name", operatorCodeName);
    operatorCodeInput.setAttribute("style", "border: none; width: 100%");
    operatorCodeInput.setAttribute("value", operatorCode);
    operatorCodeTd.appendChild(operatorCodeInput);
    tr.appendChild(operatorCodeTd);

    var phoneNumberTd = document.createElement("td");
    phoneNumberTd.setAttribute("style", "width: 25%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var phoneNumberInput = document.createElement("input");
    phoneNumberInput.setAttribute("readonly", "readonly");
    phoneNumberInput.setAttribute("type", "text");
    phoneNumberInput.setAttribute("name", numberName);
    phoneNumberInput.setAttribute("style", "border: none; width: 100%");
    phoneNumberInput.setAttribute("value", number);
    phoneNumberTd.appendChild(phoneNumberInput);
    tr.appendChild(phoneNumberTd);

    var typeTd = document.createElement("td");
    typeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var typeInput = document.createElement("input");
    typeInput.setAttribute("readonly", "readonly");
    typeInput.setAttribute("type", "text");
    typeInput.setAttribute("name", typeName);
    typeInput.setAttribute("style", "border: none; width: 100%");
    typeInput.setAttribute("value", type);
    typeTd.appendChild(typeInput);
    tr.appendChild(typeTd);

    var commentTd = document.createElement("td");
    commentTd.setAttribute("style", "width: 36%; border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var commentInput = document.createElement("input");
    commentInput.setAttribute("readonly", "readonly");
    commentInput.setAttribute("type", "text");
    commentInput.setAttribute("name", commentName);
    commentInput.setAttribute("style", "border: none; width: 100%");
    commentInput.setAttribute("value", comment);
    commentTd.appendChild(commentInput);
    tr.appendChild(commentTd);
    body.appendChild(tr);
    cleanPhonePopup();
    addPhone();
}

function addAttachments() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("attachmentPopup");
    popup.classList.toggle("show");
}

function addPhoto() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("photoPopup");
    popup.classList.toggle("show");
}

function showAlertMessage() {
    var popup = document.getElementById("alertMessagePopup");
    popup.classList.toggle("show");
    var arrow = document.getElementById("alertMessageArrow");
    arrow.classList.toggle("show");
}

function savePhotoFile() {
    if (document.getElementById('photoPath').textContent != "") {
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("photoPopup");
        popup.classList.toggle("show");
        document.getElementById('fotoMessage').innerHTML = 'Фото сохранено'
    }
}

function deletePhoto() {
    if (document.getElementById('photoPath').textContent == "") {
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("photoPopup");
        popup.classList.toggle("show");
        document.getElementById('fotoMessage').innerHTML = ''
    } else if (document.getElementById('photoPath').textContent != "") {
        var photo = document.getElementById('photoFile');
        photo.parentNode.removeChild(photo);
        document.getElementById('photoPath').innerHTML = "";
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("photoPopup");
        popup.classList.toggle("show");
        document.getElementById('fotoMessage').innerHTML = 'Фото удалено'
    }
}

var counter = 0;

function createInputTypeFileForAttachment() {
    var body = document.getElementById("attachmentRows");
    var fileInput = document.createElement("input");
    fileInput.setAttribute("type", "file");
    fileInput.setAttribute("style", "visibility: hidden; position: fixed;");
    fileInput.setAttribute("name", "attachmentFile");
    fileInput.setAttribute("id", "attachmentFile");
    fileInput.setAttribute("number", counter.toString());
    body.appendChild(fileInput)
}

function createInputTypeFileForPhoto() {
    if (document.getElementById('photoFile') == null) {
        var photoDiv = document.getElementById("image");
        var fileInput = document.createElement("input");
        fileInput.setAttribute("type", "file");
        fileInput.setAttribute("style", "visibility: hidden; position: fixed;");
        fileInput.setAttribute("name", "photoFile");
        fileInput.setAttribute("id", "photoFile");
        fileInput.setAttribute("onchange", "putPath()");
        photoDiv.appendChild(fileInput)
    }
}

function putPath() {
    var path = document.getElementById('photoFile').value;
    document.getElementById('photoPath').innerHTML = path;
}

function findPhoto() {
    createInputTypeFileForPhoto();
    document.getElementById('photoFile').click();
}


function uploadAttachment() {
    createInputTypeFileForAttachment();
    var attachments = document.getElementsByName('attachmentFile');
    for (var i = 0; i < attachments.length; i++) {
        if (attachments[i].getAttribute('number') == counter.toString()) {
            attachments[i].click();
        }
    }
}

function validateAttachment(title, comment) {
    var dataIsValid = true;
    if (document.getElementById('attachment').getAttribute('type') == 'button') {
        var attachments = document.getElementsByName('attachmentFile');
        var counterInput = 0;
        for (var i = 0; i < attachments.length; i++) {
            if (attachments[i].getAttribute('number') == counter.toString()) {
                counterInput++;
            }
        }
        if (counterInput != 1) {
            document.getElementById('attachmentMessage').innerHTML = "Add attachment";
            dataIsValid = false;
        }
    }
    if (!title || title.length > 255) {
        document.getElementById('attachTitleMessage').innerHTML = "Not valid";
        document.getElementById('attachTitle').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    if (comment.length > 255) {
        document.getElementById('attachCommentMessage').innerHTML = "Must be less than 256";
        document.getElementById('attachComment').style.borderColor = "#A94442";
        dataIsValid = false;
    }
    return dataIsValid;
}

function addAttachmentTable() {
    cleanAttachmentValidation();
    var commentName = "attachComment";
    var attachmentName = "attachTitle";
    var comment = document.getElementById(commentName).value;
    var title = document.getElementById(attachmentName).value;

    if (!validateAttachment(title, comment)) {
        return;
    }
    var body = document.getElementById("attachmentRows");
    if (document.getElementById('attachment').getAttribute('type') == 'hidden') {
        editAttachmentFields();
        return;
    }
    var tr = document.createElement("tr");
    tr.setAttribute("number", counter.toString());
    counter++;
    var checkTd = document.createElement("td");
    checkTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var checkInput = document.createElement("input");
    checkInput.setAttribute("type", "checkbox");
    checkInput.setAttribute("name", "attachmentId");
    checkTd.appendChild(checkInput);
    tr.appendChild(checkTd);

    var attachmentTd = document.createElement("td");
    attachmentTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var attachmentInput = document.createElement("input");
    attachmentInput.setAttribute("readonly", "readonly");
    attachmentInput.setAttribute("type", "text");
    attachmentInput.setAttribute("name", attachmentName);
    attachmentInput.setAttribute("style", "border: none; width: 100%");
    attachmentInput.setAttribute("value", title);
    attachmentTd.appendChild(attachmentInput);
    tr.appendChild(attachmentTd);
    if (document.getElementById('page') != null && document.getElementById('page').value == 'edit') {
        var dataTd = document.createElement("td");
        dataTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
        tr.appendChild(dataTd);
    }
    var titleTd = document.createElement("td");
    titleTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding: 1% 0 1% 0");
    var titleInput = document.createElement("input");
    titleInput.setAttribute("readonly", "readonly");
    titleInput.setAttribute("type", "text");
    titleInput.setAttribute("name", commentName);
    titleInput.setAttribute("style", "border: none; width: 100%");
    titleInput.setAttribute("value", comment);
    titleTd.appendChild(titleInput);

    tr.appendChild(titleTd);


    body.appendChild(tr);
    cleanAttachmentPopup();
    document.getElementById('attachment').setAttribute('type', 'button');
    addAttachments();
}

function deletePhoneFromTable() {
    var elms = document.getElementsByName('phoneId');
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked) {
            var id = elms[i].value;
            var td = elms[i].parentNode;
            var tr = td.parentNode;
            var body = tr.parentNode;
            if (id != null && id > 0) {
                var input = document.createElement('input');
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "phoneForDelete");
                input.setAttribute('value', id);
                body.appendChild(input);
            }
            body.removeChild(tr);
            i--;
        }
}

function deleteAttachmentFromTable() {
    var elms = document.getElementsByName('attachmentId');
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked) {
            var id = elms[i].value;
            var td = elms[i].parentNode;
            var tr = td.parentNode;
            var number = tr.getAttribute('number');
            var body = tr.parentNode;
            if (id && id > 0) {
                var input = document.createElement('input');
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "attachmentForDelete");
                input.setAttribute('value', id);
                body.appendChild(input);
            }
            var files = document.getElementsByName('attachmentFile');
            for (var j = 0; j < files.length; j++) {
                if (files[j].getAttribute('number') == number) {
                    files[j].parentNode.removeChild(files[j]);
                }
            }
            body.removeChild(tr);
            i--;
        }
}

function editPhone() {
    var counter = 0;
    var id;
    var phoneIds = document.getElementsByName('phoneId');
    for (var i = 0; i < phoneIds.length; i++) {
        if (phoneIds[i].checked) {
            counter++;
            id = phoneIds[i].value;
        }
    }
    if (counter == 1) {
        document.getElementById('countryCode').value = document.getElementById('countryCodeId_'.concat(id)).value;
        document.getElementById('operatorCode').value = document.getElementById('operatorCodeId_'.concat(id)).value;
        document.getElementById('number').value = document.getElementById('numberId_'.concat(id)).value;
        document.getElementById('type').value = document.getElementById('typeId_'.concat(id)).value;
        document.getElementById('comment').value = document.getElementById('commentId_'.concat(id)).value;
        document.getElementById('clickEdit').setAttribute('value', 'true');
        addPhone();
    }
}

function editAttachment() {
    var counter = 0;
    var id;
    var attachmentIds = document.getElementsByName('attachmentId');
    for (var i = 0; i < attachmentIds.length; i++) {
        if (attachmentIds[i].checked) {
            counter++;
            id = attachmentIds[i].value;
        }
    }
    if (counter == 1) {
        document.getElementById('attachTitle').value = document.getElementById('attachmentFileId_'.concat(id)).value;
        document.getElementById('attachComment').value = document.getElementById('attachCommentId_'.concat(id)).value;
        document.getElementById('attachment').setAttribute('type', 'hidden');
        addAttachments();
    }
}

function editPhoneFields() {
    var counter = 0;
    var id;
    var phoneIds = document.getElementsByName('phoneId');
    for (var i = 0; i < phoneIds.length; i++) {
        if (phoneIds[i].checked) {
            counter++;
            id = phoneIds[i].value;
        }
    }
    if (counter == 1) {
        document.getElementById('countryCodeId_'.concat(id)).value = document.getElementById('countryCode').value;
        document.getElementById('operatorCodeId_'.concat(id)).value = document.getElementById('operatorCode').value;
        document.getElementById('numberId_'.concat(id)).value = document.getElementById('number').value;
        document.getElementById('typeId_'.concat(id)).value = document.getElementById('type').value;
        document.getElementById('commentId_'.concat(id)).value = document.getElementById('comment').value;
        document.getElementById('clickEdit').setAttribute('value', 'false');
    }
    closePhonePopup();
}

function editAttachmentFields() {
    var counter = 0;
    var id;
    var attachmentIds = document.getElementsByName('attachmentId');
    for (var i = 0; i < attachmentIds.length; i++) {
        if (attachmentIds[i].checked) {
            counter++;
            id = attachmentIds[i].value;
        }
    }
    if (counter == 1) {
        document.getElementById('attachCommentId_'.concat(id)).value = document.getElementById('attachComment').value;
        document.getElementById('attachmentFileId_'.concat(id)).value = document.getElementById('attachTitle').value;
        document.getElementById('hrefId_'.concat(id)).innerHTML = document.getElementById('attachTitle').value;
    }
    closeAttachmentPopup();
}



