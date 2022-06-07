<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>User Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="meal">
        <tr>
        <tr style="background-color:${meal.excess ? 'red' : 'greenyellow'}">
            <td>${meal.uuid}</td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="MealOptionalServlet?action=edit&uuid=<c:out value="${meal.uuid}"/>">Update</a></td>
            <td><a href="MealOptionalServlet?action=delete&uuid=<c:out value="${meal.uuid}"/>">Delete</a></td>
        </tr>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="MealOptionalServlet?action=insert">Add meal</a></p>
</body>
</html>