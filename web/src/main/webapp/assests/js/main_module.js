/**
 * Created by Yauhen Malchanau on 14.10.2017.
 */
var mainModule = (function () {
    return {
        deleteContacts: function () {
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
        chooseEmail: function () {
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
        },
        toggle: function (source) {
            checkboxes = document.getElementsByName('id');
            for (var i in checkboxes) {
                checkboxes[i].checked = source.checked;
            }
        }
    }
}());
