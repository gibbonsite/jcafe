<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
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
    <div class="row" style="height:20px;">
        <h3 class="text-center p-3"><fmt:message key="order.score_report"/></h3>
        <form role="form" action="${absolutePath}/controller" method="post"  novalidate>
            <input type="hidden" name="command" value="update_order_score_report">
                    <c:if test="${!empty param.invalid_score_report}">
	                    <div class="invalid-feedback-backend" style="color: red">
	                        <fmt:message key="order.score_report.invalid_score_report"/>
	                    </div>
                    </c:if>
        <div class="container col-12 col-sm-6 mt-3">
            <dl class="row my-3">
                <dt class="col-2 mb-3">
                    <fmt:message key="order.score_report.order"/>
                </dt>
                <div class="form-group col-10 mb-3">
	                <input type="text" class="form-control" name="id" readonly="readonly" value="${id}">
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="order.score_report.score"/>
                </dt>
                <div class="form-group col-10 mb-3">
	                <input type="range" class="form-range" id="score" name="order_score" min="0" max="10" step="1" style="border-width:0px;" value="${!empty order_score ? order_score : '9'}">
                </div>
                <dt class="col-2 mb-3">
                    <fmt:message key="order.score_report.report"/>
                </dt>
                <div class="form-group col-10 mb-3">
           			<input id="order_report" type="text" name="order_report" value="${fn:escapeXml(order_report)}" class="form-control" required pattern="^.{0,900}$" />
                    <span class="error"></span>
                    <div id="orderReportHelp" class="form-text"><fmt:message key="order.score_report.report_pattern"></fmt:message></div>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-success"><fmt:message key="order.score_report.confirm"/></button>
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

const form  = document.getElementsByTagName('form')[0];
const amount = document.getElementById('amount');
const amountError = document.querySelector('#amount + span.error');

amount.addEventListener('input', function (event) {
  if (amount.validity.valid) {
    amountError.textContent = '';
    amountError.className = 'error';
  } else {
    showAmountError();
  }
});

form.addEventListener('submit', function (event) {
  if(!amount.validity.valid) {
	showAmountError();
    event.preventDefault();
  }
});

function showAmountError() {
  amountError.textContent = '<fmt:message key="profile.top_up_balance.invalid_amount"/>';
  amountError.className = 'error active';
}

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>