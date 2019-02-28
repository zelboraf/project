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
        .bold {
            font-weight: bold;
        }
        .lightgray {
            background: lightgray;
        }
        .lightgreen {
            background: lightgreen;
        }
    </style>
</head>
<body>
<h1>Offers:</h1>
<table>
    <tr>
        <th>Description</th>
        <th>Seller</th>
        <th>Type</th>
        <th>City</th>
        <th>District</th>
        <th>Area<br/>[m²]</th>
        <th>price<br/>[tys. zł]</th>
        <th>price/m²<br/>[tys. zł]</th>
        <th>details</th>
    </tr>
    <c:forEach items="${offers}" var="offer">
        <tr class="${offer.cheap == true ? 'lightgreen' : ''}">
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td>${offer.type.name}</td>
            <td>${offer.city.name}</td>
            <td>${offer.district.name}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
            <td class="center"><a href="/details/${offer.id}">details</a></td>
        </tr>
    </c:forEach>
    <tr class="lightgray">
        <td colspan="5" class="center bold">average offer</td>
        <td class="center bold">${averageOffer.area}</td>
        <td class="center bold">${averageOffer.price}</td>
        <td class="center bold">${averageOffer.pricePerM2}</td>
        <td></td>
    </tr>
</table>
<form action="update" method="get">
    <select name="typeName">
    <c:forEach items="${types}" var="type">
            <option value="${type.name}">${type.name}</option>
    </c:forEach>
    </select>
    <select name="cityName">
        <c:forEach items="${cities}" var="city">
            <option value="${city.name}">${city.name}</option>
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
