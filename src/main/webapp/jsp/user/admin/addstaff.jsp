<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <c:url value="/css/bootstrap.min.css" var="cssURL"/>
    <link href="${cssURL}" rel="stylesheet" type="text/css">
    <script src="${jsURL}" type="text/javascript"></script>
    <c:url value="/js/bootstrap.bundle.min.js" var="jsURL"/>
    <c:url value="/login.html" var="loginActionURL"/>
    <c:url value="/logout.html" var="logoutActionURL"/>
    <c:url value="/" var="titleActionURL"/>
    <c:url value="/register.html" var="registerActionURL"/>
    <c:url value="/user/profile.html" var="profileActionURL"/>
    <fmt:setLocale value="${sessionLang}"/>
    <fmt:setBundle basename="by.training.finaltask.resource.localization"/>
    <title><fmt:message key="addStaff"/></title>
</head>
<body>
<!-- MenuItem -->
<jsp:include page="/jsp/tags/menu.jsp" flush="true"/>

<br>
<div class="container">
    <form class="form" action=" " method="post" id="contact_form">
        <fieldset>
            <!-- Form Name -->
            <legend>
                <center><h2><b><fmt:message key="addStaff"/> </b></h2></center>
            </legend>
            <br>
            <c:if test="${not empty successMessage}">
                <div class="text-center text-info">
                    <p>Attention: <fmt:message key="${successMessage}"/></p>
                </div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="text-center text-warning">
                    <p>Attention: <fmt:message key="${message}"/></p>
                </div>
            </c:if>
            <!-- Text input-->
            <div class="form-row">
                <div class="form-group col-md-3">
                    <label class="col-md-6 control-label"><fmt:message key="firstName"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="firstname" placeholder="<fmt:message key="firstName"/>"
                                   class="form-control" value="${param.firstname}" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <label class="col-md-6 control-label"><fmt:message key="lastName"/> </label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="lastname" placeholder="<fmt:message key="lastName"/>"
                                   class="form-control" value="${param.lastname}" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <label class="col-md-6 control-label"><fmt:message key="dateofbirth"/> </label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="dateofbirth" type="date"
                                   class="form-control" value="${param.dateofbirth}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-3">
                    <label class="col-md-6 control-label"><fmt:message key="username"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="username" placeholder="<fmt:message key="username"/>"
                                   class="form-control" value="${param.login}" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <label class="col-md-6 control-label"><fmt:message key="password"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="password" placeholder="<fmt:message key="password"/>" class="form-control"
                                   type="password">
                        </div>
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <label class="col-md-6 control-label"><fmt:message key="email"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="email" placeholder="<fmt:message key="email"/>"
                                   class="form-control" value="${param.email}" type="text">
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label class="col-md-6 control-label"><fmt:message key="contactNumber"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="contactnumber" placeholder="<fmt:message key="contactNumberFormat"/>"
                                   class="form-control" value="${param.number}" type="text">
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group col-md-6">
                    <label class="col-md-6 control-label"><fmt:message key="address"/></label>
                    <div class="col-md-6 inputGroupContainer">
                        <div class="input-group">
                            <input name="address" placeholder="<fmt:message key="address"/>"
                                   class="form-control" value="${param.address}" type="text">
                        </div>
                    </div>
                </div>
            </div>

            <!-- Button -->
            <div class="form-row mx-auto" style="max-width: 55%">
                <div class="form-group col-md-5"><br>
                    <button type="submit" class="btn btn-primary"><fmt:message key="addStaff"/></button>
                </div>
                <div class="form-group col-md-2"><br>
                    <button type="reset" class="btn btn-primary">
                        <fmt:message key="reset"/></button>
                </div>
            </div>

        </fieldset>
    </form>
</div>

<jsp:include page="/jsp/tags/footer.jsp" flush="true"/>

</body>
</html>
