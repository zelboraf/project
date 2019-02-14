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
<p>wczytanych ofert: ${countProcessed}, nowych: ${countNew}, zaktualizowanych: ${countUpdated}, zapisanych w bazie: ${countAll}</p>
<h1>nowe oferty:</h1>
<table>
    <tr>
        <th>opis</th>
        <th>sprzedawca</th>
        <th>powierzchnia<br/>[m²]</th>
        <th>cena<br/>[tys. zł]</th>
        <th>cena/m²<br/>[tys. zł]</th>
    </tr>
    <c:forEach items="${newOffers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
<h1>zaktualizowane oferty:</h1>
<table>
    <tr>
        <th>opis</th>
        <th>sprzedawca</th>
        <th>powierzchnia<br/>[m²]</th>
        <th>stara cena<br/>[tys. zł]</th>
        <th>nowa cena<br/>[tys. zł]</th>
        <th>cena/m²<br/>[tys. zł]</th>
    </tr>
    <c:forEach items="${updatedOffers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.oldPrice}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
