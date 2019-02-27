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
        <th>dzielnica</th>
        <th>powierzchnia<br/>[m²]</th>
        <th>cena<br/>[tys. zł]</th>
        <th>cena/m²<br/>[tys. zł]</th>
        <th>historia cen</th>
    </tr>
    <tr>
        <td colspan="3" style="text-align: center; font-weight: bold">średnio</td>
        <td class="center">${averageOffer.area}</td>
        <td class="center">${averageOffer.price}</td>
        <td class="center">${averageOffer.pricePerM2}</td>
        <td></td>
    </tr>
    <c:forEach items="${offers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td>${offer.district.name}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
            <td class="center"><a href="/price_history/${offer.id}">historia</a></td>
        </tr>
    </c:forEach>
</table>
<form action="update" method="get">
    <select name="residentialTypeName">
    <c:forEach items="${residentialTypes}" var="residentialType">
            <option value="${residentialType.name}">${residentialType.name}</option>
    </c:forEach>
    </select>
    <select name="districtName">
    <c:forEach items="${districts}" var="district">
        <option value="${district.name}">${district.name}</option>
    </c:forEach>
    </select>
    <input type="submit" value="update">

</form>
</body>
</html>
