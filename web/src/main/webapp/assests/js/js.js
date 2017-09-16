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
