/**
 * Created by Yauhen Malchanau on 12.10.2017.
 */
var searchModule = (function() {
    var cleanSearchValidation = function() {
        document.getElementById("firstNameMessage").innerHTML = document.getElementById("secondNameMessage").innerHTML = document.getElementById("middleNameMessage").innerHTML =
            document.getElementById("birthdayToMessage").innerHTML = document.getElementById("birthdayFromMessage").innerHTML = document.getElementById("nationalityMessage").innerHTML =
                document.getElementById("countryMessage").innerHTML = document.getElementById("cityMessage").innerHTML = document.getElementById("streetMessage").innerHTML =
                    document.getElementById("houseNumberMessage").innerHTML = document.getElementById("flatNumberMessage").innerHTML = document.getElementById("postalIndexMessage").innerHTML = "";
        document.getElementById('firstName').style.borderColor = document.getElementById('lastName').style.borderColor = document.getElementById('middleName').style.borderColor =
            document.getElementById('birthdayTo').style.borderColor = document.getElementById('birthdayFrom').style.borderColor = document.getElementById('nationality').style.borderColor =
                document.getElementById('country').style.borderColor = document.getElementById('city').style.borderColor = document.getElementById('street').style.borderColor =
                    document.getElementById('houseNumber').style.borderColor = document.getElementById('flatNumber').style.borderColor =
                        document.getElementById('postalIndex').style.borderColor = "#ccc";
    };
    return {
        search: function() {
            cleanSearchValidation();
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
    }
}());