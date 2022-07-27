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
<html>
<head>
    <title><fmt:message key="menu.title"/> </title>
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
</head>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="../header/header.jsp"%>
    </header>
    <div class="container justify-content-center">
        <h3 class="text-center p-3"><fmt:message key="menu.new_product"/></h3>
        <form name="AddProductFord" method="post" action="${absolutePath}/controller"  novalidate>
            <input type="hidden" name="command" value="insert_new_product"/>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_name"/> </label>
                <input type="text" id="productName" name="product_name" value="${param.product_name}" class="form-control" required pattern="^[A-Za-zА-Яа-я\s]{3,50}$" />
                <span class="error"></span>
                <div id="nameHelp" class="form-text"><fmt:message key="menu.name_pattern"></fmt:message></div>
                <c:choose>
                    <c:when test="${!empty invalid_product_name}">
                        <div class="invalid-feedback-backend" style="color: red">
                            <fmt:message key="menu.invalid_product_name"/>
                        </div>
                    </c:when>
                    <c:when test="${!empty not_uniq_product_name}">
                        <div class="invalid-feedback-backend" style="color: red">
                            <fmt:message key="menu.not_uniq_product_name"/>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            <div class="form-group" class="mb-3">
            <label class="form-label"><fmt:message key="menu.product_description"/></label>
            <input type="text" id="productDescription" name="product_description" value="${fn:escapeXml(param.product_description)}" class="form-control" pattern=".{0,200}" />
            <span class="error"></span>
            <div id="descriptionHelp" class="form-text"><fmt:message key="menu.description_pattern"></fmt:message></div>
            <c:if test="${!empty invalid_product_description}">
                <div class="invalid-feedback-backend" style="color: red">
                <fmt:message key="menu.invalid_description"/>
                </div>
            </c:if>
            </div>
            <div class="form-group" class="mb-3">
            <label class="form-label"><fmt:message key="menu.product_weight"/></label>
            <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"  value="${param.product_weight}" var="formattedWeight"/>
            <input type="text" id="productWeight" name="product_weight" class="form-control" value="${formattedWeight}" required pattern="\d{1,6}" />
            <span class="error"></span>
            <div id="weightHelp" class="form-text"><fmt:message key="menu.weight_pattern"></fmt:message></div>
            <c:if test="${!empty invalid_product_weight}">
                <div class="invalid-feedback-backend" style="color: red">
                <fmt:message key="menu.invalid_product_weight"/>
                </div>
            </c:if>
            </div>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_loyal_score"/></label>
                <input type="text" id="productLoyalScore" name="product_loyal_score" value="${param.product_loyal_score}" class="form-control" required pattern="\d{1,6}(\.[0-9]{1,2})?" />
                <span class="error"></span>
                <div id="loyalScoreHelp" class="form-text"><fmt:message key="menu.loyal_score_pattern"></fmt:message></div>
                <c:if test="${!empty invalid_product_loyal_score}">
                    <div class="invalid-feedback-backend" style="color: red">
                    <fmt:message key="menu.invalid_product_loyal_score"/>
                    </div>
                </c:if>
            </div>
            <div class="form-group" class="mb-3">
                <label class="form-label"><fmt:message key="menu.product_cost"/></label>
                <input type="text" id="productPrice" name="product_price" value="${param.product_price}" class="form-control" required pattern="\d{1,6}(\.[0-9]{1,2})?" />
                <span class="error"></span>
                <div id="costHelp" class="form-text"><fmt:message key="menu.cost_pattern"></fmt:message></div>
                <c:if test="${!empty invalid_product_price}">
                    <div class="invalid-feedback-backend" style="color: red">
                    <fmt:message key="menu.invalid_product_price"/>
                    </div>
                </c:if>
            </div>
            <select id="productSection" class="form-select" aria-label="Default select example" name="product_section">
                <option selected disabled value=""><fmt:message key="menu.product_section"/></option>
                <c:forEach var="item" items="${applicationScope.section_list}">
                    <option value="${item.sectionId}">${item.sectionName}</option>
                </c:forEach>
            </select>
            <span class="error"></span>
            <c:if test="${!empty invalid_product_section}">
                <div class="invalid-feedback-backend" style="color: red">
                <fmt:message key="menu.invalid_product_section"/>
                </div>
            </c:if>
            <div class="text-center mb-3">
                <button type="submit" class="btn btn-primary"><fmt:message key="menu.insert_menu"/> </button>
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
const productName = document.getElementById('productName');
const productNameError = document.querySelector('#productName + span.error');
const productDescription = document.getElementById('productDescription');
const productDescriptionError = document.querySelector('#productDescription + span.error');
const productWeight = document.getElementById('productWeight');
const productWeightError = document.querySelector('#productWeight + span.error');
const productLoyalScore = document.getElementById('productLoyalScore');
const productLoyalScoreError = document.querySelector('#productLoyalScore + span.error');
const productPrice = document.getElementById('productPrice');
const productPriceError = document.querySelector('#productPrice + span.error');
const productSection = document.getElementById('productSection');
const productSectionError = document.querySelector('#productSection + span.error');

