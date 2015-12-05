<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="securit" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 10.11.2015
  Time: 1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="place" scope="request" type="com.example.domain.Place"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>${place.placeName}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <link href="<c:url value="/static/css/place-profile.css"/>" rel="stylesheet"/>
    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/jquery.Jcrop.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="<c:url value="/static/dist/themes/fontawesome-stars.css"/>">
    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/dist/jquery.barrating.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/jquery-ui-1.8.1.custom.min.js"/>"></script>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/static/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/static/js/jquery.Jcrop.min.js"/>"></script>
    <script src="<c:url value="/static/js/salvattore.min.js"/>"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
    <style>
        .new-menu-form {
            text-align: center;
            width: 65px;
            display: inline;
        }

        .new-menu-form ::-webkit-input-placeholder {
            text-align: center;
        }

        .new-menu-form ::-moz-placeholder {
            text-align: center;
        }

        .new-menu-form :-ms-input-placeholder {
            text-align: center;
        }

        .modal-form-input {
            width: 300px;
            left: 50%;
            margin-left: -150px;
        }

        .modal-new-service-input {
            width: 242px;
            margin-left: -121px;
            left: 50%;
        }

        #new-menu .input-group {
            width: 250px;
            left: 50%;
            margin-left: -125px;
        }

        #new-menu-service .input-group {
            width: 230px;
            left: 50%;
            margin-left: -115px;
        }

        .alert {
            text-align: center
        }

        .service-description {
            word-break: break-all;
            word-wrap: break-word;
            display: inline;
            vertical-align: middle;
            text-align: right
        }

    </style>

</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">MyPlaceToGo</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responcive-menu">
                <span class="sr-only">Відкрити меню</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="responcive-menu">
            <ul class="nav navbar-nav">
                <li><a href="#">На головну</a></li>

                <security:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Мій профіль<b class="caret"></b></a>
                        <ul style="padding:0" id="user-menu-dropdown" class="list-group dropdown-menu">
                            <li id="menu-item-my-page" class="list-group-item"><a href="#"><i
                                    class="fa fa-user fa-fm"></i>Моя
                                сторінка</a></li>
                            <li class="list-group-item"><a href="/user/orders"><i class="fa fa-bullhorn fa-fm"></i>Мої
                                замовлення</a>
                            </li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-calendar-check-o fa-fm"></i>Мої
                                події</a></li>
                            <li class="list-group-item"><a href="/user/places"><i class="fa fa-cutlery fa-fm"></i>Мої
                                заклади</a>
                            </li>
                            <li class="list-group-item"><a href="/logout"><i class="fa fa-power-off fa-fm"></i>Вийти</a>
                            </li>
                        </ul>
                    </li>
                    <security:authorize access="hasRole('ROLE_USER')">
                        <li>
                            <a href="/newplace">Приєднатись</a>
                        </li>
                    </security:authorize>
                </security:authorize>

                <security:authorize access="isAnonymous()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Увійти<b class="caret"></b></a>
                        <ul id="enter-form" class="dropdown-menu navbar-form">
                            <li>
                                <span id="authorization-span"></span>

                                <form action="<c:url value="/login"/>" id="authorization-form" method="POST">

                                    <div class="input-group" id="authorization-email">
                                        <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
                                        <input class="form-control" type="text" name="j_username" placeholder="E-mail">
                                    </div>
                                    <br>

                                    <div class="input-group" id="authorization-password">
                                        <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                                        <input class="form-control" type="password" name="j_password"
                                               placeholder="Password">
                                    </div>

                                    <div id="authorization-divider" class="divider"></div>

                                    <div id="authorization-group" class="btn-group">

                                        <button type="submit" class="btn btn-default authentication-button">
                                            <i class="fa fa-circle-thin"><span style="padding-right: 4px"></span>Увійти</i>
                                        </button>

                                        <a href="#" class="btn btn-default authentication-button">
                                            <i class="fa fa-user-plus"><span
                                                    style="padding-right: 4px"></span>Реестрація</i>
                                        </a>
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </li>
                </security:authorize>
            </ul>

            <form action="" id="search-form" class="navbar-form navbar-right">
                <div style="margin:0" class="input-group">
                    <span class="input-group-addon"><i class="fa fa-search fa-fw"></i></span>
                    <input class="form-control" type="text" placeholder="search stuff">
                </div>
            </form>


        </div>
    </div>
</div>
<c:set var="placeSpeciality" value="${place.placeSpeciality.get(0).speciality}"/>

