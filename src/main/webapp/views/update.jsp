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
<c:forEach items="${counters}" var="counter">
    <p>Offers processed: ${counter.processed}, new: ${counter.created}, updated: ${counter.updated}, database overall: ${counter.all}</p>
</c:forEach>
<h1>New offers:</h1>
<table>
    <tr>
        <th>Description</th>
        <th>Seller</th>
        <th>Area<br/>[m²]</th>
        <th>Price<br/>[tys. zł]</th>
        <th>Price/m²<br/>[tys. zł]</th>
    </tr>
    <c:forEach items="${createdOffers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
<h1>Updated offers:</h1>
<table>
    <tr>
        <th>Description</th>
        <th>Seller</th>
        <th>Area<br/>[m²]</th>
        <th>Price<br/>[tys. zł]</th>
        <th>Price/m²<br/>[tys. zł]</th>
    </tr>
    <c:forEach items="${updatedOffers}" var="offer">
        <tr>
            <td>${offer.description}</td>
            <td>${offer.seller}</td>
            <td class="center">${offer.area}</td>
            <td class="center">${offer.price}</td>
            <td class="center">${offer.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
<form action="/">
    <input type="submit" value="home" />
</form>
</body>
</html>
