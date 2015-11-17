<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 12.10.2015
  Time: 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ns" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="../../../webapp/static/themes/bootstrap.css" rel="stylesheet"/>
    <link href="../../../webapp/static/css/pages/user-place-list.css" rel="stylesheet"/>
    <link href="../../../webapp/static/themes/font-awesome.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
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
                        <ul id="user-menu-dropdown" class="list-group dropdown-menu">
                            <li id="menu-item-my-page" class="list-group-item"><a href="#"><i
                                    class="fa fa-user fa-fm"></i>Моя
                                сторінка</a></li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-bullhorn fa-fm"></i>Мої
                                замовлення</a>
                            </li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-calendar-check-o fa-fm"></i>Мої
                                події</a></li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-cutlery fa-fm"></i>Мої заклади</a>
                            </li>
                            <li class="list-group-item"><a href="#"><i class="fa fa-power-off fa-fm"></i>Вийти</a></li>
                        </ul>
                    </li>

                    <li>
                        <a href="MyPlaceToGo/registrater/owner">Приєднатись</a>
                    </li>
                </security:authorize>

                <security:authorize access="isAnonymous()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Увійти<b class="caret"></b></a>
                        <ul id="enter-form" class="dropdown-menu navbar-form">
                            <li>
                                <span id="authorization-span"></span>

                                <form action="/login" id="authorization-form" method="POST">

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
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-search fa-fw"></i></span>
                    <input class="form-control" type="text" placeholder="search stuff">
                </div>
            </form>


        </div>
    </div>
</div>


<div class="container">
    <div class="row">
        <div class="col col-md-2 hidden-sm hidden-xs hidden-md">
            <ul class="list-group" id="user-controls">

                <li class="list-group-item">
                    <img id="user-photo" class="img-responsive" src="user/${user.id}/photo/main"
                         onerror="http://placehold.it/100x100"/>
                </li>
                <li class="list-group-item">
                    <a href="#"><i class="fa fa-user fa-fm"></i><c:out value="${user.userName}"/></a>
                </li>
                <li class="list-group-item"><a href="/user/${user.id}/userOrderses">
                    <i class="fa fa-bullhorn fa-fm"></i>Мої замовлення</a>
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

        <div class="col col-xs-12 col-lg-10">
            <div class="container">
                <div class="row masonry" data-columns="">
                    <c:forEach items="${user.userPlaces}" varStatus="i" var="place">
                        <div>
                            <div class="panel panel-default" id="places-list">
                                <div class="panel-groupe">
                                    <div class="panel panel-default place-panel">
                                        <div class="panel-heading">
                                            <img class="place-img" src="/place/${place.id}/photo/small"
                                                 onerror="http://placehold.it/100x100"/>

                                            <h2 class="place-name-exp">
                                                <a href="/place/${place.id}}"><c:out value="${place.placeName}"/></a>
                                                <i class="fa fa-cultery"></i>
                                            </h2>

                                            <p>
                                                <c:out value="${place.placeDescription}"/>
                                            </p>

                                            <div class="btn-group btn-place-dropdown btn-right">
                                                <a class="btn btn-primary" data-toggle="collapse" href="#place-${i}">
                                                    Показати події
                                                </a>
                                                <a class="btn btn-primary">
                                                    <span class="badge">2</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="collapse" id="place-${i}">
                                        <div class="panel-body">
                                            <c:forEach items="${place.placeEvents}" var="event" varStatus="i">
                                                <table class="table table-striped">
                                                    <tr>
                                                        <td rowspan="2">
                                                            <img class="event-image"
                                                                 src="/event/${event.id}/photo/small"
                                                                 onerror="http://placehold.it/100x100"/>
                                                        </td>
                                                        <td>
                                                            <h3 class="event-name">${event.eventName}</h3>
                                                        </td>
                                                        <td>
                                                            <p class="event-followers"><span
                                                                    class="badge">${ns:length(event.users.size())}</span></p>
                                                        </td>
                                                    </tr>

                                                        <tr>
                                                            <td>
                                                                <a href="/event/${event.id}/edit"><i
                                                                        class="fa fa-cog"></i>Змінити</a>
                                                            </td>
                                                            <td>
                                                                <i class="fa fa-cog"></i>Видалити
                                                            </td>
                                                        </tr>

                                                </table>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../../../webapp/static/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../../webapp/static/js/bootstrap.js"></script>

<script src="../../../webapp/static/js/salvattore.min.js"></script>
</body>
</html>
