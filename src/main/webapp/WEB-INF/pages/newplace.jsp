<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="/static/css/bootstrap.css" rel="stylesheet"/>
    <link href="/static/css/font-awesome.css" rel="stylesheet"/>
    <link href="/static/css/jquery.Jcrop.css" rel="stylesheet"/>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->


    <style type="text/css">
        #photo-input-wrapper {
            height: 420px;
        }

        #image {
            max-width: 100%;
            max-height: 90%;
            display: block;
            margin-left: auto;
            margin-right: auto
        }

        #upload {
            position: relative;
            background-color: #ddd;
            margin: 8px 0 0 -132px;
            left: 50%;
        }

        .registration-pointers {
            font-size: 8em;
        }

        .registration-info {
            display: inline;
        }

        .input-group-addon {
            padding: 0px 0px;
            width: 30px;
        }

        .input-group {
            text-align: left;
            left: 50%;
            margin-left: -102px;
            width: 204px;
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

        #address-info {
            margin-top: 62px;
        }

        #user-info {
            margin-top: 23px;
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

        .ui-autocomplete {
            background-color: white;
            width: 300px;
            border: 1px solid #cfcfcf;
            list-style-type: none;
            padding-left: 0px;
        }

        #map_canvas {
            border-radius: 25px;
        }

        #address-wrapper {
            padding: 1px;
        }

        #address {
            position: absolute;
            z-index: 9;
            margin: 60px 0 0 -85px;
        }

        #place_description {
            resize: none;
        }

    </style>

</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a href="#" class="navbar-brand"></a>
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

                                <form action="/j_spring_security_check" id="authorization-form" method="POST">

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
    <sf:form modelAttribute="place" action="/newplace" method="post" enctype="multipart/form-data">
        <div class="row">
            <div class="col-xs-12 col-md-6 col-lg-4">
                <div id="user-credentials" class="thumbnail">
                    <span id="main-pointer"> <i class="fa fa-hand-peace-o registration-pointers"></i></span>
                    <table class="table" border="0">
                        <tr>
                            <td>
                                <div class="input-group input-group">
                                    <span class="input-group-addon" id="basic-addon1">
                                       <i class="glyphicon glyphicon-apple"></i>
                                     </span>
                                    <sf:input path="name" id="form-name" class="form-control"
                                              placeholder="Назва" value=""
                                              aria-describedby="basic-addon1"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span id="type_addon" class="input-group-addon"><i
                                            class="glyphicon glyphicon-glass"></i></span>
                                    <sf:select path="specialization" id="place_type" class="form-control">
                                        <sf:option value="NightClub">Night club</sf:option>
                                        <sf:option value="Sport"/>
                                        <sf:option value="Cafe"/>
                                    </sf:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <sf:textarea path="description" class="form-control"
                                                 placeholder="Короткий опис закладу"
                                                 id="place_description"/>
                                </div>
                                <small id="place_description-addon">Залишилося 160 символів</small>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <sf:input path="photo.image" type="file" id="upload" name="image"/>
                                    <sf:hidden path="photo.x" id="x"/>
                                    <sf:hidden path="photo.y" id="y"/>
                                    <sf:hidden path="photo.h" id="h"/>
                                    <sf:hidden path="photo.w" id="w"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="col col-xs-4" style="width:390px; padding: 0 15px; margin-top: 40px; border-radius: 4px; display: inline">
                <div style="border: #dddddd 1px solid; padding: 4px;"
                     id="image-holder">
                    <img style="min-width: 349px; max-width: 349px" src="http://placehold.it/433x300" id="image"/>
                </div>
                <div style="margin-top: 20px" class="alert alert-success" id="user-detail-info">
                    <i class="fa fa-info"></i> Поля цієї групи є обов'язковими для заповнення
                </div>
            </div>


            <div class="col-xs-12 col-md-6 col-lg-4">
                <div class="thumbnail" id="address-wrapper">
                    <sf:input path="address.fullAddress" id="address"/>

                    <div id="map_canvas" style="width:100%; height:100%"></div>
                    <sf:input path="address.latitude" id="latitude"/>
                    <sf:input path="address.longitude" id="longitude"/>
                </div>
            </div>
        </div>
        <div class="row">
            <input type="submit" id="register-btn" class="btn btn-primary btn-lg"/>
        </div>
    </sf:form>
