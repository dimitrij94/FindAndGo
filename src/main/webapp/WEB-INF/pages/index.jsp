<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>My Place TO Go</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/static/themes/bootstrap.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/themes/font-awesome.css"/>" rel="stylesheet"/>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/static/js/bootstrap.js"/>"></script>

    <script src="<c:url value="/static/js/salvattore.min.js"/>"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->

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
                            <li class="list-group-item"><a href="/user/places"><i class="fa fa-cutlery fa-fm"></i>Мої заклади</a>
                            </li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-power-off fa-fm"></i>Вийти</a></li>
                        </ul>
                    </li>
                    <security:authorize access="hasRole('PLACE_OWNER')">
                        <li>
                            <a href="<c:url value="/newplace"/>">Приєднатись</a>
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


<c:if test="${events ne null}">
    <div class="carousel slide" id="carousel">

        <!-- Carousel slides indicators-->
        <ol class="carousel-indicators">
            <c:forEach items="${events}" var="event" varStatus="e">
                <c:if test="${e.index eq 0}">
                    <li class="active" data-target="#carousel" data-slide="${i}"></li>
                </c:if>
                <li data-target="#carousel" data-slide="${e.index}"></li>
            </c:forEach>
        </ol>
        <!-- Slides-->
        <div class="carousel-inner">

            <c:forEach items="${events}" var="event" varStatus="ev">

                    <div class="item <c:if test="${ev.index eq 0}"> active</c:if>">
                        <img src="${event.eventPhoto.url}" onerror="placeholder.it/1920x400"
                             alt="${event.eventName} photo"/>

                        <div class="carousel-caption">
                            <h3>${event.eventName}</h3>

                            <p>${event.eventDescription}у</p>
                        </div>
                    </div>
            </c:forEach>

        </div>
        <a class="left carousel-control" href="#carousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
        </a>
        <a class="right carousel-control" href="#carousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
        </a>

    </div>
</c:if>

<div class="container-flex">
    <div class="row">
        <div class="navbar navbar-default" id="category-tabs">
            <div class="container">
                <div class="navbar-header">

                    <button class="navbar-toggle" data-toggle="collapse" data-target="#category-navbar">
                        <span class="sr-only">Відкрити меню</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="category-navbar">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="#club-tab" data-toggle="tab">
                                <i class="fa fa-coffee fa-2x"><span style="padding-right: 8px"></span></i>

                                <h3 class="menu-title">Кафе</h3>
                            </a>
                        </li>

                        <li>
                            <a href="#hotel-tab" data-toggle="tab">
                                <i class="fa fa fa-bed fa-2x"><span style="padding-right: 8px"></span></i>

                                <h3 class="menu-title">Готелі</h3>
                            </a>
                        </li>
                        <li>
                            <a href="#club-tab" data-toggle="tab">
                                <i class="fa fa-glass fa-2x"><span style="padding-right: 8px"></span></i>

                                <h3 class="menu-title">Клуби</h3>
                            </a>
                        </li>
                        <li>
                            <a href="#sport-tab" data-toggle="tab">
                                <i class="fa fa-futbol-o fa-2x"><span style="padding-right: 8px"></span></i>

                                <h3 class="menu-title">Спорт</h3>
                            </a>
                        </li>
                        <li>
                            <a href="#sport-tab" data-toggle="tab">
                                <i class="fa fa-cutlery fa-2x"><span style="padding-right: 8px"></span>
                                </i>

                                <h3 class="menu-title">Ресторани</h3>
                            </a>
                        </li>
                        <li>
                            <a href="#club-tab" data-toggle="tab">
                                <i class="fa fa-music fa-2x"><span style="padding-right: 8px"></span>

                                    <h3 class="menu-title">Концерти</h3></i>
                            </a>
                        </li>
                        <li>
                            <a href="#club-tab" data-toggle="tab">
                                <i class="fa fa-cogs fa-2x"><span style="padding-right: 8px"></span>

                                    <h3 class="menu-title">Сервіс</h3></i>
                            </a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>


<div class="container">
    <div class="row masonry" data-columns>
        <jsp:useBean id="places" scope="request" type="java.util.List<com.example.domain.Place>"/>
        <c:forEach items="${places}" var="place" varStatus="i">
            <div class="cell">
                <div class="thumbnail">
                    <img class="img-responsive" onerror="http://placehold.it/600x340"
                         src="/photo/place/${place.id}/small" alt="">

                    <table class="table ">
                        <tr>
                            <td>
                                <h3>${place.placeName}</h3><span class="badge">${place.placeOwner.userName}</span>
                            </td>
                            <td>
                                <div>
                                        <span style="float: right; margin-top: 12px; font-size: 20px;" class="rating">
                                            <c:forEach varStatus="rp" begin="0" end="5" step="1">
                                                <c:if test="${place.placeFinalRating>rp.index}">
                                                    <i class="fa fa-star rating-star"></i>
                                                </c:if>
                                                <c:if test="${place.placeFinalRating<rp.index}">
                                                    <i class="fa fa-star-o rating-star"></i>
                                                </c:if>
                                            </c:forEach>
                                        </span>
                                </div>
                            </td>
                        </tr>
                    </table>

                    <div class="tabs">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#description-tab-${i}" data-toggle="tab">
                                    <i class="fa fa-info">
                                        <span style="margin-right: 4px"></span> Опис
                                    </i>
                                </a>
                            </li>
                            <li>
                                <a href="#comments-tab-${i}" data-toggle="tab">
                                    <i class="fa fa-comments-o">
                                        <span style="margin-right: 4px"></span> Коментарі
                                    </i>
                                </a>
                            </li>
                        </ul>
                        <div class="tab-content">

                            <div class="tab-pane description-wrapper caption active fade in"
                                 style="border-top: 0;" id="description-tab-${i}">
                                <c:out value="${place.placeDescription}"/>
                            </div>

                            <div class="tab-pane fade comment-wrapper" id="comments-tab-${i}">
                                <c:forEach items="${place.placeComments}" var="comment">
                                    <div class="comment">
                                        <i class="fa fa-user" style="margin-right: 4px">
                                            <span style="margin-right: 4px"></span>
                                            <a href="#"><b>${comment.commentor.userName}</b></a>
                                        </i>
                                        <i>${comment.commentBody}</i>
                                        <a href="#">
                                            <i class="fa fa-thumbs-o-up like-ikon">
                                                <span style="margin-right: 2px"></span>
                                                Yes!
                                            </i>
                                        </a>

                                    </div>
                                </c:forEach>
                            </div>

                            <a href="/place/${place.id}" class="btn btn-success">
                                <i class="fa fa-arrow-right"></i> It`s my place
                            </a>

                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="<c:url value="/static/js/salvattore.min.js"/>"></script>
</body>
</html>