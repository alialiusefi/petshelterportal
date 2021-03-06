<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
    <title>Add Pet</title>
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
<jsp:include page="/jsp/tags/menu.jsp" flush="true"/>


<div class="container">
    <form action="" enctype="multipart/form-data" method="post">
        <fieldset>
            <legend>
                <center><h2><b><fmt:message key="addPet"/> </b></h2></center>
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
        </fieldset>
        <div class="container">
            <label>
                <fmt:message key="petPicture"/>:
            </label>
            <input type="file" required name="petPicture">
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label>
                    <fmt:message key="petName"/>:
                </label>
                <input name="petName"
                       type="text"
                       class="form-control"
                       placeholder="<fmt:message key="petName"/>"
                       value="${param.petName}"
                       pattern="^[a-zA-Z]+$"
                       required
                >
            </div>
            <div class="form-group col-md-6">
                <label><fmt:message key="weight"/>:</label>
                <input name="petWeight" type="text" class="form-control" placeholder="6.9"
                       value="${param.petWeight}" pattern="^[0-9]+(.[0-9]+)?$" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label>
                    <fmt:message key="dateofbirth"/>:
                </label>
                <input name="dateOfBirth" required
                       type="date"
                       value="${param.dateOfBirth}"
                       class="form-control"/>
            </div>
            <div class="form-group col-md-6">
                <label><fmt:message key="dateSheltered"/>:</label>
                <input required
                       name="dateSheltered" type="date" value="${param.dateSheltered}" class="form-control">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputShelter">
                    <fmt:message key="shelter"/>:
                </label>
                <select required name="shelter" id="inputShelter" class="form-control">
                    <c:forEach items="${shelterList}" var="i">
                        <option value="${i.id}">${i.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-4">
                <label for="inputBreed">
                    <fmt:message key="breed"/>:
                </label>
                <select required name="breed" id="inputBreed" class="form-control">
                    <c:forEach items="${breedList}" var="q">
                        <option value="${q.id}">${q.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-2">
                <label for="inputStatus">
                    <fmt:message key="petStatus"/>:
                </label>
                <select required name="petStatus" id="inputStatus" class="form-control">
                    <option selected value="SHELTERED">
                        <fmt:message key="sheltered"/>
                    </option>
                    <option value="ADOPTED">
                        <fmt:message key="adopted"/>
                    </option>
                    <option value="DEAD">
                        <fmt:message key="dead"/>
                    </option>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="addPet"/></button>
    </form>
</div>
<jsp:include page="/jsp/tags/footer.jsp" flush="true"/>
</body>
</html>
