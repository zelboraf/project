<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style>
        table, th, tr, td {
            border-collapse: collapse;
            border: 1px solid black;
            padding: 1px;
            font-size: small;
        }
        .center {
            text-align: center;
        }
    </style>
</head>
<body>
<h1>mieszkania:</h1>
<table>
    <tr>
        <th>opis</th>
        <th>sprzedawca</th>
    </tr>
    <c:forEach items="${prices}" var="price">
        <tr>
            <td class="center">${price.localDateTime}</td>
            <td class="center">${price.value}</td>
        </tr>
    </c:forEach>
</table>
<form action="/">
    <input type="submit" value="home" />
</form>
</body>
</html>
