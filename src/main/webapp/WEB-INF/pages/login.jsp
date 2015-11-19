<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Login page</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
    <style>
        html, body, .container, .row {
            height: 100%;
        }

        #enter-main-form {
            top: 40%;
            margin-top: -75px;
        }

        #form-btn-group {
            left: 50%;
            width:224px;
            margin: 7px 0 1px -112px;
        }

        #form-btn-group li {
            padding: 0 5px;
        }

        .line {
            margin: 10px 0 2px 0px;
            width: 100%;
            border-bottom: 1px solid #d4d4d4;
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
                            <li class="list-group-item"><a href="/user/places"><i class="fa fa-cutlery fa-fm"></i>Мої заклади</a>
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



<div class="container">
    <div class="row">
        <div id="enter-main-form" class="col
        col-xs-10 col-xs-offset-1
        col-md-4 col-md-offset-4">
            <div class="thumbnail">
                <form action="/login"  method="post">
                    <div class="input-group" id="authorization-form-email">
                        <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
                        <input class="form-control" name="j_username" type="email" placeholder="E-mail">
                    </div>
                    <br>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                        <input class="form-control" name="j_password" type="password" placeholder="Password">
                    </div>

                    <div class="line"></div>

                    <div id="form-btn-group" class="btn-group" role="group">

                        <a href="#" class="btn btn-default authentication-button">
                            <i class="fa fa-user-plus"><span
                                    style="padding-right: 4px"></span>Реестрація</i>
                        </a>

                        <div class="btn-group">

                            <button type="submit" class="btn btn-default authentication-button">
                                <i class="fa fa-circle-thin"><span style="padding-right: 4px"></span>Увійти</i>
                            </button>

                            <button class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li style="vertical-align: middle">
                                    <input style="margin:0 0 3px 0" type="checkbox" name="j_remember" id="remember" class="btn btn-defoult"/>
                                    <label style="margin-bottom: 0" for="remember">Запам'ятати</label>
                                </li>
                            </ul>
                        </div>
                    </div>


                </form>
            </div>
            <c:if test="${!(SPRING_SECURITY_LAST_EXCEPTION.message==null||SPRING_SECURITY_LAST_EXCEPTION.message=='')}">
                <div class="alert alert-danger valid-warning" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>

                    Your login attempt was not successful due to <br/><br/>
                    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </div>
            </c:if>

        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/static/js/bootstrap.js"/>"></script>

</body>
</html>