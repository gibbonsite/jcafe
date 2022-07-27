<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        window.history.forward();
        function noBack() {
            window.history.forward();
        }
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css" type="text/css">
    <script src="${absolutePath}/js/counter.js"></script>
    <script type="text/javascript" src="${absolutePath}/js/f5.js"></script>
</head>
<body class="body" style="background: whitesmoke"  onLoad="noBack();" onpageshow="if (event.persisted) noBack();" onUnload="">
<div class="page">
    <header>
        <%@include file="header/header.jsp"%>
    </header>
    <div class="box box_padding catalog-wrapp catalog-body" style="padding-left:0px;">
        <div class="catalog" style="margin:0px;width:1600px;">
           <div style="padding-top:20px; width:500px;">
	       <div class="col-3 text-center" style="display: inline-block;"><fmt:message key="action.sort"/></div>
	       <div style="display: inline-block;">
	        <form name="sortByPrice" action="${absolutePath}/controller">
	            <input type="hidden" name="command" value="sort_all_menu_by_price">
	            <c:if test="${not empty param.section_id}">
	                <input type="hidden" name="section_id" value="${param.section_id}">
	            </c:if>
	            <button type="submit" class="btn btn-warning btn-sm"><fmt:message key="menu.sort_by_price"/></button>
	        </form>
	       </div>
	       <div style="display: inline-block;">
	           <form name="sortByPopularity" action="${absolutePath}/controller">
	               <input type="hidden" name="command" value="sort_all_menu_by_popularity">
	               <c:if test="${not empty param.section_id}">
	                   <input type="hidden" name="section_id" value="${param.section_id}">
	               </c:if>
	               <button type="submit" class="btn btn-warning btn-sm"><fmt:message key="menu.sort_by_popularity"/></button>
	           </form>
	       </div>
	       </div>
	       
			<div class="container-fluid" style="width:1600px;">
            <c:choose>
                <c:when test="${empty menu_list}">
                    <h3 class="text-center"><fmt:message key="menu.empty"/> </h3>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${menu_list}" var="menu">
                        <div class="catalog-item" style="width:340px; display: inline-block;">
                            <div class="product">
                                <div class="product_header">
                                    <div class="product_title"><b>${menu.name}</b></div>
                                </div>
                                <div class="product_figure" style="height:165px;">
                                    <c:choose>
                                        <c:when test="${empty menu.picturePath}">
                                            <img src="${absolutePath}/download_image?image_path=/srv/images/cafe/default-image_1920.png" alt="" class="product_img" style="height:135px;">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${absolutePath}/download_image?image_path=${menu.picturePath}" alt="" class="product_img" style="height:135px;">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product_info"><fmt:message key="menu.product_weight"/> <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"  value="${menu.weight}"/></div>
                                <c:choose>
                                    <c:when test="${not empty menu.description}">
                                        <div class="product_consist mb-2"><c:out value="${menu.description}"/> <br><br><br></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="product_consist mb-2"><br><br><br></div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="">
                                    <div class="product_price"><fmt:message key="menu.product_loyal_score"/> <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${menu.loyalScore}"/>  </div>
                                    <div class="product_price"><fmt:message key="menu.product_price"/> <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${menu.price}"/> <fmt:message key="menu.product_money"/>  </div>
                                    <c:if test="${user.role eq 'CLIENT'}">
                                        <form action="${absolutePath}/controller" method="post">
                                            <input type="hidden" name="command" value="add_product_to_cart">
                                            <input type="hidden" name="selected" value="${menu.menuId}">
                                            <div class="product_actions">
                                                <div class="counter">
                                                    <div class="counter_btn counter_btn_minus btn-secondary text-center" style="width: 30px; display:inline-block;">&#8211;</div>
                                                    <input  type="text"  class="counter_number" max="100" id="product_number" name="product_number" value="1" style="width: 33px; display:inline-block;">
                                                    <div class="counter_btn counter_btn_plus btn-secondary text-center" style="width: 30px; display:inline-block;">+</div>
                                                </div>
                                                <button type="submit" class="btn btn-primary js_add-to-cart"><fmt:message key="action.to_cart"/> </button>
                                            </div>
                                        </form>
                                    </c:if>
                                    <c:if test="${user.role eq 'ADMIN'}">
                                        <form name="UploadPhoto" method="post" action="${absolutePath}/upload_product_photo" enctype="multipart/form-data">
                                            <input type="hidden" name="command" value="upload_product_photo">
                                            <input type="hidden" name="product_name" value="${menu.name}">
                                            <br />
                                            <div class="form-group mb-2">
                                                <label class="form-label"><fmt:message key="menu.picture"/></label>
                                                <input type="file" name="picture_path" class="form-control form-control-sm">
                                            </div>
                                            <button type="submit" class="btn btn-primary btn-sm mb-2"><fmt:message key="menu.insert_menu"/></button>
                                        </form>
                                        <div>
                                            <div class="row">
                                                <div class="col">
                                                    <a class="btn btn-info btn-sm" href="${absolutePath}/controller?command=go_to_update_product_page&id=<c:out value="${menu.menuId}"/>" role="button"><fmt:message key="profile.update"/></a>
                                                </div>
                                                <div class="col">
                                                    <form action="${absolutePath}/controller" method="post">
                                                        <input type="hidden" name="command" value="delete_product">
                                                        <input type="hidden" name="id" value="${menu.menuId}">
                                                        <button type="submit" class="btn btn-danger btn-sm"><fmt:message key="action.delete"/></button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
			</div>
        </div>
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