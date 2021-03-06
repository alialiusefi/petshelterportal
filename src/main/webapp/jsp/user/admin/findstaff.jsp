<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <fmt:setLocale value="${sessionLang}"/>
    <fmt:setBundle basename="by.training.finaltask.resource.localization"/>
    <title><fmt:message key="findStaff"/></title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/popper.min.js"></script>
</head>

<body>
<jsp:include page="/jsp/tags/menu.jsp" flush="true"/>

<br>

<legend>
    <center><h2><b><fmt:message key="findStaff"/> </b></h2></center>
</legend>
<c:if test="${not empty message}">
    <center>
        <label class="text text-danger">
            <fmt:message key="${message}"/>
        </label>
    </center>
</c:if>

<br>
<div class="table mx-auto" style="max-width: 95%">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th><fmt:message key="username"/></th>
            <th><fmt:message key="role"/></th>
            <th><fmt:message key="email"/></th>
            <th><fmt:message key="firstName"/></th>
            <th><fmt:message key="lastName"/></th>
            <th><fmt:message key="dateofbirth"/></th>
            <th><fmt:message key="address"/></th>
            <th><fmt:message key="contactNumber"/></th>
            <th><fmt:message key="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultUsers}" var="adoptions" varStatus="i">
            <tr>
                <td><c:out value="${adoptions.id}"/></td>
                <td><c:out value="${adoptions.username}"/></td>
                <td><c:out value="${adoptions.userRole.getName()}"/></td>
                <td><c:out value="${resultsUserInfo[i.index].email}"/></td>
                <td><c:out value="${resultsUserInfo[i.index].firstName}"/></td>
                <td><c:out value="${resultsUserInfo[i.index].lastName}"/></td>
                <td><fmt:formatDate type="date" dateStyle="medium"
                                    value="${resultsUserInfo[i.index].dateOfBirth.time}"/>
                </td>
                <td><c:out value="${resultsUserInfo[i.index].address}"/></td>
                <td>+<c:out value="${resultsUserInfo[i.index].phone}"/></td>
                <td>
                    <form action="<c:url value="/user/userdelete.html"/>"
                          method="post">
                        <input type="hidden" name="userToDelete" value="${adoptions.id}">
                        <input type="submit" value="<fmt:message key="deleteUser"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${param.page > 1}">
            <li class="page-item">
                <a class="page-link" href="<c:url value="${paginationURL += '?page=' += (param.page - 1)}"/>">
                    Previous</a>
            </li>
        </c:if>
        <c:forEach var="i" begin="1" end="${amountOfPages}">

            <a class="page-link" href="<c:url value="${paginationURL += '?page=' += i}"/>">
                <c:out value="${i}"/></a>
        </c:forEach>
        <c:if test="${param.page < amountOfPages}">
            <li class="page-item">
                <a class="page-link" href="<c:url value="${paginationURL += '?page=' += (param.page + 1)}"/>">
                    Next</a>
            </li>
        </c:if>
    </ul>
</nav>

<div class="table-light">
    <div class="row">
        <div class="col-md-4">
            <%--Search by first name--%>
            <form class="form-inline" method="post"
                  action="<c:url value="/user/admin/findstaffbyfirstname.html?page=1"/>">
                <div class="form-group">
                    <label class="col-form-label"><fmt:message key="searchByFirstName"/></label>
                    <input class="form-control mr-sm-2" name="firstnameParameter"
                           type="search" placeholder="Search" aria-label="Search"
                           value="${sessionScope.firstnameParameter}">
                    <button style="margin-left: 0; z-index: 999;" class="btn btn-outline-success my-2 my-sm-0"
                            type="submit">Search
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <%--Search by phone--%>
            <form class="form-inline" method="post"
                  action="<c:url value="/user/admin/findstaffbyphone.html?page=1"/>">
                <div class="form-group">
                    <label class="col-form-label"><fmt:message key="searchByPhone"/></label>
                    <input class="form-control mr-sm-2"
                           name="phoneParameter" type="search" placeholder="Search"
                           aria-label="Search" value="${sessionScope.phoneParameter}">
                    <button style="margin-left: 0; z-index: 999;" class="btn btn-outline-success my-2 my-sm-0"
                            type="submit">Search
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form action="<c:url value="/user/admin/findstaff.html?page=1"/>" method="post" class="form-inline">
                <div class="form-group">
                    <button style="margin-left: 0; z-index: 999;" class="btn btn-outline-success my-2 my-sm-0"
                            type="submit">
                        <fmt:message key="reset"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/jsp/tags/footer.jsp" flush="true"/>

</body>
</html>
