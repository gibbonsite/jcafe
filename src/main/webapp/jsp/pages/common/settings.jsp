<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<fmt:message key="registration.birthday" var="user_birthday"/>
<fmt:message key="registration.email" var="user_email"/>
<fmt:message key="registration.first_name" var="user_first_name"/>
<fmt:message key="registration.last_name" var="user_last_name"/>
<fmt:message key="registration.login" var="user_login"/>
<fmt:message key="registration.password" var="user_pass"/>
<fmt:message key="registration.phone" var="user_phone"/>
<fmt:message key="registration.name" var="reg_name"/>

<fmt:message key="registration.enter_birthday" var="e_birthday"/>
<fmt:message key="registration.enter_email" var="e_email"/>
<fmt:message key="registration.enter_first_name" var="e_first_name"/>
<fmt:message key="registration.enter_last_name" var="e_last_name"/>
<fmt:message key="registration.enter_login" var="e_login"/>
<fmt:message key="registration.enter_password" var="e_password"/>
<fmt:message key="registration.enter_phone" var="e_phone"/>
<html>
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${absolutePath}/CSS/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        window.history.forward();
        function noBack() {
            window.history.forward();
        }
    </script>
    <script type="text/javascript" src="${absolutePath}/js/f5.js"></script>
    <title><fmt:message key="profile.settings"/></title>
