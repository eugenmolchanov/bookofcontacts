/**
 * Created by Yauhen Malchanau on 14.10.2017.
 */
var messageModule = (function () {
    return {
        deleteMessages: function () {
            var dataIsValid = true;
            var dataForDelete = 0;
            var elms = document.querySelectorAll("[name='id']");
            for (var i = 0; i < elms.length; i++)
                if (elms[i].checked && elms[i] != 0) {
                    dataForDelete++;
                }
            if (dataForDelete > 0) {
                var agree = confirm(messages['agreement']);
                if (agree) {
                    return dataIsValid;
                }
            }
            return !dataIsValid;
        },
        toggle: function (source) {
            checkboxes = document.getElementsByName('id');
            for (var i in checkboxes) {
                checkboxes[i].checked = source.checked;
            }
        },
        toEmailForm: function () {
            window.location = "http://localhost:8080/controller?command=redirect&form=sendEmail";
        },
        findMessage: function (id) {
            window.location = "http://localhost:8080/controller?command=getMessage&id=" + id;
        },
        toDeleteMessagePage: function () {
            window.location = "http://localhost:8080/controller?command=showDeletedMessages";
        },
        restoreMessages: function () {
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
        },
    }
}());