</div>


<script type="text/javascript" src="/static/js/jquery.min.js"></script>

<script type="text/javascript" src="/static/js/jquery-ui-1.8.1.custom.min.js"></script>

<script type="text/javascript" src="/static/js/jquery.Jcrop.min.js"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/static/js/bootstrap.js"></script>


<script type="text/javascript">

    var geocoder;
    var map;
    var marker;

    var mIndex;
    var target = document.getElementById("image");
    var src = document.getElementById("upload");
    var jcropApi;
    <!-- Display map API ---------------------------- -->

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

    $(document).ready(function () {

        initialize();
        showImage(src, target);
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

        $("#warning-sighn").hide();

        var descriptionField = $("#place_description");
        var descriptionFieldAddon = $("#place_description-addon");
        descriptionField.keypress(function () {
            updateDescriptionSpan();
        });
        descriptionField.keydown(function () {
            updateDescriptionSpan();
        });
        descriptionField.change(function () {
            updateDescriptionSpan();
        });

        var type_addon = $("#type_addon");
        var place_type = $("#place_type");

        function updateDescriptionSpan() {
            descriptionFieldAddon.text(function () {
                var i = 160 - descriptionField.val().length;
                if (i >= 0) {
                    $("#warning-sighn").hide();
                    descriptionFieldAddon.css("backgroundColor", "#EEE");
                    descriptionFieldAddon.css("color", "#555");
                    return "Залишилося " + i + " символів";
                }
                if (i < 0) {
                    $("#warning-sighn").show();
                    descriptionFieldAddon.css("backgroundColor", "#F2DEDE");
                    descriptionFieldAddon.css("color", "#A94442");
                    return "Ви перевищили дозволену кількість символів символів";
                }


            });
        }

        place_type.change(function () {
            $("#main-pointer").html(function (i, originText) {
                switch (place_type.val()) {
                    case "Sport":
                        return '<i class="fa fa-futbol-o registration-pointers"></i>';
                    case "Cafe":
                        return '<i class="fa fa-coffee registration-pointers"></i>';
                    case "NightClub":
                        return '<i class="fa fa-glass registration-pointers "></i>';
                }
            });
        });

    });

    <!-- Description counter -->


    <!-- Description counter -->

    function validateTextInput(c) {
        return !(c.val() == null || c.val() == "");
    }
    function validatePhoto(c) {

    }

    function showCoords(c) {
        $('#x').val(c.x*mIndex);
        $('#y').val(c.y*mIndex);
        $('#w').val(c.w*mIndex);
        $('#h').val(c.h*mIndex);
    }

    function initJcrop(img) {
        jQuery(img).Jcrop({
                    aspectRatio: 16 / 9,
                    onChange: showCoords,
                    setSelect: [0, 90, 160, 0],
                    onSelect: showCoords
                },
                function(){
                });
    }


    function showImage(src, target) {
        var fr = new FileReader();
        // when image is loaded, set the src of the image where you want to display it
        fr.onload = function (e) {
            $(".jcrop-holder").remove();
            var imga =$('#image');
            imga.attr("src", this.result);
            imga.load(function(){
                console.log(this.width);
                mIndex=this.width/351   ;
                console.log(mIndex)
            });
            initJcrop(target);
        };

        src.addEventListener("change", function () {
            // fill fr with image data
            fr.readAsDataURL(src.files[0]);
            showImage(src, target);
        });
    }


</script>

</body>
</html>
