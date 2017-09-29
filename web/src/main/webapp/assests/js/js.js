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
    document.getElementById("phoneButtons").style.visibility = "visible";
    document.getElementById("attachmentButtons").style.visibility = "visible";
    document.getElementById("phoneCheckboxTd").style.visibility = "visible";
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

function sendEmail() {

}

function chooseTemplate() {
    var template = document.getElementById("template").value;
    if (template == 'birthday') {
        document.getElementById("message").value = "";
    }
}

function toMainPage() {
    window.location = "http://localhost:8080/controller";
}
function toCreateContact() {
    window.location = "http://localhost:8080/controller?command=redirect&form=createContact";
}
function toSearchPage() {
    window.location = "http://localhost:8080/controller?command=redirect&form=search";
}
function toMessagePage() {
    window.location = "http://localhost:8080/controller?command=redirect&form=message";
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

function createContact() {
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
    var countryCode = document.getElementById('countryCode').value;
    var operatorCode = document.getElementById('operatorCode').value;
    var number = document.getElementById('number').value;
    var comment = document.getElementById('comment').value;
    var attachComment = document.getElementById('attachComment').value;
    if (firstName == "" || firstName == null || firstName.length > 255 || /[0-9~@#$%^&*()_+|?><":}!№;,\s]/.test(firstName.toString())) {
        dataIsValid = false;
        document.getElementById("firstNameMessage").innerHTML = "Only letters";
    }
    if (lastName == "" || lastName == null || lastName.length > 255 || /[0-9~@#$%^&*()_+|?><":}!№;,\s]/.test(lastName.toString())) {
        document.getElementById("secondNameMessage").innerHTML = "Only letters";
        dataIsValid = false;
    }
    if (middleName.length > 255 || /[\d~@#$%^&*()_+|?><":}!№;,\s]/.test(middleName.toString())) {
        dataIsValid = false;
        document.getElementById("middleNameMessage").innerHTML = "Only letters";
    }
    if (/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(birthday.toString())) {
        dataIsValid = false;
        document.getElementById("birthdayMessage").innerHTML = "Date format DD/MM/YYYY";
    }
    if (nationality.length > 255 || /[\d~@#$%^&*()_+|?><":}!№;,\s]/.test(nationality.toString())) {
        document.getElementById("nationalityMessage").innerHTML = "Only letters";
        dataIsValid = false;
    }
    if (!/(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9]\.[^\s]{2,})/.test(webSite)) {
        document.getElementById("websiteMessage").innerHTML = "Invalid website";
        dataIsValid = false;
    }
    if (!/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)) {
        document.getElementById("emailMessage").innerHTML = "Invalid email";
        dataIsValid = false;
    }
    // if (employmentPlace.length > 255 || /[~@#$%^&*()_+|?><":}!№;,\s]/.test(employmentPlace.toString())) {
    //     dataIsValid = false;
    //     document.getElementById("firstNameMessage").innerHTML = "Only letters";
    // }
    if (country.length > 255 || /[\d~@#$%^&*()_+|?><":}!№;,\s]/.test(country.toString())) {
        dataIsValid = false;
        document.getElementById("countryMessage").innerHTML = "Only letters";
    }
    if (city.length > 255 || /[\d~@#$%^&*()_+|?><":}!№;,\s]/.test(city.toString())) {
        dataIsValid = false;
        document.getElementById("cityMessage").innerHTML = "Only letters";
    }
    if (street.length > 255 || /[\d~@#$%^&*()_+|?><":}!№;,\s]/.test(street.toString())) {
        dataIsValid = false;
        document.getElementById("streetMessage").innerHTML = "Only letters";
    }
    if (houseNumber.length > 255 || /[~@#$%^&*()_+|?><":}!№;,\s]/.test(houseNumber.toString())) {
        dataIsValid = false;
        document.getElementById("houseNumberMessage").innerHTML = "Only letters";
    }
    if (flatNumber.length > 255 || /[^\d]/.test(flatNumber.toString())) {
        dataIsValid = false;
        document.getElementById("houseNumberMessage").innerHTML = "Only letters";
    }
    if (postalIndex.length > 255 || /[^\d]/.test(postalIndex.toString())) {
        dataIsValid = false;
        document.getElementById("houseNumberMessage").innerHTML = "Only letters";
    }
    if (countryCode.length > 255 || /\+(\d\s[0-9]{3}|[0-9]{3})/.test(countryCode.toString())) {
        dataIsValid = false;
        document.getElementById("countryCodeMessage").innerHTML = "+d ddd";
    }
    if (operatorCode.length > 255 || /[^\d]/.test(operatorCode.toString())) {
        dataIsValid = false;
        document.getElementById("operatorCodeMessage").innerHTML = "Only digits";
    }
    if (number.length > 255 || /[^\d]/.test(number.toString())) {
        dataIsValid = false;
        document.getElementById("number").innerHTML = "Only digits";
    }
    if (comment.length > 255) {
        dataIsValid = false;
        document.getElementById("commentMessage").innerHTML = "Must be less than 256";
    }
    if (attachComment.length > 255) {
        dataIsValid = false;
        document.getElementById("attachCommentMessage").innerHTML = "Must be less than 256";
    }
    return dataIsValid;
}

function search() {
    var dataIsValid = true;
    var firstName = document.forms["searchForm"]["firstName"].value;
    var lastName = document.forms["searchForm"]["lastName"].value;
    var middleName = document.forms["searchForm"]["middleName"].value;
    var birthdayFrom = document.forms["searchForm"]["birthdayFrom"].value;
    var birthdayTo = document.forms["searchForm"]["birthdayTo"].value;
    var gender = document.forms["searchForm"]["gender"].value;
    var maritalStatus = document.forms["searchForm"]["maritalStatus"].value;
    var country = document.forms["searchForm"]["country"].value;
    var city = document.forms["searchForm"]["city"].value;
    var street = document.forms["searchForm"]["street"].value;
    var houseNumber = document.forms["searchForm"]["houseNumber"].value;
    var flatNumber = document.forms["searchForm"]["flatNumber"].value;
    var postalIndex = document.forms["searchForm"]["postalIndex"].value;
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

function cleanPhonePopup() {
    document.getElementById('countryCode').value = "";
    document.getElementById('operatorCode').value = "";
    document.getElementById('number').value = "";
    document.getElementById('type').value = "";
    document.getElementById('comment').value = "";
}

function addPhoneTable() {
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
    var comentInput = document.createElement("input");
    comentInput.setAttribute("readonly", "readonly");
    comentInput.setAttribute("type", "text");
    comentInput.setAttribute("name", commentName);
    comentInput.setAttribute("style", "border: none; width: 100%");
    comentInput.setAttribute("value", comment);
    commentTd.appendChild(comentInput);
    tr.appendChild(commentTd);
    body.appendChild(tr);
    cleanPhonePopup();
    addPhone();
}
function deletePhoneFromTable() {
    var elms = document.querySelectorAll("[name='phoneId']");
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked) {
            var td = elms[i].parentNode;
            var tr = td.parentNode;
            var body = tr.parentNode;
            body.removeChild(tr);
        }
}

function addAttachments() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("attachmentPopup");
    popup.classList.toggle("show");
}

function addAttachmentTable() {
    var commentName = "attachComment";
    var attachmentName = "attachment";

    var comment = document.getElementById(commentName).value;
    var attachment = document.getElementById(attachmentName).value;


    var body = document.getElementById("attachmentRows");

    var tr = document.createElement("tr");
    var checkTd = document.createElement("td");
    var checkInput = document.createElement("input");
    checkInput.setAttribute("type", "checkbox");
    checkInput.setAttribute("name", "attachmentId");
    checkTd.appendChild(checkInput);
    tr.appendChild(checkTd);

    var attachmentTd = document.createElement("td");
    var attachmentInput = document.createElement("input");
    attachmentInput.setAttribute("readonly", "readonly");
    attachmentInput.setAttribute("type", "file");
    attachmentInput.setAttribute("name", attachmentName);
    attachmentInput.setAttribute("style", "border: none; width: 100%");
    attachmentInput.setAttribute("value", attachment);
    attachmentTd.appendChild(attachmentInput);
    tr.appendChild(attachmentTd);

    var titleTd = document.createElement("td");
    var titleInput = document.createElement("input");
    titleInput.setAttribute("readonly", "readonly");
    titleInput.setAttribute("type", "text");
    titleInput.setAttribute("name", commentName);
    titleInput.setAttribute("style", "border: none; width: 100%");
    titleInput.setAttribute("value", comment);
    titleTd.appendChild(titleInput);

    tr.appendChild(titleTd);


    body.appendChild(tr);
    addAttachments();
}

function deleteAttachmentFromTable() {
    var elms = document.querySelectorAll("[name='attachmentId']");
    for (var i = 0; i < elms.length; i++)
        if (elms[i].checked) {
            var td = elms[i].parentNode;
            var tr = td.parentNode;
            var body = tr.parentNode;
            body.removeChild(tr);
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
        addPhone();
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
    }
    closePhonePopup();
}



