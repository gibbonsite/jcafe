<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<jsp:useBean id="cart" scope="session" type="java.util.HashMap"/>
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
    <title><fmt:message key="header.cart"/> </title>
</head>
<body  onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="../header/header.jsp"%>
    </header>
    <div class="container-fluid justify-content-center">
    <div class="justify-content-center">
        <c:choose>
            <c:when test="${cart.isEmpty() eq 'false'}">
                <h3 class="text-center p-3"><fmt:message key="header.cart"/></h3>
                </br>
            	<div class="justify-content-center col-12 col-sm-6 mt-3" style="width:600px; margin: 0 auto;">
                <table class="table table-bordered ">
                    <thead>
                    <tr>
                        <th><fmt:message key="menu.product_name"/> </th>
                        <th><fmt:message key="menu.cost"/> </th>
                        <th><fmt:message key="menu.number_products"/> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${cart}">
                        <input type="hidden" value="<c:out value="${product.key.menuId}"/>"/>
                        <tr>
                            <td><c:out value="${product.key.name}"/></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${product.key.price}"/> </td>
                            <td><c:out value="${product.value}"/> </td>
                            <td>
                                <form action="${absolutePath}/controller" method="post">
                                    <input type="hidden" name="command" value="delete_product_in_cart">
                                    <input type="hidden" name="id" value="${product.key.menuId}">
                                    <button type="submit" class="btn-danger"><fmt:message key="action.delete"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="mb-3"><fmt:message key="menu.product_price"/> <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${total_price}" var="formattedPrice"/> <c:out value="${formattedPrice}" /> <fmt:message key="menu.product_money"/> </div>
                <form name="CartForm" method="post" action="${absolutePath}/controller" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="create_order"/>
                    <input type="hidden" name="total_price" value="${total_price}"/>
                    <div class="form-group">
                        <label class="form-label"><fmt:message key="order.comment"/></label>
                        <input type="text" name="user_comment" class="form-control form-control-sm" value="${fn:escapeXml(user_comment)}" pattern="^.{0,200}$">
                        <c:if test="${! empty invalid_order_comment}">
                            <div class="invalid-feedback-backend" style="color: red">
                            	<fmt:message key="order.invalid_comment"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group" style="padding-top:12px;">
                        <label class="form-label"><fmt:message key="order.order_date"/></label>
	                    <input type="datetime-local" name="order_date" class="form-control form-control-sm" value="${order_date}" />
	                    <div id="orderDateHelp" class="form-text"><fmt:message key="order.order_date_pattern"></fmt:message></div>
	                    
                        <c:if test="${! empty invalid_order_date}">
                            <div class="invalid-feedback-backend" style="color: red">
                            	<fmt:message key="order.invalid_order_date"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group" style="padding-top:12px;">
                        <label class="form-label"><fmt:message key="order.payment"/></label>
	                    <select class="form-select" aria-label="Default select example" name="product_payment" value="${product_payment}" required>
	                        <option disabled value=""><fmt:message key="order.payment"/></option>
	                        <option ${product_payment == 'CASH' || empty product_payment ? 'selected' : ''} value="CASH"><fmt:message key="order.payment_cash"/> </option>
	                        <option ${product_payment == 'CASH_ON_DELIVERY' ? 'selected' : ''} value="CASH_ON_DELIVERY"><fmt:message key="order.payment_cash_on_delivery"/> </option>
	                    </select>
	                    <c:if test="${! empty invalid_order_payment}">
	                        <div class="invalid-feedback-backend" style="color: red">
	                            <fmt:message key="order.invalid_order_payment"/>
	                        </div>
	                    </c:if>
	                    <c:if test="${! empty out_of_cash}">
	                        <div class="invalid-feedback-backend" style="color: red">
	                            <fmt:message key="order.out_of_cash"/>
	                        </div>
	                    </c:if>
                    </div>
                    <c:if test="${suggested_discount > 0}">
                    	<fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"  value="${suggested_discount * 100}" var="suggested_discount_formatted"/>
                    	<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${discounted_price}" var="discounted_price_formatted"/>
	                    <div class="form-group" style="padding-top:12px;">
	                        <label class="form-label"><fmt:message key="order.discount"/></label>
		                    <select class="form-select" aria-label="Default select example" name="order_discount" value="${order_discount}" required>
		                        <option disabled value=""><fmt:message key="order.discount"/></option>
		                        <option ${order_discount == 'SUGGESTED_DISCOUNT' || empty order_discount ? 'selected' : ''} value="SUGGESTED_DISCOUNT"><fmt:message key="order.suggested_discount"/> ${suggested_discount_formatted}%, <fmt:message key="order.discounted_price" /> ${discounted_price_formatted} <fmt:message key="menu.product_money"/> </option>
		                        <option ${order_discount == 'ACCUMULATIVE_DISCOUNT' ? 'selected' : ''} value="ACCUMULATIVE_DISCOUNT"><fmt:message key="order.accumulative_discount"/> </option>
		                    </select>
		                    
		                    <c:if test="${! empty invalid_order_discount}">
		                        <div class="invalid-feedback-backend" style="color: red">
		                            <fmt:message key="order.invalid_order_discount"/>
		                        </div>
		                    </c:if>
	                    </div>
                    </c:if>
                    <div class="form-group" style="padding-top:12px;">
                        <label class="form-label"><fmt:message key="order.address"/></label>
	                    <input type="text" name="address" class="form-control form-control-sm" required value="${fn:escapeXml(address)}" pattern="^.{4,200}$"/>
	                    <c:if test="${! empty invalid_order_address}">
	                        <div class="invalid-feedback-backend" style="color: red">
	                            <fmt:message key="order.invalid_order_address"/>
	                        </div>
	                    </c:if>
                    </div>
                    
                    <div class="text-center" style="padding-top:12px;">
                        <button type="submit" class="btn btn-primary"><fmt:message key="order.confirm"/></button>
                    </div>
                </form>
                </div>
            </c:when>
            <c:otherwise>
                <h1 class="text-center"><fmt:message key="order.empty"/> </h1>
            </c:otherwise>
        </c:choose>
    </div>
    </div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>