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
            if (!/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(emails[i])) {
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



function showAlertMessage() {
    var popup = document.getElementById("alertMessagePopup");
    popup.classList.toggle("show");
    var arrow = document.getElementById("alertMessageArrow");
    arrow.classList.toggle("show");
}











