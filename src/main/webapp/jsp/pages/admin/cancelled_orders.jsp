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
<jsp:useBean id="order_list" scope="request" type="java.util.List"/>
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
        <c:choose>
            <c:when test="${order_list.isEmpty()}">
                <h3 class="text-center"><fmt:message key="order.empty_cancelled_orders"/> </h3>
            </c:when>
            <c:otherwise>
                <h3 class="text-center"><fmt:message key="order.cancelled_orders"/> </h3>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col" class="col"><fmt:message key="order.id"/></th>
                        <th scope="col" class="col"><fmt:message key="order.date_state_change"/> </th>
                        <th scope="col" class="col"><fmt:message key="order.state"/> </th>
                        <th scope="col" class="col"><fmt:message key="menu.product_price"/></th>
                        <th scope="col" class="col"><fmt:message key="order.payment"/></th>
                        <th scope="col" class="col"><fmt:message key="order.address"/></th>
                        <th scope="col" class="col"><fmt:message key="order.comment"/></th>
                        <th scope="col" class="col"><fmt:message key="registration.login"/></th>
                        <th scope="col" class="col"><fmt:message key="registration.phone"/> </th>
                        <th scope="col" class="col-2"><fmt:message key="menu.products"/> </th>
                        <th scope="col" class="col"><fmt:message key="order.current_loyal_score"/></th>
                        <th scope="col" class="col"><fmt:message key="order.change_loyal_score"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderItem" items="${order_list}">
                        <tr>
                            <td class="col"><c:out value="${orderItem.order.orderId}"/></td>
                            <td><c:out value="${orderItem.order.orderDate.toLocalDate()} ${orderItem.order.orderDate.toLocalTime()}"/></td>
                            <td class="col"><fmt:message key="order.order_state_${orderItem.order.orderState}"/></td>
                            <td class="col"><c:out value="${orderItem.order.totalCost}"/></td>
                            <td class="col"><fmt:message key="order.payment_type_${orderItem.order.paymentType}"/> </td>
                            <td class="col"><c:out value="${orderItem.order.address}"/></td>
                            <td class="col"><c:out value="${orderItem.order.userComment}"/></td>
                            <td class="col"><c:out value="${orderItem.user.login}"/> </td>
                            <td class="col"><c:out value="${orderItem.user.phoneNumber}"/> </td>
                            <td class="col-2">
                                <c:forEach var="item" items="${orderItem.menuList}">
                                    <p style="margin-top: 0; margin-bottom: 0"><c:out value="${item.name}"/> - <c:out value="${item.amount}"/></p>
                                </c:forEach>
                            </td>
                            <td class="col">
				                <div class="form-group col mb-3">
									${orderItem.user.loyalScore}				                
								</div>
                            <td class="col">
				                <div class="form-group col mb-3">
				                    <form role="form" action="${absolutePath}/controller" method="post"  novalidate>
		           						<input type="hidden" name="command" value="update_loyal_score">
		           						<input type="hidden" name="id" value="${orderItem.order.orderId}">
			                			<input type="text" name="change_loyal_score" value="0.0" class="form-control" required style="width: 99px;">
	                                    <button type="submit" class="btn btn-success" style="margin-top:6px;margin-bottom:6px;"><fmt:message key="order.update_loyal_score"/></button>
	                                    <c:if test="${!empty invalid_loyal_score_amount}">
					                        <div class="invalid-feedback-backend" style="color: red">
						                        <fmt:message key="order.invalid_loyal_score_negative_amount"/>
					                        </div>
					                    </c:if>
				                    </form>
				                    <form role="form" action="${absolutePath}/controller" method="post"  novalidate>
		           						<input type="hidden" name="command" value="block_user_by_id">
		           						<input type="hidden" name="id" value="${user.userId}">
	                                    <button type="submit" class="btn btn-success"><fmt:message key="admin.block_action"/></button>
	                                </form>
	                            </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="pages">
        <div class="justify-content-center" >
            <ctg:pagination currentPage="${requestScope.currentPage}" lastPage="${requestScope.lastPage}" url="${requestScope.url}"/>
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