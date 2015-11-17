<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fs" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Registration</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/pages/registration.css"/>" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">

        #authorization-password {
            margin-left: -115px;
            left: 50%;
        }

        #authorization-email {
            margin-left: -115px;
            left: 50%;
        }

        .registration-pointers {
            font-size: 8em;
        }

        .registration-info {
            display: inline;
        }

        .input-group-addon {
            padding: 0 5px 0 10px;
            width: 30px;
        }

        .input-group {
            text-align: left;
            left: 50%;
            margin-left: -105px;
        }

        table {
            align-content: center;
        }

        .thumbnail {
            margin-top: 40px;
            height: 420px;
            text-align: center;
            border-radius: 50px;
            padding: 10px 50px 10px 50px;
            overflow: hidden;
        }

        #user-info {
            margin-top: 23px;
        }

        #user-detail-info {
            height: 92.6px;
        }

        #file-input {
            left: 50%;
            margin-left: -151px;
            background-color: #dddddd;
        }

        #register-btn {
            display: block;
            position: relative;
            left: 50%;
            margin-left: -90.7px;
            margin-bottom: 20px;
        }

        #input-number {
            display: inline;
            width: 103px;
            margin-left: -2px;
        }

        #input-sex {
            color: #555;
            text-align: center;
            background-color: #eee;
            border: 1px solid #ccc;
            padding: 2px 25px 2px 2px;
            border-bottom-left-radius: 4px;
            border-top-left-radius: 4px;
        }

        .ui-autocomplete {
            background-color: white;
            width: 300px;
            border: 1px solid #cfcfcf;
            list-style-type: none;
            padding-left: 0;
        }

        #map_canvas {
            border-radius: 50px;
        }

        #address-wrapper {
            padding: 1px;
        }

        #address {
            position: absolute;
            z-index: 9;
            margin: 60px 0 0 -85px;
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
                        <a href="/registrater/owner">Приєднатись</a>
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
    <sf:form action="/registration" modelAttribute="user" id="registration-form" method="post">
        <div class="row">

            <!-- Область авторизации -->

            <div class="col-xs-12 col-md-6 col-lg-4">
                <div id="user-credentials" class="thumbnail">
                    <i class="fa fa-user-secret registration-pointers"></i>
                    <table class="table" border="0">
                        <tr>
                            <td>
                                <c:set var="emailError"><sf:errors path="userEmail"/></c:set>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope-o"></i></span>
                                    <sf:input path="userEmail" id="userEmail"
                                              title='${emailError}' placeholder="Email"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:set var="nameError"><sf:errors path="userName"/></c:set>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user"></i> </span>
                                    <sf:input path="userName" id="userName" title="${nameError}" placeholder="Username" name="name"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <c:set var="passError"><sf:errors path="userPass"/></c:set>
                                    <span class="input-group-addon"><i class="fa fa-key"></i></span>
                                    <sf:password path="userPass" title="${passError}" placeholder="Password" id="userPass" name="pass"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-key"></i></span>
                                    <c:set var="nameError"><sf:errors path="userName"/></c:set>
                                    <sf:password path="userPassConf" placeholder="Confirm"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="alert alert-success" id="user-detail-info">
                        <i class="fa fa-info"></i> Поля цієї групи є обов'язковими для заповнення
                    </div>

                </div>
            </div>

            <!-- Область аддрессов -->

            <div class="col-xs-12 col-md-6 col-lg-4">
                <div class="thumbnail" id="address-wrapper">
                    <input placeholder="Address" type="text" id="address"/>

                    <div id="map_canvas" style="width:100%; height:100%"></div>
                    <sf:hidden path="address.latitude" id="latitude"/>
                    <sf:hidden path="address.longitude" id="longitude"/>
                </div>
            </div>

            <!-- Область персональной инф. -->

            <div class="col-xs-12 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-0">
                <div class="thumbnail">
                    <i class="fa fa-users registration-pointers"></i>
                    <table class="table" border="0">
                        <tr>
                            <td colspan="2">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-info"></i></span>
                                    <sf:input path="name" placeholder="Name" type="text"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-info"></i></span>
                                    <fs:input path="sname" placeholder="Sname" type="text"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <sf:select path="sex" id="input-sex" name="gender">
                                        <sf:option value="Man"/>
                                        <sf:option value="Woman"/>
                                    </sf:select>

                                    <sf:input path="age" id="input-number" placeholder="Age"/>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <div id="file-input" class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-image"></i></span>
                                    <input type="file"/>
                                </div>
                            </td>
                        </tr>

                    </table>

                    <div class="alert alert-info" id="user-info">
                        <i class="fa fa-eye"></i>

                        <p class="registration-info">Інформація буде доступна лиша закладам яким ви надіслали
                            замовлення</p>
                    </div>
                </div>
            </div>

        </div>
        <div class="row">
            <button id="register-btn" class="btn btn-primary btn-lg">Зареєструватись</button>
        </div>
    </sf:form>

