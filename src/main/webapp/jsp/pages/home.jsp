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
	    function noBack()
	    {
	        window.history.forward();
	    }
	</script>
    <title><fmt:message key="main.title"/> </title>
</head>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="header/header.jsp"%>
    </header>

<div class="container-fluid justify-content-center" style="background-image: url('${absolutePath}/picture/cafe-stock-image.png');">
    <div style="width:1200px;margin:auto;padding-top:30px;">
    <img src="${absolutePath}/picture/logo.png" style="padding:24px;"/>
    <img src="${absolutePath}/picture/foodbonus.png" style="padding:24px;"/>
    <img src="${absolutePath}/picture/accumulativediscount.png"  style="padding:24px;"/>
    <p align="justify" style="font-weight:bold;margin-top:30px;">
    
        <fmt:message key="cafe.home_text_one"/>
    </p>
    <p align="justify" style="font-weight:bold;">
        <fmt:message key="cafe.home_text_two"/>
    </p>
    <div class="text-center">
    <fmt:message key="cafe.go_to_menu_beginning"/> <a href="${absolutePath}/controller?command=find_all_menu"><fmt:message key="cafe.go_to_menu_end"/></a>.
    </div>
    </div>
</div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>