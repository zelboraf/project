<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>powierzchnia<br/>[m²]</th>
        <th>cena<br/>[tys. zł]</th>
        <th>cena/m²<br/>[tys. zł]</th>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td class="center">${avgOffer.area}</td>
        <td class="center">${avgOffer.price}</td>
        <td class="center">${avgOffer.pricePerM2}</td>
    </tr>
    <c:forEach items="${offers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
