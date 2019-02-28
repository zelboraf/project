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
<h1>Offer details:</h1>
<p>Type: ${offer.type.name}</p>
<p>City: ${offer.city.name}</p>
<p>District: ${offer.district.name}</p>
<br/>
<p>Description: ${offer.description}</p>
<p>Seller: ${offer.seller}</p>
<p>Area: ${offer.area}</p>
<p>Price per m²: ${offer.pricePerM2}</p>
<br>
<p>Current price: ${offer.price}</p>
<p>Price history:</p>
<table>
    <tr>
        <th>Date</th>
        <th>Price</th>
        <th>Price per m²</th>
    </tr>
    <c:forEach items="${prices}" var="price">
        <tr>
            <td class="center">${price.localDateTime}</td>
            <td class="center">${price.value}</td>
            <td class="center">${price.pricePerM2}</td>
        </tr>
    </c:forEach>
</table>
<form action="/">
    <input type="submit" value="home" />
</form>
</body>
</html>