<div class="container">
    <div class="row">

        <security:authorize access="isAuthenticated()">
            <div class="col col-md-2 hidden-sm hidden-xs">
                <ul class="list-group" id="user-controls">
                    <li style="text-align: center" class="list-group-item">
                   <span class="fa-stack" style="font-size: 60px">
                     <i class="fa fa-circle fa-stack-2x"></i>
                        <c:choose>
                            <c:when test="${placeSpeciality=='Sport'}">
                                <i class="fa fa-footbol fa-stack-1x fa-inverse"></i>
                            </c:when>
                            <c:when test="${placeSpeciality=='NightClub'}">
                                <i class="fa fa-glass fa-stack-1x fa-inverse"></i>
                            </c:when>
                            <c:when test="${placeSpeciality=='Cafe'}">
                                <i class="fa fa-coffee fa-stack-1x fa-inverse"></i>
                            </c:when>
                        </c:choose>
                   </span>
                    </li>
                    <li class="list-group-item">
                        <a href="/user/profile"><i class="fa fa-user fa-fm"></i><c:out value="Мій профайл"/></a>
                    </li>
                    <li class="list-group-item">
                        <a href="/user/orders"> <i class="fa fa-bullhorn fa-fm"></i>Мої замовлення</a>
                    </li>
                    <li class="list-group-item"><a href="/user/${user.id}/events">
                        <i class="fa fa-calendar-check-o fa-fm"></i>Мої події</a></li>
                    <li class="list-group-item"><a href="/user/${user.id}/places">
                        <i class="fa fa-cutlery fa-fm"></i>Мої заклади</a>
                    </li>

                    <li class="list-group-item">
                        <a href="#"><i class="glyphicon glyphicon-cog fa-fm"></i>Налаштування</a>
                    </li>

                    <li class="list-group-item">
                        <a href="/j_spring_security_logout"><i class="fa fa-power-off fa-fm"></i>Вийти</a>
                    </li>

                </ul>
            </div>
        </security:authorize>

        <div style="margin-top: 10px; height: 500px;" id="place-info-panel"
             class="col col-xs-12 col-md-5 <security:authorize access="isAnonymous()">col-md-offset-1</security:authorize> thumbnail">
            <div>
                <img src="<c:url value="/photo/place/${place.id}/main"/>" width="100%" onerror="http://placehold.it/160x90">
                <table class="table">
                    <tr>
                        <td>
                            <h3>${place.placeName}</h3>
                            <a style="color:white; cursor:pointer" id="like">
                                    <span class="badge">
                                                    <i id="heart"
                                                       class="fa fa-fw
                                                       <c:if test="${liked eq 0}">fa-heart-o "</c:if>
                                                       <c:if test="${liked eq 1}">fa-heart"  </c:if> >
                                        </i>
                                        <span id="place-f-n">${place.placeUsers.size()}</span>
                                    </span>
                            </a>
                        </td>
                        <td>
                            <div style="float:right">
                                <span class="rating">
                                    <c:forEach step="1" varStatus="pr" begin="1"
                                               end="5">
                                        <c:if test="${place.placeFinalRating >= rm.index}">
                                            <i class="fa fa-star  rating-star"></i>
                                        </c:if>
                                        <c:if test="${place.placeFinalRating < rm.index}">
                                            <i class="fa fa-star-o  rating-star"></i>
                                        </c:if>
                                    </c:forEach>
                                </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: justify">${place.placeDescription}</td>
                    </tr>
                </table>
            </div>
        </div>

        <div style="padding: 4px; height:500px; margin-top: 10px;" id="address-panel"
             class="col col-xs-12 col-md-5 thumbnail">
            <div id="address-wrapper" style=" width:100%; height:100%">
                <div id="map_canvas" style=" width:100%; height:100%"></div>
                <input id="latitude" type="hidden"/>
                <input id="longitude" type="hidden"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col col-xs-12 col-md-10
        <security:authorize access="isAnonymous()">col-md-offset-1"</security:authorize>
             <security:authorize access="isAuthenticated()">col-md-offset-2
        "</security:authorize>
        id="masonry" data-columns>
        <c:forEach items="${place.placeMenu}" var="menu" varStatus="m">
            <div>
                <c:url value="/place/${place.id}/menu/${menu.id}" var="neworder"/>
                <form method="POST" action="${neworder}">
                    <div class="thumbnail">
                        <img width="100%" src="<c:url value="/photo/menu/${menu.id}/small"/>"
                             onerror="http://placehold.it/250x200"/>

                        <div class="caption">
                            <table style="width:100%">
                                <tr id="first-row">
                                    <td>
                                        <h3 class="service-name">${menu.menuName}</h3>

                                    </td>
                                    <td>
                                        <span class="badge">${menu.menuPrice}грн.<i class="fa fa-tags"></i></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="tabs">
                                            <ul class="nav nav-tabs">
                                                <li style="width: 33%" class="active">
                                                    <a href="#description-tab-${m.index}" data-toggle="tab">
                                                        <i class="fa fa-info">
                                                            <span style="margin-right: 4px"></span> Опис
                                                        </i>
                                                    </a>
                                                </li>
                                                <li style="width: 67%; padding-right:5px">
                                                    <a href="#options-tab-${m.index}" data-toggle="tab">
                                                        <i class="fa fa-gift">
                                                            <span style="margin-right: 4px"></span> Опції
                                                        </i>
                                                    </a>
                                                </li>
                                            </ul>
                                            <div class="tab-content">

                                                <div style="border-top:0"
                                                     class="tab-pane description-wrapper caption active fade in"
                                                     id="description-tab-${m.index}">
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <p>
                                                                        ${menu.menuDescription}
                                                                </p>
                                                            </td>
                                                        </tr>
                                                        <tr style="vertical-align: middle">
                                                            <td width="80%">
                                                                <span class="rating">
                                                                    <c:forEach step="1" varStatus="rm" begin="1"
                                                                               end="5">
                                                                        <c:if test="${menu.menuFinalRating >= rm.index}">
                                                                            <i class="fa fa-star  rating-star"></i>
                                                                        </c:if>
                                                                        <c:if test="${menu.menuFinalRating < rm.index}">
                                                                            <i class="fa fa-star-o  rating-star"></i>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>

                                                <div style="border-top: 0; margin-bottom: 2px; padding: 5px"
                                                     class="tab-pane thumbnail fade comment-wrapper"
                                                     id="options-tab-${m.index}">
                                                    <table class="table">
                                                        <c:forEach items="${menu.services}" var="service"
                                                                   varStatus="s">
                                                            <tr style="vertical-align: middle">
                                                                <td width="80%">
                                                                    <input type="checkbox" name="services"
                                                                           value="${service.id}"
                                                                           id="service-${s.index}"
                                                                           style="margin-top: 15px">
                                                                    <label for="service-${s.index}">${service.description}</label>
                                                                </td>
                                                                <td width="20%" class="service-description">
                                                                    <span style="display: block;"
                                                                          class="badge">${service.price}
                                                                        .грн<i class="fa fa-tag"></i></span></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div style="float: right; margin: 5px 0 1px 0;" class="btn-group">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fa fa-hand-scissors-o fa-rotate-90 fa-fw"></i>
                                                Замовити
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                        </div>
                    </div>
                </form>
            </div>

        </c:forEach>
    </div>