</div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/static/js/bootstrap.js"/>"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="/static/js/jquery-ui-1.8.1.custom.min.js"></script>

<script type="text/javascript">
    var geocoder;
    var map;
    var marker;

    var userName = $("#userName");
    var userEmail = $("#userEmail");
    var userPass = $("#userPass");
    var userPassConf = $("#userPassConf");
    function initialize() {
//Определение карты
        var latlng = new google.maps.LatLng(50.4501, 30.523400000000038);
        var options = {
            zoom: 15,
            center: latlng,
        };

        map = new google.maps.Map(document.getElementById("map_canvas"), options);

        //Определение геокодера
        geocoder = new google.maps.Geocoder();

        marker = new google.maps.Marker({
            map: map,
            draggable: true
        });

    }

    function submitFails(parameters) {
        var element = parameters.element;
        var message = parameters.message;
        element.css("border", "1px solid red");
        if (message != null) {
            element.prop("placeholder", message);
        }
        element.focus();
        return false;
    }

    $(document).ready(function () {

        initialize();

        $(function () {
            $("#address").autocomplete({
                //Определяем значение для адреса при геокодировании
                source: function (request, response) {
                    geocoder.geocode({'address': request.term}, function (results, status) {
                        response($.map(results, function (item) {
                            return {
                                label: item.formatted_address,
                                value: item.formatted_address,
                                latitude: item.geometry.location.lat(),
                                longitude: item.geometry.location.lng()
                            }
                        }));
                    })
                },
                //Выполняется при выборе конкретного адреса
                select: function (event, ui) {
                    $("#latitude").val(ui.item.latitude);
                    $("#longitude").val(ui.item.longitude);
                    var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                    marker.setPosition(location);
                    map.setCenter(location);
                }
            });


            $("#registration-form").submit(function validateData() {

                if (userName.val() == "") {
                    return submitFails({element: userName, message: "Wrong username"});
                }
                if (userPass.val() != userPassConf.val()) {
                    submitFails({element: userPass, message: "Password don not mutch"});
                    return submitFails({element: userPassConf});
                }
                if (userEmail.val() == "") {
                    return submitFails({element: userEmail, message: "Wrong email"})
                }
                return true;
            });

            userName.keypress(function check() {
                if (userName.val() != "") {
                    userName.css("border", "border: 2px inset");
                }
            });
            userEmail.keypress(function check() {
                if (userEmail.val() != "") {
                    userEmail.css("border", "border: 2px inset");
                }
            });
            userPass.keypress(function check() {
                if (userPass.val() != "" && userPass.val() == userPassConf) {
                    userPass.css("border", "border: 2px inset");
                }
            });
            userPassConf.keypress(function check() {
                if (userPassConf.val() != "" && userPass.val() == userPassConf) {
                    userName.css("border", "border: 2px inset");
                }
            });

        });

        //Добавляем слушателя события обратного геокодирования для маркера при его перемещении
        google.maps.event.addListener(marker, 'drag', function () {
            geocoder.geocode({'latLng': marker.getPosition()}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        $('#address').val(results[0].formatted_address);
                        $('#latitude').val(marker.getPosition().lat());
                        $('#longitude').val(marker.getPosition().lng());
                    }
                }
            });
        });


    });

</script>
</body>
</html>