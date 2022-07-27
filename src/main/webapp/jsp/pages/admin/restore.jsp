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
    <script type="text/javascript">
        window.history.forward();
        function noBack() {
            window.history.forward();
        }
    </script>
    <title><fmt:message key="restore.title"/> </title>
</head>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="../header/header.jsp"%>
    </header>
        <div class="container justify-content-center" style="width: 30%; align-content: center">
            <c:choose>
                <c:when test="${requestScope.restore_menu eq 'true'}">
                </c:when>
                <c:when test="${requestScope.restore_section eq 'true'}">
                </c:when>
                <c:otherwise>
                <c:set var="restore_section" value="true" scope="request"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${requestScope.restore_menu eq 'true'}">
                    <c:choose>
                        <c:when test="${empty requestScope.menu_list}">
                            <h1 class="text-center"><fmt:message key="restore.empty"/> </h1>
                        </c:when>
                        <c:otherwise>
                            <br>
                            <table class="table table-striped text-center">
                                <thead>
                                <tr>
                                    <th><fmt:message key="menu.product_name"/> </th>
                                    <th><fmt:message key="menu.product_description"/> </th>
                                    <th><fmt:message key="menu.product_weight"/> </th>
                                    <th><fmt:message key="admin.users_action"/> </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="menu" items="${menu_list}">
                                    <input type="hidden" value="<c:out value="${menu.menuId}"/>"/>
                                    <tr>
                                        <td><c:out value="${menu.name}"/></td>
                                        <td><c:out value="${menu.description}"/></td>
                                        <td><c:out value="${menu.weight}"/></td>
                                        <td>
                                            <div class="row">
                                                <div class="col">
                                                    <form action="${absolutePath}/controller" method="post">
                                                        <input type="hidden" name="command" value="restore_menu_product">
                                                        <input type="hidden" name="id" value="${menu.menuId}">
                                                        <button type="submit" class="btn-danger"><fmt:message key="action.restore"/></button>
                                                    </form>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${requestScope.restore_section eq 'true'}">
                    <c:choose>
                        <c:when test="${empty requestScope.section_list}">
                            <h1 class="text-center"><fmt:message key="restore.empty"/> </h1>
                        </c:when>
                        <c:otherwise>
                            <br>
                            <table class="table table-striped text-center">
                             <thead>
                            <tr>
                                <th><fmt:message key="section.title"/> </th>
                                <th><fmt:message key="admin.users_action"/> </th>
                            </tr>
                            </thead>
                         <tbody>
                            <c:forEach var="section" items="${section_list}">
                            <input type="hidden" value="<c:out value="${section.sectionId}"/>"/>
                            <tr>
                                <td><c:out value="${section.sectionName}"/></td>
                                <td>
                                    <div class="row">
                                        <div class="col">
                                            <form action="${absolutePath}/controller" method="post">
                                                <input type="hidden" name="command" value="restore_section">
                                                <input type="hidden" name="section_id" value="${section.sectionId}">
                                                <button type="submit" class="btn-danger"><fmt:message key="action.restore"/></button>
                                            </form>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
                </c:when>
            </c:choose>

        </div>
 <div class="text-center">
  <ctg:footertag/>
 </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>