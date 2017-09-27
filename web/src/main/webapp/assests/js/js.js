/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
function toggle(source) {
    checkboxes = document.getElementsByName('id');
    for (var i in checkboxes) {
        checkboxes[i].checked = source.checked;
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

function sendEmail() {

}

function chooseTemplate() {
    var template = document.getElementById("template").value;
    if (template == 'birthday') {
        document.getElementById("message").value = "Hello again, $name$ !";
    }
}

function goToCreateForm() {
    window.location = "http://localhost:8080/controller?command=redirect&form=createContact";
}

function createContact() {
    var dataIsValid = true;
    var firstName = document.getElementById('firstName');
    var lastName = document.getElementById('lastName');
    var middleName = document.getElementById('middleName');
    var birthday = document.getElementById('birthday');
    var gender = document.getElementById('gender');
    var nationality = document.getElementById('nationality');
    var maritalStatus = document.getElementById('maritalStatus');
    var webSite = document.getElementById('webSite');
    var email = document.getElementById('email');
    var employmentPlace = document.getElementById('employmentPlace');
    var contactGroup = document.getElementById('contactGroup');
    var country = document.getElementById('country');
    var city = document.getElementById('city');
    var street = document.getElementById('street');
    var houseNumber = document.getElementById('houseNumber');
    var flatNumber = document.getElementById('flatNumber');
    var postalIndex = document.getElementById('postalIndex');
    if (firstName == "" || firstName == null || firstName.length > 255 || firstName == /[^0-9~@#$%^&*()_+|?><":}!№;,\s]/) {
        dataIsValid = false;
        document.getElementById("firstNameMessage").innerHTML = "Not valid";
    }
    if (lastName == "" || lastName == null) {
        dataIsValid = false;
    }
    if (middleName == "" || middleName == null || firstName.length > 255 || middleName == /[^0-9~@#$%^&*()_+|?><":}!№;,\s]/) {
        dataIsValid = false;
        document.getElementById("middleNameMessage").innerHTML = "Not valid";
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

function addPhones() {
    var body = document.getElementById('contact_form');
    body.classList.toggle("roll");
    var popup = document.getElementById("phonePopup");
    popup.classList.toggle("show");
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
    tr.setAttribute("style", "line-height: 5%");
    var checkTd = document.createElement("td");
    checkTd.setAttribute("style", "width: 3%; border-bottom: 1px solid #ddd;");
    var checkInput = document.createElement("input");
    checkInput.setAttribute("type", "checkbox");
    checkInput.setAttribute("name", "phoneId");
    checkTd.appendChild(checkInput);
    tr.appendChild(checkTd);

    var countryCodeTd = document.createElement("td");
    countryCodeTd.setAttribute("style", "width: 11%; border-bottom: 1px solid #ddd");
    var countryCodeInput = document.createElement("input");
    countryCodeInput.setAttribute("readonly", "readonly");
    countryCodeInput.setAttribute("type", "text");
    countryCodeInput.setAttribute("name", countryCodeName);
    countryCodeInput.setAttribute("style", "border: none; width: 100%");
    countryCodeInput.setAttribute("value", countryCode);
    countryCodeTd.appendChild(countryCodeInput);
    tr.appendChild(countryCodeTd);

    var operatorCodeTd = document.createElement("td");
    operatorCodeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd");
    var operatorCodeInput = document.createElement("input");
    operatorCodeInput.setAttribute("readonly", "readonly");
    operatorCodeInput.setAttribute("type", "text");
    operatorCodeInput.setAttribute("name", operatorCodeName);
    operatorCodeInput.setAttribute("style", "border: none; width: 100%");
    operatorCodeInput.setAttribute("value", operatorCode);
    operatorCodeTd.appendChild(operatorCodeInput);
    tr.appendChild(operatorCodeTd);

    var phoneNumberTd = document.createElement("td");
    phoneNumberTd.setAttribute("style", "width: 25%; border-bottom: 1px solid #ddd");
    var phoneNumberInput = document.createElement("input");
    phoneNumberInput.setAttribute("readonly", "readonly");
    phoneNumberInput.setAttribute("type", "text");
    phoneNumberInput.setAttribute("name", numberName);
    phoneNumberInput.setAttribute("style", "border: none; width: 100%");
    phoneNumberInput.setAttribute("value", number);
    phoneNumberTd.appendChild(phoneNumberInput);
    tr.appendChild(phoneNumberTd);

    var typeTd = document.createElement("td");
    typeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd");
    var typeInput = document.createElement("input");
    typeInput.setAttribute("readonly", "readonly");
    typeInput.setAttribute("type", "text");
    typeInput.setAttribute("name", typeName);
    typeInput.setAttribute("style", "border: none; width: 100%");
    typeInput.setAttribute("value", type);
    typeTd.appendChild(typeInput);
    tr.appendChild(typeTd);

    var commentTd = document.createElement("td");
    commentTd.setAttribute("style", "width: 36%; border-bottom: 1px solid #ddd");
    var comentInput = document.createElement("input");
    comentInput.setAttribute("readonly", "readonly");
    comentInput.setAttribute("type", "text");
    comentInput.setAttribute("name", commentName);
    comentInput.setAttribute("style", "border: none; width: 100%");
    comentInput.setAttribute("value", comment);
    commentTd.appendChild(comentInput);
    tr.appendChild(commentTd);

    body.appendChild(tr);
    addPhones();
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



