/**
 * Created by Yauhen Malchanau on 13.10.2017.
 */
var counter = 0;
var contactModule = (function () {
    var cleanContactValidation = function () {
        document.getElementById("firstNameMessage").innerHTML = document.getElementById("secondNameMessage").innerHTML = document.getElementById("middleNameMessage").innerHTML =
            document.getElementById("birthdayMessage").innerHTML = document.getElementById("nationalityMessage").innerHTML = document.getElementById("websiteMessage").innerHTML =
                document.getElementById("emailMessage").innerHTML = document.getElementById("employmentPlaceMessage").innerHTML = document.getElementById("countryMessage").innerHTML =
                    document.getElementById("cityMessage").innerHTML = document.getElementById("streetMessage").innerHTML = document.getElementById("houseNumberMessage").innerHTML =
                        document.getElementById("flatNumberMessage").innerHTML = document.getElementById("postalIndexMessage").innerHTML = "";
        document.getElementById('firstName').style.borderColor = document.getElementById('lastName').style.borderColor = document.getElementById('middleName').style.borderColor =
            document.getElementById('birthday').style.borderColor = document.getElementById('nationality').style.borderColor = document.getElementById('webSite').style.borderColor =
                document.getElementById('email').style.borderColor = document.getElementById('employmentPlace').style.borderColor = document.getElementById('country').style.borderColor =
                    document.getElementById('city').style.borderColor = document.getElementById('street').style.borderColor = document.getElementById('houseNumber').style.borderColor =
                        document.getElementById('flatNumber').style.borderColor = document.getElementById('postalIndex').style.borderColor = "#ccc";
    };
    var addPhone = function () {
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("phonePopup");
        popup.classList.toggle("show");
    };
    var addAttachments = function () {
        counter++;
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("attachmentPopup");
        popup.classList.toggle("show");
    };
    var cleanPhoneValidation = function () {
        document.getElementById('countryCodeMessage').innerHTML = document.getElementById('operatorCodeMessage').innerHTML = document.getElementById('numberMessage').innerHTML =
            document.getElementById('typeMessage').innerHTML = document.getElementById('commentMessage').innerHTML = "";
        document.getElementById('countryCode').style.borderColor = document.getElementById('operatorCode').style.borderColor = document.getElementById('number').style.borderColor =
            document.getElementById('type').style.borderColor = document.getElementById('comment').style.borderColor = "";
    };
    var validateAttachment = function (title, comment) {
        var dataIsValid = true;
        if (document.getElementById('attachment').getAttribute('type') == 'button') {
            var attachments = document.getElementsByName('attachmentFile');
            var counterInput = 0;
            for (var i = 0; i < attachments.length; i++) {
                if (attachments[i].files[0] && attachments[i].getAttribute('number') == (counter - 1).toString()) {
                    if (attachments[i].files[0].size > 10000000) {
                        attachments[i].parentNode.removeChild(attachments[i]);
                        document.getElementById('attachmentMessage').innerHTML = messages['restriction.attachment'];
                        dataIsValid = false;
                    }
                    counterInput++;
                }
            }
            if (counterInput != 1) {
                document.getElementById('attachmentMessage').innerHTML = messages['validation.attachment'];
                dataIsValid = false;
            }
        }
        if (!title) {
            document.getElementById('attachTitleMessage').innerHTML = messages['validation.fill'];
            document.getElementById('attachTitle').style.borderColor = "#A94442";
            dataIsValid = false;
        } else if (title.length > 255) {
            document.getElementById('attachTitleMessage').innerHTML = messages['validation.size.255'];
            document.getElementById('attachTitle').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        if (comment.length > 255) {
            document.getElementById('attachCommentMessage').innerHTML = messages['validation.size.255'];
            document.getElementById('attachComment').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        return dataIsValid;
    };
    var cleanPhonePopup = function () {
        document.getElementById('countryCode').value = document.getElementById('operatorCode').value = document.getElementById('number').value =
            document.getElementById('type').value = document.getElementById('comment').value = "";
        cleanPhoneValidation();
    };
    var createInputTypeFileForAttachment = function () {
        var body = document.getElementById("attachmentRows");
        var fileInput = document.createElement("input");
        fileInput.setAttribute("type", "file");
        fileInput.setAttribute("style", "visibility: hidden; position: fixed;");
        fileInput.setAttribute("name", "attachmentFile");
        fileInput.setAttribute("id", "attachmentFile");
        fileInput.setAttribute("number", counter.toString());
        fileInput.setAttribute("onchange", "contactModule.putPathForAttachment()");
        counter++;
        body.appendChild(fileInput)
    };
    var editAttachmentFields = function () {
        var counter = 0;
        var id;
        var attachmentIds = document.getElementsByName('attachmentId');
        for (var i = 0; i < attachmentIds.length; i++) {
            if (attachmentIds[i].checked) {
                counter++;
                id = attachmentIds[i].value;
            }
        }
        if (counter == 1) {
            document.getElementById('attachCommentId_'.concat(id)).value = document.getElementById('attachComment').value;
            document.getElementById('attachmentFileId_'.concat(id)).value = document.getElementById('attachTitle').value;
            document.getElementById('hrefId_'.concat(id)).innerHTML = document.getElementById('attachTitle').value;
        }
        closeAttachmentPopup();
    };
    var closeAttachmentPopup = function () {
        var attachments = document.getElementsByName('attachmentFile');
        for (var i = 0; i < attachments.length; i++) {
            if (!attachments[i].files[0]) {
                var attachment = attachments[i];
                var number = attachment.getAttribute('number');
                attachment.parentNode.removeChild(attachment);
                i--;
            } else {
                var count = 0;
                var attachment = attachments[i];
                var number = attachment.getAttribute('number');
                var trs = document.getElementsByName('attachmentTr');
                for (var j = 0; j < trs.length; j++) {
                    if (trs[j].getAttribute('number') == number) {
                        count++;
                    }
                }
                if (!count) {
                    attachment.parentNode.removeChild(attachment);
                    i--;
                }
            }
        }
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("attachmentPopup");
        popup.classList.toggle("show");
        document.getElementById('attachment').setAttribute('type', 'button');
        cleanAttachmentPopup();
    };
    var createInputTypeFileForPhoto = function () {
        if (document.getElementById('photoFile') == null) {
            var photoDiv = document.getElementById("image");
            var fileInput = document.createElement("input");
            fileInput.setAttribute("type", "file");
            fileInput.setAttribute("style", "visibility: hidden; position: fixed;");
            fileInput.setAttribute("name", "photoFile");
            fileInput.setAttribute("id", "photoFile");
            fileInput.setAttribute("onchange", "contactModule.putPath()");
            photoDiv.appendChild(fileInput)
        }
    };
    var cleanAttachmentValidation = function () {
        document.getElementById('attachmentMessage').innerHTML = document.getElementById('attachTitleMessage').innerHTML = document.getElementById('attachCommentMessage').innerHTML =
            "";
        document.getElementById('attachTitle').style.borderColor = document.getElementById('attachComment').style.borderColor = "";
    };
    var cleanAttachmentPopup = function () {
        document.getElementById('attachTitle').value = document.getElementById('attachComment').value = "";
        cleanAttachmentValidation()
    };
    var validatePhone = function validatePhone(countryCode, operatorCode, number, type, comment) {
        var dataIsValid = true;
        if (!countryCode || countryCode.toString().search(/[^\d]/) != -1) {
            document.getElementById('countryCodeMessage').innerHTML = messages['validation.digit'];
            document.getElementById('countryCode').style.borderColor = "#A94442";
            dataIsValid = false;
        } else if (countryCode.length > 10) {
            document.getElementById('countryCodeMessage').innerHTML = messages['validation.size.10'];
            document.getElementById('countryCode').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        if (!operatorCode || operatorCode.toString().search(/[^\d]/) != -1) {
            document.getElementById('operatorCodeMessage').innerHTML = messages['validation.digit'];
            document.getElementById('operatorCode').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        if (!number || number.toString().search(/[^\d]/) != -1) {
            document.getElementById('numberMessage').innerHTML = messages['validation.digit'];
            document.getElementById('number').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        if (!type || (type != "Рабочий" && type != "Домашний" && type != "Сотовый")) {
            document.getElementById('typeMessage').innerHTML = messages['validation.select'];
            document.getElementById('type').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        if (comment.length > 255) {
            document.getElementById('commentMessage').innerHTML = messages['validation.size.255'];
            document.getElementById('comment').style.borderColor = "#A94442";
            dataIsValid = false;
        }
        return dataIsValid;
    };
    var editPhoneFields = function () {
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
            document.getElementById('clickEdit').setAttribute('value', 'false');
        }
        closePhonePopup();
    };
    var closePhonePopup = function () {
        var body = document.getElementById('contact_form');
        body.classList.toggle("roll");
        var popup = document.getElementById("phonePopup");
        popup.classList.toggle("show");
        cleanPhonePopup();
    };
    return {
        contactValidation: function () {
            cleanContactValidation();
            var dataIsValid = true;
            var firstName = document.getElementById('firstName').value;
            var lastName = document.getElementById('lastName').value;
            var middleName = document.getElementById('middleName').value;
            var birthday = document.getElementById('birthday').value;
            var nationality = document.getElementById('nationality').value;
            var webSite = document.getElementById('webSite').value;
            var email = document.getElementById('email').value;
            var employmentPlace = document.getElementById('employmentPlace').value;
            var country = document.getElementById('country').value;
            var city = document.getElementById('city').value;
            var street = document.getElementById('street').value;
            var houseNumber = document.getElementById('houseNumber').value;
            var flatNumber = document.getElementById('flatNumber').value;
            var postalIndex = document.getElementById('postalIndex').value;

            if (!firstName || (firstName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
                document.getElementById("firstNameMessage").innerHTML = messages['validation.letter'];
                document.getElementById('firstName').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (firstName.length > 255) {
                document.getElementById("firstNameMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('firstName').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if (!lastName || (lastName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
                document.getElementById("secondNameMessage").innerHTML = messages['validation.letter'];
                document.getElementById('lastName').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (lastName.length > 255) {
                document.getElementById("secondNameMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('lastName').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((middleName.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
                document.getElementById("middleNameMessage").innerHTML = messages['validation.letter'];
                document.getElementById('middleName').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (middleName.length > 255) {
                document.getElementById("middleNameMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('middleName').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            var today = new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate();
            if (/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(birthday.toString()) || new Date(birthday).getTime() > new Date(today).getTime()) {
                document.getElementById("birthdayMessage").innerHTML = messages['validation.birthday'];
                document.getElementById('birthday').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((nationality.toString()).search(/[0-9~@#$%^&*.()_+|>?\/<":}!№;,]/) != -1) {
                document.getElementById("nationalityMessage").innerHTML = messages['validation.letter'];
                document.getElementById('nationality').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (nationality.length > 255) {
                document.getElementById("nationalityMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('nationality').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if (/[~@#$%^&/.*()_+|?><:}!№;,]/.test(employmentPlace.toString())) {
                document.getElementById("employmentPlaceMessage").innerHTML = messages['validation.letter.digit'];
                document.getElementById('employmentPlace').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (employmentPlace.length > 255) {
                document.getElementById("employmentPlaceMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('employmentPlace').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if (webSite) {
                if (!/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z‌​]{2,6}\b([-a-zA-Z0-9‌​@:%_\+.~#?&=]*)/.test(webSite)) {
                    document.getElementById("websiteMessage").innerHTML = messages['validation.url'];
                    document.getElementById('webSite').style.borderColor = "#A94442";
                    dataIsValid = false;
                } else if (webSite.length > 255) {
                    document.getElementById("websiteMessage").innerHTML = messages['validation.size.255'];
                    document.getElementById('webSite').style.borderColor = "#A94442";
                    dataIsValid = false;
                }
            }
            if (email) {
                if (!/(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)) {
                    document.getElementById("emailMessage").innerHTML = messages['validation.email'];
                    document.getElementById('email').style.borderColor = "#A94442";
                    dataIsValid = false;
                } else if (email.length > 255) {
                    document.getElementById("emailMessage").innerHTML = messages['validation.size.255'];
                    document.getElementById('email').style.borderColor = "#A94442";
                    dataIsValid = false;
                }
            }
            if ((country.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,]/) != -1) {
                document.getElementById("countryMessage").innerHTML = messages['validation.letter'];
                document.getElementById('country').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (country.length > 255) {
                document.getElementById("countryMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('country').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((city.toString()).search(/[\d~@#$%^&*.()_+|>?\/<":}!№;,]/) != -1) {
                document.getElementById("cityMessage").innerHTML = messages['validation.letter'];
                document.getElementById('city').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (city.length > 255) {
                document.getElementById("cityMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('city').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((street.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,]/) != -1) {
                document.getElementById("streetMessage").innerHTML = messages['validation.letter.digit'];
                document.getElementById('street').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (street.length > 255) {
                document.getElementById("streetMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('street').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((houseNumber.toString()).search(/[~@#$%^&*.()_+|>?\/<":}!№;,\s]/) != -1) {
                document.getElementById("houseNumberMessage").innerHTML = messages['validation.letter.digit'];
                document.getElementById('houseNumber').style.borderColor = "#A94442";
                dataIsValid = false;
            } else if (houseNumber.length > 255) {
                document.getElementById("houseNumberMessage").innerHTML = messages['validation.size.255'];
                document.getElementById('houseNumber').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((flatNumber.toString()).search(/[^\d]/) != -1) {
                document.getElementById("flatNumberMessage").innerHTML = messages['validation.digit'];
                document.getElementById('flatNumber').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            if ((postalIndex.toString()).search(/[^\d]/) != -1) {
                document.getElementById("postalIndexMessage").innerHTML = messages['validation.digit'];
                document.getElementById('postalIndex').style.borderColor = "#A94442";
                dataIsValid = false;
            }
            return dataIsValid;
        }
        ,
        addPhoto: function () {
            var body = document.getElementById('contact_form');
            body.classList.toggle("roll");
            var popup = document.getElementById("photoPopup");
            popup.classList.toggle("show");
        },
        changeGender: function () {
            return document.getElementById('gender').style.color = '#555';
        },
        changeMaritalStatus: function () {
            document.getElementById('maritalStatus').style.color = '#555';
        },
        changeContactGroup: function () {
            return document.getElementById('contactGroup').style.color = '#555';
        },
        addPhone: function () {
            addPhone()
        },
        deletePhoneFromTable: function () {
            var elms = document.getElementsByName('phoneId');
            for (var i = 0; i < elms.length; i++)
                if (elms[i].checked) {
                    var id = elms[i].value;
                    var td = elms[i].parentNode;
                    var tr = td.parentNode;
                    var body = tr.parentNode;
                    if (id != null && id > 0) {
                        var input = document.createElement('input');
                        input.setAttribute("type", "hidden");
                        input.setAttribute("name", "phoneForDelete");
                        input.setAttribute('value', id);
                        body.appendChild(input);
                    }
                    body.removeChild(tr);
                    i--;
                }
        },
        addAttachments: function () {
            addAttachments()
        },
        deleteAttachmentFromTable: function () {
            var elms = document.getElementsByName('attachmentId');
            for (var i = 0; i < elms.length; i++)
                if (elms[i].checked) {
                    var id = elms[i].value;
                    var td = elms[i].parentNode;
                    var tr = td.parentNode;
                    var number = tr.getAttribute('number');
                    var body = tr.parentNode;
                    if (id && id > 0) {
                        var input = document.createElement('input');
                        input.setAttribute("type", "hidden");
                        input.setAttribute("name", "attachmentForDelete");
                        input.setAttribute('value', id);
                        body.appendChild(input);
                    }
                    var files = document.getElementsByName('attachmentFile');
                    for (var j = 0; j < files.length; j++) {
                        if (files[j].getAttribute('number') == number) {
                            files[j].parentNode.removeChild(files[j]);
                        }
                    }
                    body.removeChild(tr);
                    i--;
                }
        },
        addPhoneTable: function () {
            cleanPhoneValidation();
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
            if (validatePhone(countryCode, operatorCode, number, type, comment) == false) {
                return;
            }
            if (document.getElementById('clickEdit') && document.getElementById('clickEdit').value == 'true') {
                editPhoneFields();
                return;
            }
            var tr = document.createElement("tr");
            var checkTd = document.createElement("td");
            checkTd.setAttribute("style", "width: 3%; border-bottom: 1px solid #ddd");
            var checkInput = document.createElement("input");
            checkInput.setAttribute("type", "checkbox");
            checkInput.setAttribute("name", "phoneId");
            checkTd.appendChild(checkInput);
            tr.appendChild(checkTd);

            var countryCodeTd = document.createElement("td");
            countryCodeTd.setAttribute("style", "width: 11%; border-bottom: 1px solid #ddd; padding-top: 1%");
            var countryCodeInput = document.createElement("input");
            countryCodeInput.setAttribute("readonly", "readonly");
            countryCodeInput.setAttribute("type", "text");
            countryCodeInput.setAttribute("name", countryCodeName);
            countryCodeInput.setAttribute("style", "border: none; width: 100%");
            countryCodeInput.setAttribute("value", countryCode);
            countryCodeTd.appendChild(countryCodeInput);
            tr.appendChild(countryCodeTd);

            var operatorCodeTd = document.createElement("td");
            operatorCodeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd; padding-top: 1%");
            var operatorCodeInput = document.createElement("input");
            operatorCodeInput.setAttribute("readonly", "readonly");
            operatorCodeInput.setAttribute("type", "text");
            operatorCodeInput.setAttribute("name", operatorCodeName);
            operatorCodeInput.setAttribute("style", "border: none; width: 100%");
            operatorCodeInput.setAttribute("value", operatorCode);
            operatorCodeTd.appendChild(operatorCodeInput);
            tr.appendChild(operatorCodeTd);

            var phoneNumberTd = document.createElement("td");
            phoneNumberTd.setAttribute("style", "width: 25%; border-bottom: 1px solid #ddd; padding-top: 1%");
            var phoneNumberInput = document.createElement("input");
            phoneNumberInput.setAttribute("readonly", "readonly");
            phoneNumberInput.setAttribute("type", "text");
            phoneNumberInput.setAttribute("name", numberName);
            phoneNumberInput.setAttribute("style", "border: none; width: 100%");
            phoneNumberInput.setAttribute("value", number);
            phoneNumberTd.appendChild(phoneNumberInput);
            tr.appendChild(phoneNumberTd);

            var typeTd = document.createElement("td");
            typeTd.setAttribute("style", "width: 12%; border-bottom: 1px solid #ddd; padding-top: 1%");
            var typeInput = document.createElement("input");
            typeInput.setAttribute("readonly", "readonly");
            typeInput.setAttribute("type", "text");
            typeInput.setAttribute("name", typeName);
            typeInput.setAttribute("style", "border: none; width: 100%");
            typeInput.setAttribute("value", type);
            typeTd.appendChild(typeInput);
            tr.appendChild(typeTd);

            var commentTd = document.createElement("td");
            commentTd.setAttribute("style", "width: 36%; border-bottom: 1px solid #ddd; padding-top: 1%");
            var commentInput = document.createElement("input");
            commentInput.setAttribute("readonly", "readonly");
            commentInput.setAttribute("type", "text");
            commentInput.setAttribute("name", commentName);
            commentInput.setAttribute("style", "border: none; width: 100%");
            commentInput.setAttribute("value", comment);
            commentTd.appendChild(commentInput);
            tr.appendChild(commentTd);
            body.appendChild(tr);
            cleanPhonePopup();
            addPhone();
        },
        closePhonePopup: function () {
            closePhonePopup();
        },
        uploadAttachment: function () {
            createInputTypeFileForAttachment();
            var attachments = document.getElementsByName('attachmentFile');
            for (var i = 0; i < attachments.length; i++) {
                if (attachments[i].getAttribute('number') == (counter - 1).toString()) {
                    attachments[i].click();
                }
            }
        },
        addAttachmentTable: function () {
            cleanAttachmentValidation();
            var commentName = "attachComment";
            var attachmentName = "attachTitle";
            var comment = document.getElementById(commentName).value;
            var title = document.getElementById(attachmentName).value;

            if (!validateAttachment(title, comment)) {
                return;
            }
            var body = document.getElementById("attachmentRows");
            if (document.getElementById('attachment').getAttribute('type') == 'hidden') {
                editAttachmentFields();
                return;
            }
            var tr = document.createElement("tr");
            tr.setAttribute("number", (counter - 1).toString());
            tr.setAttribute("name", "attachmentTr");
            var checkTd = document.createElement("td");
            checkTd.setAttribute("style", "border-bottom: 1px solid #ddd");
            var checkInput = document.createElement("input");
            checkInput.setAttribute("type", "checkbox");
            checkInput.setAttribute("name", "attachmentId");
            checkTd.appendChild(checkInput);
            tr.appendChild(checkTd);

            var attachmentTd = document.createElement("td");
            attachmentTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding-top: 1%");
            var attachmentInput = document.createElement("input");
            attachmentInput.setAttribute("readonly", "readonly");
            attachmentInput.setAttribute("type", "text");
            attachmentInput.setAttribute("name", attachmentName);
            attachmentInput.setAttribute("style", "border: none; width: 100%");
            attachmentInput.setAttribute("value", title);
            attachmentTd.appendChild(attachmentInput);
            tr.appendChild(attachmentTd);
            if (document.getElementById('page') != null && document.getElementById('page').value == 'edit') {
                var dataTd = document.createElement("td");
                dataTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding-top: 1%");
                tr.appendChild(dataTd);
            }
            var titleTd = document.createElement("td");
            titleTd.setAttribute("style", "border-bottom: 1px solid #ddd; padding-top: 1%");
            var titleInput = document.createElement("input");
            titleInput.setAttribute("readonly", "readonly");
            titleInput.setAttribute("type", "text");
            titleInput.setAttribute("name", commentName);
            titleInput.setAttribute("style", "border: none; width: 100%");
            titleInput.setAttribute("value", comment);
            titleTd.appendChild(titleInput);

            tr.appendChild(titleTd);


            body.appendChild(tr);

            closeAttachmentPopup();
            document.getElementById('attachment').setAttribute('type', 'button');
            document.getElementById('attachmentRestriction').innerHTML = messages['restriction.head.attachment'];
        },
        closeAttachmentPopup: function () {
            document.getElementById('attachmentRestriction').innerHTML = messages['restriction.head.attachment'];
            closeAttachmentPopup();
        },
        findPhoto: function () {
            createInputTypeFileForPhoto();
            document.getElementById('photoFile').click();
        },
        savePhotoFile: function () {
            if (document.getElementById('photoPath').textContent != "") {
                var photoFile = document.getElementById('photoFile');
                if (photoFile.files[0].size < 1500000) {
                    var body = document.getElementById('contact_form');
                    body.classList.toggle("roll");
                    var popup = document.getElementById("photoPopup");
                    popup.classList.toggle("show");
                    document.getElementById('fotoMessage').innerHTML = messages['photo.saved'];
                    document.getElementById('photoPathMessage').innerHTML = '';
                } else {
                    document.getElementById('photoPathMessage').innerHTML = messages['validation.photo'];
                    var file = document.getElementById('photoFile');
                    file.parentNode.removeChild(file);
                    document.getElementById('photoPath').innerHTML = '';
                }
            } else {
                document.getElementById('photoPathMessage').innerHTML = messages['validation.add.photo'];
            }
        },
        deletePhoto: function () {
            if (document.getElementById('photoPath').textContent == "") {
                var photo = document.getElementById('photoFile');
                if (photo && photo.value) {
                    photo.parentNode.removeChild(photo);
                }
                var body = document.getElementById('contact_form');
                body.classList.toggle("roll");
                var popup = document.getElementById("photoPopup");
                popup.classList.toggle("show");
                document.getElementById('fotoMessage').innerHTML = ''
            } else if (document.getElementById('photoPath').textContent != "") {
                var photo = document.getElementById('photoFile');
                if (photo) {
                    photo.parentNode.removeChild(photo);
                    document.getElementById('fotoMessage').innerHTML = '';
                }
                document.getElementById('photoPath').innerHTML = "";
                var body = document.getElementById('contact_form');
                body.classList.toggle("roll");
                var popup = document.getElementById("photoPopup");
                popup.classList.toggle("show");
            }
            document.getElementById('photoPathMessage').innerHTML = '';
        },
        putPath: function () {
            document.getElementById('photoPath').innerHTML = document.getElementById('photoFile').value;
        },
        putPathForAttachment: function () {
            var array = document.getElementById('attachmentFile').value.split('\\');
            document.getElementById('attachTitle').value = array[array.length - 1];
        },
        editAttachment: function () {
            var counter = 0;
            var id;
            var attachmentIds = document.getElementsByName('attachmentId');
            for (var i = 0; i < attachmentIds.length; i++) {
                if (attachmentIds[i].checked) {
                    counter++;
                    id = attachmentIds[i].value;
                }
            }
            if (counter == 1 && id > 0) {
                document.getElementById('attachmentRestriction').innerHTML = '';
                document.getElementById('attachTitle').value = document.getElementById('attachmentFileId_'.concat(id)).value;
                document.getElementById('attachComment').value = document.getElementById('attachCommentId_'.concat(id)).value;
                document.getElementById('attachment').setAttribute('type', 'hidden');
                addAttachments();
            }
        },
        editPhone: function () {
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
                document.getElementById('clickEdit').setAttribute('value', 'true');
                addPhone();
            }
        },
        edit: function () {
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
            document.getElementById("image").style.pointerEvents = "auto";
            document.getElementById("phoneButtons").style.visibility = "visible";
            document.getElementById("attachmentButtons").style.visibility = "visible";
            document.getElementById("bigEditButton").style.visibility = "visible";
            var photoImage = document.getElementById("photoImage");
            if (photoImage != null) {
                photoImage.style.cursor = "pointer";
            }
            var defaultImage = document.getElementById("defaultImage");
            if (defaultImage != null) {
                defaultImage.style.cursor = "pointer";
            }
            var phoneCheckBoxes = document.getElementsByName("phoneId");
            for (var j = 0; j < phoneCheckBoxes.length; j++) {
                phoneCheckBoxes[j].style.visibility = "visible";
            }
            var attachmentCheckboxes = document.getElementsByName("attachmentId");
            for (var i = 0; i < attachmentCheckboxes.length; i++) {
                attachmentCheckboxes[i].style.visibility = "visible";
            }
        }
    }
}());
