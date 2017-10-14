/**
 * Created by Yauhen Malchanau on 14.10.2017.
 */
var headerModule = (function () {
    return {
        toMainPage: function () {
            window.location = "http://localhost:8080/controller";
        },
        toMessagePage: function () {
            window.location = "http://localhost:8080/controller?command=showMessages";
        },
        toCreateContact: function () {
            window.location = "http://localhost:8080/controller?command=redirect&form=createContact";
        },
        toSearchPage: function () {
            window.location = "http://localhost:8080/controller?command=redirect&form=search";
        },
        showAlertMessage: function () {
            var popup = document.getElementById("alertMessagePopup");
            popup.classList.toggle("show");
            var arrow = document.getElementById("alertMessageArrow");
            arrow.classList.toggle("show");
        },
        toEnglish: function () {
            window.location = "http://localhost:8080/controller?command=language&language=en_US";
        },
        toRussian: function () {
            window.location = "http://localhost:8080/controller?command=language&language=ru_RU";
        },
        toBelorussian: function () {
            window.location = "http://localhost:8080/controller?command=language&language=be_BY";
        }
    }
}());