</head>
<body  onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="../header/header.jsp"%>
    </header>
    <c:choose>
        <c:when test="${param.result eq 'true'}">
            <div class="alert alert-success" role="alert" id="message"><fmt:message key="action.result_success"/></div>
        </c:when>
    </c:choose>
    <div class="row">
        <h3 class="text-center p-3"><fmt:message key="profile.settings"/></h3>
        <form role="form" action="${absolutePath}/controller" method="post"  novalidate>
            <input type="hidden" name="command" value="update_user_profile">
        <div class="container col-12 col-sm-6 mt-3">
            <dl class="row my-3">
                <dt class="col-2 mb-3">
                    <fmt:message key="registration.first_name"/>
                </dt>
                <div class="form-group col-10 mb-3">
                    <c:choose>
                        <c:when test="${!empty param.first_name}">
                            <input id="firstName" type="text" name="first_name" value="${param.first_name}" class="form-control" placeholder="${e_first_name}" required pattern="^[A-Za-zА-Яа-я]{3,50}$" />
                        </c:when>
                        <c:otherwise>
                            <input id="firstName" type="text" name="first_name" value="${user.firstName}" class="form-control" placeholder="${e_first_name}" required pattern="^[A-Za-zА-Яа-я]{3,50}$" />
                        </c:otherwise>
                    </c:choose>
	                <span class="error"></span>
                    <div id="firstNameHelp" class="form-text"><fmt:message key="registration.correct_first_name" /></div>
                    <c:if test="${!empty invalid_first_name}">
	                    <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="registration.invalid_first_name"/>
	                    </div>
                    </c:if>
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="registration.last_name"/>
                </dt>
                <div class="form-group col-10 mb-3">
                    <c:choose>
                        <c:when test="${!empty param.last_name}">
                            <input id="lastName" type="text" name="last_name" value="${param.last_name}" class="form-control" placeholder="${e_last_name}" required pattern="^[A-Za-zА-Яа-я]{3,50}$" />
                        </c:when>
                        <c:otherwise>
                            <input id="lastName" type="text" name="last_name" value="${user.lastName}" class="form-control" placeholder="${e_last_name}" required pattern="^[A-Za-zА-Яа-я]{3,50}$" />
                        </c:otherwise>
                    </c:choose>
			        <span class="error"></span>
			        <div id="lastNameHelp" class="form-text"><fmt:message key="registration.correct_last_name"></fmt:message></div>
                    <c:if test="${!empty invalid_last_name}">
                        <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="registration.invalid_last_name"/>
                        </div>
                    </c:if>
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="registration.email"/>
                </dt>
                <div class="form-group col-10 mb-3">
                    <c:choose>
                        <c:when test="${!empty param.email}">
                            <input id="email" type="email" name="email" value="${param.email}" class="form-control" placeholder="${e_email}" required pattern="^[A-Za-z0-9\.]{1,30}@[a-z]{2,7}\.[a-z]{2,4}$" />
                        </c:when>
                        <c:otherwise>
                            <input id="email" type="email" name="email" value="${user.email}" class="form-control" placeholder="${e_email}" required pattern="^[A-Za-z0-9\.]{1,30}@[a-z]{2,7}\.[a-z]{2,4}$" />
                        </c:otherwise>
                    </c:choose>
	                <span class="error"></span>
                    <div id="emailHelp" class="form-text"><fmt:message key="registration.correct_mail"></fmt:message></div>
                    <c:if test="${!empty invalid_email}">
                        <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="registration.invalid_mail"/>
                        </div>
                    </c:if>
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="registration.birthday"/>
                </dt>
                <div class="form-group col-10 mb-3">
                    <c:choose>
                        <c:when test="${!empty param.birthday}">
                            <input id="birthday" type="date" name="birthday" value="${param.birthday}" class="form-control" placeholder="${e_birthday}" required />
                        </c:when>
                        <c:otherwise>
                            <input id="birthday" type="date" name="birthday" value="${user.birthday}" class="form-control" placeholder="${e_birthday}" required />
                        </c:otherwise>
                    </c:choose>
	                <span class="error"></span>
                    <c:if test="${!empty invalid_birthday}">
                        <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="registration.invalid_birthday"/>
                        </div>
                    </c:if>
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="registration.phone"/>
                </dt>
                <div class="form-group col-10 mb-3">
                    <c:choose>
                        <c:when test="${!empty param.phone_number}">
                            <input id="phone" type="text" name="phone_number" value="${param.phone_number}" class="form-control" placeholder="${e_phone}" required pattern="(29|25|44|33)\d{7}">
                        </c:when>
                        <c:otherwise>
                            <input id="phone" type="text" name="phone_number" value="${user.phoneNumber}" class="form-control" placeholder="${e_phone}" required pattern="(29|25|44|33)\d{7}">
                        </c:otherwise>
                    </c:choose>
                    <span class="error"></span>
                    <div id="phoneHelp" class="form-text"><fmt:message key="registration.correct_phone_number"></fmt:message></div>
                    <c:if test="${!empty invalid_phone_number}">
                        <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="registration.invalid_phone_number"/>
                        </div>
                    </c:if>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-success"><fmt:message key="profile.update"/></button>
                </div>
            </dl>
        </div>
        </form>

        <h3 class="text-center p-3"><fmt:message key="profile.change_password"/></h3>
        <form role="form" action="${absolutePath}/controller" method="post" novalidate>
            <input type="hidden" name="command" value="change_password">
            <div class="container col-12 col-sm-6 mt-3">
                <dl class="row my-3">
                    <div class="form-group mb-3">
                        <label class="form-label"><fmt:message key="profile.enter_old_password"/></label>
                        <input id="oldPassword" type="password" name="old_password" value="${param.old_password}" class="form-control" required pattern="^[A-Za-zА-Яа-я0-9\.]{5,40}$" />
                        <span class="error"></span>
                        <div id="oldPasswordHelp" class="form-text"><fmt:message key="registration.correct_password"></fmt:message></div>
                        <c:if test="${!empty invalid_old_password}">
                            <div class="invalid-feedback-backend" style="color: red">
	                            <fmt:message key="profile.invalid_old_password"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group mb-3">
                        <label class="form-label"><fmt:message key="profile.enter_new_password"/></label>
                        <input id="password" type="password" name="new_password" value="${param.new_password}" class="form-control" required pattern="^[A-Za-zА-Яа-я0-9\.]{5,40}$" />
                        <span class="error"></span>
                        <div id="passwordHelp" class="form-text"><fmt:message key="registration.correct_password"></fmt:message></div>
                        <div class="invalid-feedback">
                            <fmt:message key="profile.invalid_repeat_password"/>
                        </div>
                        <c:if test="${!empty invalid_new_password}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="registration.invalid_password"/>
                            </div>
                        </c:if>
                        <c:if test="${! empty invalid_new_uniq_password}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="settings.not_uniq_new_password"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group mb-3">
                        <label class="form-label"><fmt:message key="profile.repeat_new_password"/></label>
                        <input id="repeatPassword" type="password" name="repeat_password" value="${param.repeat_password}" class="form-control" required pattern="^[A-Za-zА-Яа-я0-9\.]{5,40}$" />
                        <span class="error"></span>
                        <div id="passHelp" class="form-text"><fmt:message key="registration.correct_password"></fmt:message></div>
                        <c:if test="${!empty invalid_repeat_password}">
                            <div class="invalid-feedback-backend" style="color: red">
	                            <fmt:message key="profile.invalid_repeat_password"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-success"><fmt:message key="profile.change_password"/></button>
                    </div>
                </dl>
            </div>
        </form>
    </div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
