<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ page pageEncoding="UTF-8" %>
    <meta charset="UTF-8" content="text/html"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/place_profile.css"/>

    <style>
        body {
            scrollbar-face-color: #5997CA;
            scrollbar-shadow-color: #ffffff;
            scrollbar-highlight-color: #ffffff;
            scrollbar-3dlight-color: #5997CA;
            scrollbar-darkshadow-color: #5997CA;
            scrollbar-track-color: #F6F6F6;
            scrollbar-arrow-color: #F6F6F6;
        }
    </style>

    <script type="text/javascript" src="/resources/js/jquery.min.js">
        $(document.ready(function () {
            $(".place").mouseover(function () {
                this.$(".photo").css("left", "-48px");
            });

        }));
    </script>
</head>
<c:choose>
    <c:when test="${owner ne null}">
        <div id="owner">
        <h1><c:out value="${owner.ownerName}"/></h1>

        <h3><c:out value="${owner.ownerEmail}"/></h3>

        <c:if test="${owner.ownerPlaces ne null}">
            <div class="places_link_list">
                <c:forEach items="${owner.ownerPlaces}" var="place">
                    <div class="place_link">
                        <a href="browzer/profile?${place.id}">
                            <c:out value="${place.placeName}"/>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        </c:if>

        <c:if test="${place ne null}">

            <div class="positive_comments">
                <ul class="comment_list">
                    <li class="comment">Some very positive comment</li>
                </ul>
            </div>

            <div class="negative_comments">
                <ul class="comment_list">
                    <li class="comment">Some very negative comment</li>
                </ul>
            </div>

            <table class="place">
                <tbody class="body">
                <tr>
                    <td rowspan="5" class="photoFrame"><img class="photo" src="images/user.png" width="150px"></td>
                    <td class="prop">Name:</td>
                    <td class="val"><c:out value="${place.placeName}"/></td>
                </tr>
                <tr>
                    <td class="prop">Address:</td>
                    <td class="val"><c:out value="${place.placeCity}"/>, <c:out value="${place.placeStreet}"/>,
                        <c:out value="${place.placeAddress}"/></td>
                </tr>
                <tr>
                    <td class="prop">Speciality:</td>
                    <td class="val"><<c:out value="${place.placeSpeciality}"/></td>
                </tr>
                <tr>
                    <td class="prop">Description</td>
                    <td class="val"><c:out value="${place.placeDescription}"/></td>
                </tr>
                <tr>
                    <td class="prop">Rating</td>
                    <td class="val">
                        <c:forEach begin="1" end="${place.placeFinalRating}">
                            <img class="star" src="images/zvezda.png" alt="zvezda" height="15"/>
                        </c:forEach>
                    </td>
                </tr>
                </tbody>
                <tfoot>

                <tr>
                    <td class="menu_list_wrapper" colspan="3">
                        <div class="menu_list_wrapper">
                            <ul class="menu_list">
                                <c:forEach items="${place.placeMenu}" var="menu"/>
                                <li class="menu_list_item">
                                    <div class="menu_name"><c:out value="${menu.menuName}"/></div>
                                    <div class="menu_price"><c:out value="${menu.menuPrice}"/></div>
                                    <c:forEach begin="1" end="${menu.menuFinalRating}">
                                        <img class="menu_star" src="images/zvezda.png" alt="zvezda"
                                             height="15"/>
                                    </c:forEach>
                                </li>
                                <li>Add new item to menu</li>
                            </ul>
                        </div>
                    </td>
                </tr>
                </tfoot>
            </table>
        </c:if>
        <c:if test="${place eq null}">
            <div class="newplace"><a href="/browzer/newplace_page">Click to add new place</a></div>
        </c:if>
    </c:when>
    <c:otherwise>
        <div class="alternative error">
            Error occurred, please try to reload the page.
        </div>
    </c:otherwise>
</c:choose>
<div class="gradient"></div>
</body>
</html>