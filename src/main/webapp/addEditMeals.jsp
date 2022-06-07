<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new user</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=dateTime]').datepicker();
    });
</script>

<form method="POST" action='MealOptionalServlet' name="frmAddMeal">
    User ID : <input type="text" readonly="readonly" name="uuid"
                     value="<c:out value="${meal.uuid}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    Date : <input
        type="datetime-local" name="dateTime"
        <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="dateTime"
                       type="both"/>
/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>