<script type="text/javascript">
(function () {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')

    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

const form0  = document.getElementsByTagName('form')[0];
const form1  = document.getElementsByTagName('form')[1];
const firstName = document.getElementById('firstName');
const firstNameError = document.querySelector('#firstName + span.error');
const lastName = document.getElementById('lastName');
const lastNameError = document.querySelector('#lastName + span.error');
const email = document.getElementById('email');
const emailError = document.querySelector('#email + span.error');
const phone = document.getElementById('phone');
const phoneError = document.querySelector('#phone + span.error');
const birthday = document.getElementById('birthday');
const birthdayError = document.querySelector('#birthday + span.error');
const oldPassword = document.getElementById('oldPassword');
const oldPasswordError = document.querySelector('#oldPassword + span.error');
const password = document.getElementById('password');
const passwordError = document.querySelector('#password + span.error');
const repeatPassword = document.getElementById('repeatPassword');
const repeatPasswordError = document.querySelector('#repeatPassword + span.error');

firstName.addEventListener('input', function (event) {
  if (firstName.validity.valid) {
    firstNameError.textContent = '';
    firstNameError.className = 'error';
  } else {
    showFirstNameError();
  }
});
lastName.addEventListener('input', function (event) {
  if (lastName.validity.valid) {
    lastNameError.textContent = '';
    lastNameError.className = 'error';
  } else {
    showLastNameError();
  }
});
email.addEventListener('input', function (event) {
  if (email.validity.valid) {
    emailError.textContent = '';
    emailError.className = 'error';
  } else {
    showEmailError();
  }
});
phone.addEventListener('input', function (event) {
  if (phone.validity.valid) {
	phoneError.textContent = '';
	phoneError.className = 'error';
  } else {
    showPhoneError();
  }
});
birthday.addEventListener('input', function (event) {
  if (birthday.validity.valid) {
    birthdayError.textContent = '';
    birthdayError.className = 'error';
  } else {
    showBirthdayError();
  }
});
oldPassword.addEventListener('input', function (event) {
  if (oldPassword.validity.valid) {
	  oldPasswordError.textContent = '';
	  oldPasswordError.className = 'error';
  } else {
    showOldPasswordError();
  }
});
password.addEventListener('input', function (event) {
  if (password.validity.valid) {
	  passwordError.textContent = '';
	  passwordError.className = 'error';
  } else {
    showPasswordError();
  }
});
repeatPassword.addEventListener('input', function (event) {
  if (repeatPassword.validity.valid && repeatPassword.value == password.value) {
	  repeatPasswordError.textContent = '';
	  repeatPasswordError.className = 'error';
  } else {
    showRepeatPasswordError();
  }
});

form0.addEventListener('submit', function (event) {
  if(!firstName.validity.valid) {
    showFirstNameError();
    event.preventDefault();
  }
  if(!lastName.validity.valid) {
    showLastNameError();
    event.preventDefault();
  }
  if(!email.validity.valid) {
    showEmailError();
    event.preventDefault();
  }
  if(!phone.validity.valid) {
    showPhoneError();
    event.preventDefault();
  }
  if(!birthday.validity.valid) {
    showBirthdayError();
    event.preventDefault();
  }
});

form1.addEventListener('submit', function (event) {
  if(!oldPassword.validity.valid) {
    showOldPasswordError();
    event.preventDefault();
  }
  if(!password.validity.valid) {
    showPasswordError();
    event.preventDefault();
  }
  if(!(repeatPassword.validity.valid && repeatPassword.value == password.value)) {
    showRepeatPasswordError();
    event.preventDefault();
  }
});

function showFirstNameError() {
  firstNameError.textContent = '<fmt:message key="registration.invalid_first_name"/>';
  firstNameError.className = 'error active';
}

function showLastNameError() {
  lastNameError.textContent = '<fmt:message key="registration.invalid_last_name"/>';
  lastNameError.className = 'error active';
}

function showEmailError() {
  emailError.textContent = '<fmt:message key="registration.invalid_mail"/>';
  emailError.className = 'error active';
}

function showPhoneError() {
  phoneError.textContent = '<fmt:message key="registration.invalid_phone_number"/>';
  phoneError.className = 'error active';
}

function showBirthdayError() {
  birthdayError.textContent = '<fmt:message key="registration.invalid_birthday"/>';
  birthdayError.className = 'error active';
}

function showOldPasswordError() {
  oldPasswordError.textContent = '<fmt:message key="profile.invalid_old_password"/>';
  oldPasswordError.className = 'error active';
}

function showPasswordError() {
  passwordError.textContent = '<fmt:message key="registration.invalid_password"/>';
  passwordError.className = 'error active';
}

function showRepeatPasswordError() {
  repeatPasswordError.textContent = '<fmt:message key="profile.invalid_repeat_password"/>';
  repeatPasswordError.className = 'error active';
}

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>