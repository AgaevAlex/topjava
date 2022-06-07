<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<table>
    <colgroup>
        <col span="5" style="background:Khaki">
    </colgroup>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <tr>
        <c:forEach items="${list}" var="meal">
    <tr>
    <tr style="background-color:${meal.excess ? 'greenyellow' : 'red'}">
        <td>
            <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                           type="both"/>
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
        </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
    </tr>
    </c:forEach>
    </tr>
</table>
</body>
</html>
