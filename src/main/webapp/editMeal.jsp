<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <!-- 2. Подключить CSS-файл библиотеки Bootstrap 3 DateTimePicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css"/>

    <!-- 3. Подключить библиотеку jQuery -->
    <script src="js/jquery.min.js"></script>
    <!-- 4. Подключить библиотеку moment -->
    <script src="js/moment-with-locales.min.js"></script>
    <!-- 5. Подключить js-файл фреймворка Bootstrap 3 -->
    <script src="js/bootstrap.min.js"></script>
    <!-- 6. Подключить js-файл библиотеки Bootstrap 3 DateTimePicker -->
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form method="POST" action='${pageContext.request.contextPath}/meals' name="frmAddMeal">
    <input type="hidden" readonly="readonly" name="mealid"
           value="<c:out value="${meal.id}" />"/> <br/>
    DateTime :
    <c:choose>
        <c:when test="${not empty meal.dateTime}">
            <input type="text" id="dob" name="dob"
                   value="${f:formatLocalDateTime(meal.dateTime, 'MM/dd/yyyy HH:mm')}"/>
        </c:when>
        <c:otherwise>
            <input type="datetime-local" id="datepicker" name="dob" value=""/>
        </c:otherwise>
    </c:choose>
    <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <input
            type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>