productName.addEventListener('input', function (event) {
  if (productName.validity.valid) {
    productNameError.textContent = '';
    productNameError.className = 'error';
  } else {
    showProductNameError();
  }
});

productDescription.addEventListener('input', function (event) {
  if (productDescription.validity.valid) {
    productDescriptionError.textContent = '';
    productDescriptionError.className = 'error';
  } else {
    showProductDescriptionError();
  }
});

productWeight.addEventListener('input', function (event) {
  if (productWeight.validity.valid) {
    productWeightError.textContent = '';
    productWeightError.className = 'error';
  } else {
    showProductWeightError();
  }
});

productLoyalScore.addEventListener('input', function (event) {
  if (productLoyalScore.validity.valid) {
    productLoyalScoreError.textContent = '';
    productLoyalScoreError.className = 'error';
  } else {
    showProductLoyalScoreError();
  }
});

productPrice.addEventListener('input', function (event) {
  if (productPrice.validity.valid) {
    productPriceError.textContent = '';
    productPriceError.className = 'error';
  } else {
    showProductPriceError();
  }
});

productSection.addEventListener('input', function (event) {
  if (productSection.value != '') {
    productSectionError.textContent = '';
    productSectionError.className = 'error';
  } else {
    showProductSectionError();
  }
});

form.addEventListener('submit', function (event) {
  if(!productName.validity.valid) {
    showProductNameError();
    event.preventDefault();
  }

  if(!productDescription.validity.valid) {
    showProductDescriptionError();
    event.preventDefault();
  }

  if(!productWeight.validity.valid) {
    showProductWeightError();
    event.preventDefault();
  }

  if(!productLoyalScore.validity.valid) {
    showProductLoyalScoreError();
    event.preventDefault();
  }
  
  if(!productPrice.validity.valid) {
    showProductPriceError();
    event.preventDefault();
  }

  if(productSection.value == '') {
    showProductSectionError();
    event.preventDefault();
  }

})

function showProductNameError() {
  productNameError.textContent = '<fmt:message key="menu.invalid_product_name"/>';
  productNameError.className = 'error active';
}

function showProductDescriptionError() {
  productDescriptionError.textContent = '<fmt:message key="menu.invalid_description"/>';
  productDescriptionError.className = 'error active';
}

function showProductWeightError() {
  productWeightError.textContent = '<fmt:message key="menu.invalid_product_weight"/>';
  productWeightError.className = 'error active';
}

function showProductLoyalScoreError() {
  productLoyalScoreError.textContent = '<fmt:message key="menu.invalid_product_loyal_score"/>';
  productLoyalScoreError.className = 'error active';
}

function showProductPriceError() {
  productPriceError.textContent = '<fmt:message key="menu.invalid_product_price"/>';
  productPriceError.className = 'error active';
}

function showProductSectionError() {
  productSectionError.textContent = '<fmt:message key="menu.invalid_product_section"/>';
  productSectionError.className = 'error active';
}

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>