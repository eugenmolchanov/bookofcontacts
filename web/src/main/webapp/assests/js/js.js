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
    var firstName = document.forms["createForm"]["firstName"].value;
    var lastName = document.forms["createForm"]["lastName"].value;
    var middleName = document.forms["createForm"]["middleName"].value;
    var birthday = document.forms["createForm"]["birthday"].value;
    var gender = document.forms["createForm"]["gender"].value;
    var nationality = document.forms["createForm"]["nationality"].value;
    var maritalStatus = document.forms["createForm"]["maritalStatus"].value;
    var webSite = document.forms["createForm"]["webSite"].value;
    var email = document.forms["createForm"]["email"].value;
    var employmentPlace = document.forms["createForm"]["employmentPlace"].value;
    var country = document.forms["createForm"]["country"].value;
    var city = document.forms["createForm"]["city"].value;
    var street = document.forms["createForm"]["street"].value;
    var houseNumber = document.forms["createForm"]["houseNumber"].value;
    var flatNumber = document.forms["createForm"]["flatNumber"].value;
    var postalIndex = document.forms["createForm"]["postalIndex"].value;
    if (firstName == "" || firstName == null) {
        dataIsValid = false;
        document.getElementById("firstName").innerHTML = "Not valid";
    }
    if (lastName == "" || lastName == null) {
        dataIsValid = false;
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
    var params = [countryCode, operatorCode, number, type, comment];
    var names = [countryCodeName, operatorCodeName, numberName, typeName, commentName];
    var body = document.getElementById("phoneRows");
    for (var j = 1; j <= params.length; j++) {
        var tr = document.createElement("tr");
        var checkTd = document.createElement("td");
        var checkInput = document.createElement("input");
        checkInput.setAttribute("type", "checkbox");
        checkInput.setAttribute("name", "phoneId");
        checkTd.appendChild(checkInput);
        tr.appendChild(checkTd);
        for (var i = 0; i < params.length; i++) {
            var td = document.createElement("td");
            var input = document.createElement("input");
            input.setAttribute("readonly", "readonly");
            input.setAttribute("type", "text");
            input.setAttribute("name", names[i]);
            input.setAttribute("style", "border: none");
            input.setAttribute("value", params[i]);
            td.appendChild(input);
            tr.appendChild(td);
        }
    }
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
    var popup = document.getElementById("attachmentPopup");
    popup.classList.toggle("show");
}

function addAttachmentTable() {
    var attachmentTitleName = "attachmentTitle";
    var attachmentName = "attachment";

    var attachmentTitle = document.getElementById(attachmentTitleName).value;
    var attachment = document.getElementById(attachmentName).value;


    var body = document.getElementById("attachmentRows");

    var tr = document.createElement("tr");
    var checkTd = document.createElement("td");
    var checkInput = document.createElement("input");
    checkInput.setAttribute("type", "checkbox");
    checkInput.setAttribute("name", "attachmentId");
    checkTd.appendChild(checkInput);
    tr.appendChild(checkTd);

    var titleTd = document.createElement("td");
    var titleInput = document.createElement("input");
    titleInput.setAttribute("readonly", "readonly");
    titleInput.setAttribute("type", "text");
    titleInput.setAttribute("name", attachmentTitleName);
    titleInput.setAttribute("style", "border: none");
    titleInput.setAttribute("value", attachmentTitle);
    titleTd.appendChild(titleInput);
    tr.appendChild(titleTd);

    var attachmentTd = document.createElement("td");
    var attachmentInput = document.createElement("input");
    attachmentInput.setAttribute("readonly", "readonly");
    attachmentInput.setAttribute("type", "file");
    attachmentInput.setAttribute("name", attachmentName);
    attachmentInput.setAttribute("style", "border: none");
    attachmentInput.setAttribute("value", attachment);
    attachmentTd.appendChild(attachmentInput);
    tr.appendChild(attachmentTd);


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