</div>

<script type="text/javascript">
    var geocoder;
    var map;
    var marker;
    var latlng = new google.maps.LatLng(${place.address.latitude}, ${place.address.longitude});
    var liked;

    $(document).ready(function () {

        <security:authorize access="isAuthenticated()">
        liked = ${liked};
        $("#like").click(function () {
            like("${place.id}");
        });
        </security:authorize>

        initialize();
        geocoder.geocode({'location': latlng}, function (results, status) {
            var infowindow = new google.maps.InfoWindow;
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    map.setZoom(15);
                    var marker = new google.maps.Marker({
                        position: latlng,
                        map: map
                    });
                    infowindow.setContent(results[0].formatted_address);
                    infowindow.open(map, marker);
                } else {
                    window.alert('No results found');
                }
            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    });

    <security:authorize access="isAuthenticated()">
        function like(placeId) {
        liked %= 2;
        $.ajax({
            url: "/place/" + placeId + "/liked/",
            method: "GET"
        }).success(function (data) {
            is = liked % 2;
            if (is == 0) {
                $("#heart").removeClass("fa-heart-o");
                $("#heart").addClass("fa-heart");
            }
            if (is == 1) {
                $("#heart").addClass("fa-heart-o");
                $("#heart").removeClass("fa-heart");
            }
            liked += 1;
            $("#place-f-n").text(data);
        });
    }
    </security:authorize>

    function initialize() {
//Определение карты
        var options = {
            zoom: 15,
            center: latlng
        };

        map = new google.maps.Map(document.getElementById("map_canvas"), options);

        //Определение геокодера
        geocoder = new google.maps.Geocoder();

        marker = new google.maps.Marker({
            map: map,
            draggable: false,
            position: latlng
        });

    }


</script>
<script src="<c:url value="/static/js/salvattore.min.js"/>"></script>

</body>
</html>