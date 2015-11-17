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

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->

    <link href="<c:url value="/static/css/pages/user-place-list.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/pages/registration.css"/>" rel="stylesheet"/>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/static/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/static/js/bootstrap.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
    <style>
        .place-btn {
            background-color: transparent;
            color: white;
        }

        .place-btn:hover {
            background-color: rgba(191, 234, 191, 0.2);
            color: #ddd;
        }
    </style>
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


<div style="margin-top:60px" class="container">
    <div class="row">
        <div class="col col-md-2 hidden-sm hidden-xs">
            <c:if test="${user eq null}">
                <ul class="list-group" id="user-controls">

                    <li class="list-group-item">
                        <img id="user-photo" class="img-responsive"
                             <c:if test="${user.photos.size()==0}">src="http://placehold.it/100x100"</c:if>
                                <c:if test="${user.photos ne null}"> src="user/${user.id}/photo/main"</c:if>
                                />
                    </li>
                    <li class="list-group-item">
                        <a href="#"><i class="fa fa-user fa-fm"></i><c:out value="${user.userName}"/></a>
                    </li>
                    <li class="list-group-item"><a href="/user/orders">
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
            </c:if>
        </div>
        <div class="masonry" data-columns="">
            <c:forEach items="${places}" varStatus="i" var="place">
                <div style="text-align: center;box-shadow: 2px 3px 8px;">
                    <img class="place-img" src="/photo/place/${place.id}/small"
                         width="100%" height="100%" onerror="http://placehold.it/160x90"/>
                    <a class="btn btn-default place-btn" href="/place/${place.id}"
                       style="margin-top:-100px">${place.placeName}</a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<script src="/static/js/salvattore.min.js"></script>
</body>
</html>
