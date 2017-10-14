/**
 * Created by Yauhen Malchanau on 14.10.2017.
 */
var sendEmailModule = (function () {
    var cleanMessageValidation = function () {
        document.getElementById('addresseesMessage').innerHTML = document.getElementById('textMessage').innerHTML = '';
        document.getElementById('addressees').style.borderColor = document.getElementById('message').style.borderColor = "";
    };
    return {
        sendEmail: function () {
            var dataIsValid = true;
            cleanMessageValidation();
            var addressees = document.getElementById('addressees').value;
            var topic = document.getElementById('topic').value;
            var message = document.getElementById('message').value;
            if (addressees) {
                var emails = addressees.split(" ");
                for (var i = 0; i < emails.length; i++) {
                    if (!/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(emails[i])) {
                        document.getElementById('addresseesMessage').innerHTML = messages['validation.email'];
                        document.getElementById('addressees').style.borderColor = "#A94442";
                        dataIsValid = false;
                    }
                }
            } else {
                document.getElementById('addresseesMessage').innerHTML = messages['validation.fill'];
                document.getElementById('addressees').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if (topic && topic.length > 255) {
                document.getElementById('topicMessage').innerHTML = messages['validation.size.255'];
                document.getElementById('topic').style.borderColor = "#A94442";
            }
            if (!message) {
                document.getElementById('textMessage').innerHTML = messages['validation.fill'];
                document.getElementById('message').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            return dataIsValid;
        },
        toEmailForm: function () {
            window.location = "http://localhost:8080/controller?command=redirect&form=sendEmail";
        },
        chooseTemplate: function () {
            var template = document.getElementById("template").value;
            if (template == 'birthday') {
                document.getElementById("message").value = messages['template.birthday'];
            }
            if (template == 'newYear') {
                document.getElementById("message").value = messages['template.new.year'];
            }
        }
    }
}());
