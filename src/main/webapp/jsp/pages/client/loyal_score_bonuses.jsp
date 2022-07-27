<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty sessionScope.language}"> <fmt:setLocale value="${sessionScope.language}" scope="session"/></c:when>
    <c:when test="${empty sessionScope.language}"> <fmt:setLocale value="${sessionScope.language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<jsp:useBean id="loyal_score_bonus_list" scope="request" type="java.util.List"/>
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
    <title><fmt:message key="header.orders"/> </title>
</head>
<body  onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="../header/header.jsp"%>
    </header>
    <div class="container-fluid">
        <h3 class="text-center"><fmt:message key="bonus.all_loyal_score_bonuses"/> </h3>
        <div class="container col-12 col-sm-6 mt-3">
            <dl class="row my-3">
                <dt class="col-2 mb-3">
                    <fmt:message key="bonus.client_loyal_score"/>
                </dt>
                <div class="form-group col-10 mb-3">
           			<input type="text" name="loyal_score" value="${user.loyalScore}" class="form-control" required readonly />
            			
                </div>
            </dl>
            <dl class="row my-3">
                <dt class="col-2 mb-3">
                    <fmt:message key="bonus.client_discount"/>
                </dt>
                <div class="form-group col-10 mb-3">
                	<fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"  value="${loyal_score_discount * 100}" var="user_discount"/>
           			<input type="text" name="discount" value="${user_discount}" class="form-control" required readonly />
            			
                </div>
            </dl>
        <h3 class="text-center"><fmt:message key="bonus.loyal_score_bonuses"/> </h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="bonus.loyal_score"/></th>
                <th scope="col"><fmt:message key="bonus.discount"/> </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="loyal_score_bonus" items="${loyal_score_bonus_list}">
                <tr>
                    <td><c:out value="${loyal_score_bonus.loyalScore}"/></td>
                    <td><fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"  value="${loyal_score_bonus.discount * 100}" var="item_discount"/>
                    <c:out value="${item_discount}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>