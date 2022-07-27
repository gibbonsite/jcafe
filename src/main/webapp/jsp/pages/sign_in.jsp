<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="ru_RU" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>

<fmt:message var="log" key="form.sign_in.login"/>
<fmt:message var="pass" key="form.sign_in.password"/>
<fmt:message var="errorMessage" key="error.login_or_password"/>
<fmt:message var="go_back" key="form.button.back_main"/>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${absolutePath}/CSS/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <script type="text/javascript">
	    window.history.forward();
	    function noBack()
	    {
	        window.history.forward();
	    }
    </script>
    <title><fmt:message key="form.sign_in"/></title>
</head>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="header/header.jsp"%>
    </header>
<div class="container justify-content-center col-12 col-sm-6 mt-3">
    <h3 class="text-center p-3"><fmt:message key="form.sign_in"/></h3>
    <form name="LoginForm" method="post" action="${absolutePath}/controller" novalidate>
        <input type="hidden" name="command" value="sign_in"/>
        <br/>
        <div class="form-group" class="mb-3">
            <label class="form-label">${log}</label>
            <input type="text" name="login" class="form-control form-control-sm" value="${user.login}">
        </div>
        <br/>
        <div class="form-group" class="mb-3">
            <label class="form-label">${pass}</label>
            <input type="password" name="password" class="form-control form-control-sm" value="${user.password}">
        </div>
        <c:if test="${!empty incorrect_login_password}">
            <div style="color: red">
           		<fmt:message key="form.sign_in.incorrect_login_password"/>
            </div>
        </c:if>
        <br/>
        <c:if test="${! empty user_status_blocked}">
            <div style="color: red">
           		<fmt:message key="form.sign_in.user_blocked"/>
            </div>
        </c:if>
        <br/>
        <div class="text-center">
            <button type="submit" class="btn btn-primary"><fmt:message key="form.sign_in"/></button>
            <a class="btn btn-info" href="${absolutePath}/jsp/pages/registration.jsp" role="button"><fmt:message key="registration.name"/></a>
        </div>
        </form>
</div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script>
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
</script>
</body><hr/>
</html>