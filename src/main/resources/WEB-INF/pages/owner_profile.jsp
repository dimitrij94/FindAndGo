<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 20.09.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
</head>
<body>
<div id="head"></div>
<security:authorize access="hasRole('ROLE_OWNER')">
    <div id="toolbar">
    <a href="/browzer/newplace"><p>Add new place</p></a>
    <a href="/browzer/edit_profile"/${owner.id}>Edit profile</a>
    </div>
</security:authorize>
<table id="list">
    <thead>
    <tr>
        <th></th>
        <th>Name</th>
        <th>City</th>
        <th>Street</th>
        <th>Address</th>
        <th>Rating</th>
        <th>Followers</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${owner.places}" var="place">
        <tr>
            <td>

                <img src="/browzer/getImage/${place.placePhotos[0].id}"
                     alt="${place.placeName} photo"
                     width="40px"
                     height="40px"/>
            </td>
            <td>
                <a href="browzer/place_profile/${place.id}">
                    <c:out value="${place.placeName}"/>
                </a>
            </td>
            <td><c:out value="${place.placeCity}"/></td>
            <td><c:out value="${place.placeStreet}"/></td>
            <td><c:out value="${place.placeAddress}"/></td>
            <td>
                <c:forEach begin="0" end="${place.placeFinalRating}">
                    <img src="images/zvezda.png" alt="zvezda" height="15"/>
                </c:forEach>
            </td>
            <td><c:out value="${place.placeFollowersNum}"/></td>
            <td><a href="browzer/delete_place/${place.id}">Delete</a></td>
            <td><a href="browzer/edit_place/${place.id}">Edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
