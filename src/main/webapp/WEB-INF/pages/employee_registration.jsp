<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 11.12.2015
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><spring:message message=""/></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/jquery.Jcrop.css"/>" rel="stylesheet"/>
    <!-- Bootstrap -->
    <link href="<c:url value="/static/bower/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/bower/ion.rangeslider/css/ion.rangeSlider.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/bower/ion.rangeslider/css/ion.rangeSlider.skinHTML5.css"/>" rel="stylesheet"/>

    <script type="text/javascript" src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/static/js/jquery-ui-1.8.1.custom.min.js"/>"></script>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<c:url value="/static/bower/moment/min/moment-with-locales.min.js"/>"></script>
    <script src="<c:url value="/static/js/jquery.Jcrop.min.js"/>"></script>
    <script src="<c:url value="/static/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/static/bower/ion.rangeslider/js/ion.rangeSlider.min.js"/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
    <style>
        .jcrop-centered {
            left: 50%;
        }

        .input-group {
            width: 100%;
        }

        #nav-login-form tr td {
            padding: 5px 0 0 0;
        }

        #nav-login-form tr td input:-moz-placeholder {
            padding-left: 5px;
        }

        #nav-login-form tr td input::-webkit-input-placeholder {
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a href="#" class="navbar-brand"><spring:message code="project.name"/></a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responcive-menu">
                <span class="sr-only"><spring:message code="header.expand.sr"/> </span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="responcive-menu">
            <form class="form" id="formLogin">
                <ul class="nav navbar-nav">
                    <li><a href="#"><spring:message code="header.nav.home"/> </a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">
                            <spring:message code="header.nav.profile"/>
                            <span class="caret"></span></a>
                        <ul id="user-menu-dropdown"  role="menu" class="dropdown-menu">
                            <li id="menu-item-my-page"><a href="#"><i class="fa fa-user fa-fm"></i>Profile</a></li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-bullhorn fa-fm"></i>
                                    <spring:message code="header.nav.profile.orders"/>
                                </a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-cutlery fa-fm"></i>
                                    <spring:message code="header.nav.profile.places"/>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-power-off fa-fm"></i>
                                    <spring:message code="header.nav.profile.logout"/>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">
                            <spring:message code="header.nav.login"/>
                            <span class="caret"></span>
                        </a>

                        <ul class="dropdown-menu" style="width:230px; padding: 15px">
                            <li>
                                <input name="username" id="username" class="form-control" type="text"
                                       placeholder="<spring:message code="form.login.placeholder.username"/> ">
                            </li>
                            <li>
                                <input name="password" id="password" class="form-control" type="password"
                                       placeholder="<spring:message code="form.login.placeholder.password"/> ">
                            </li>
                            <li style="background-color: white" class="divider"></li>
                            <li>
                                <input class="btn btn-success" style="float: right" type="submit" id="btnLogin"
                                       value="<spring:message code="form.login.title.submit"/>"/>
                            </li>
                        </ul>
                    </li>
                </ul>
            </form>
            <form action="" id="search-form" class="navbar-form navbar-right">
                <div class="input-group">
                    <input class="form-control" type="text"
                           placeholder="<spring:message code="form.search.input.query.placeholder"/>">
                    <span class="input-group-btn">
                        <button style="padding: 4px 4px 4px 8px" class="btn btn-default" type="submit">
                            <i class="fa fa-search fa-fw"></i>
                        </button>
                    </span>
                </div>
            </form>


        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col col-xs-10 col-xs-offset-1 col-md-8  col-md-offset-2 col-lg-6 col-lg-offset-0">
            <div style="margin-top: 15px;" class="jumbotron">
                <sf:form modelAttribute="employee" method="POST" class="form-horizontal">
                    <fieldset>
                        <legend><spring:message code="form.registration.employee.legend"/></legend>
                        <div class="thumbnail" style="background-color: #6DB3DF; color: white;">
                            <div class="form-group">
                                <label for="inputEmail" class="col-lg-2 control-label">
                                    <spring:message code="form.registration.employee.email.label"/>
                                </label>

                                <div class="col-lg-10">
                                    <sf:input path="email" type="text" class="form-control" id="inputEmail"
                                              placeholder="<spring:message code='form.registration.employee.email.placeholder'/>"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputPassword" class="col-lg-2 control-label">
                                    <spring:message code="form.registration.employee.password.label"/>
                                </label>

                                <div class="col-lg-10">
                                    <input type="password" class="form-control" id="inputPassword"
                                           placeholder="<spring:message code="form.registration.employee.password.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputPasswordConfirm" class="col-lg-2 control-label">
                                    <spring:message code="form.registration.employee.password.confirm.label"/>
                                </label>

                                <div class="col-lg-10">
                                    <input type="password" class="form-control" id="inputPasswordConfirm"
                                           placeholder="<spring:message code="form.registration.employee.password.confirm.placeholder"/>">
                                </div>
                            </div>
                        </div>

                        <div class="form-group" id="description-wrapper">
                            <label for="description" class="col-lg-2 control-label">
                                <spring:message code="form.registration.employee.description.label"/>
                            </label>

                            <div class="col-lg-10">
                                <sf:textarea path="description" style="resize: vertical" class="form-control" rows="3"
                                             id="description"/>
                                <span class="help-block" id="description-advice">
                                    <spring:message code="form.hints.description"/>
                                    <span id="description-advice-num"></span>
                                </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="menus-search" class="col-lg-2 control-label">
                                <spring:message code="form.registration.employee.services.label"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="text" id="menus-search" class="form-control" style="border-bottom: 0"
                                       placeholder="<spring:message code="form.registration.employee.services.placeholder"/> "/>

                                <ul id="services-wrapper" style="height: 200px; overflow: auto" class="form-control">
                                    <jsp:useBean id="menus" scope="request"
                                                 type="java.util.List<com.example.pojo.dto.PlaceMenusListDTO>"/>
                                    <c:forEach items="${menus}" var="menu">
                                        <li data-service="${menu.name}" class="checkbox">
                                            <label>
                                                <input type="checkbox"> ${menu.name}
                                            </label>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <span class="help-block"><spring:message
                                        code="form.registration.employee.services.hint"/> </span>
                            </div>
                        </div>

                        <div style="height: 120px" class="form-control">
                            <input type="text" id="work-schedule" value=""/>
                            <span class="help-block"><spring:message
                                    code="form.registration.employee.schedule.hint"/> </span>
                        </div>
                        <br/>

                        <div class="form-control">
                            <sf:hidden path="photo.image" id="input-image" type="file"/>
                            <sf:hidden path="photo.2" id="w"/>
                            <sf:hidden path="photo.h" id="h"/>
                            <sf:hidden path="photo.x" id="x"/>
                            <sf:hidden path="photo.y" id="y"/>
                        </div>
                        <br/>

                        <div class="form-group">
                            <div class="col-lg-10">
                                <a type="reset" class="btn btn-default">
                                    <spring:message code="form.cancel"/>
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <spring:message code="form.submit"/>
                                </button>
                            </div>
                        </div>
                    </fieldset>
                </sf:form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="crop-it">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header alert alert-info">
                <button style="margin-top: -9px;" type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <strong>
                    <spring:message code="attention"/>
                </strong>
                <spring:message code="form.registration.photo.hint"/>
            </div>
            <div class="modal-body">
                <div class="jcrop-wrapper">
                    <img src="" id="crop-this-shit"/>
                </div>
            </div>
            <div class="modal-footer">
                <div class="btn-group">
                    <a data-dismiss="modal" class="btn btn-success">Ok!</a>
                    <a class="btn" data-dismiss="modal" id="modal-csl">Відміна</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var jcrop_api;
    var descriptionIsOk = true;
    function sortElements(query) {
        var $target = $('#services-wrapper');
        var $elements = $target.find('li');
        var regex = new RegExp(query, "i");
        console.log(regex);
        $elements.sort(function (a, b) {
            var an = $(a).attr("data-service").toString(),
                    bn = $(b).attr("data-service").toString();
            console.log(an);
            console.log(bn);

            anMatches = regex.test(an);
            bnMatches = regex.test(bn);

            if (anMatches && bnMatches) {
                return 0;
            }

            if (anMatches && !bnMatches) {
                return -1;
            }

            return 1;
        });

        $elements.detach().appendTo($target);
    }
    $(document).ready(function () {
        var menuSearchInput = $("#menus-search");
        menuSearchInput.keyup(function () {
            var query = menuSearchInput.val();
            sortElements(query);
        });


        var $range = $("#work-schedule");
        $range.ionRangeSlider({
            type: "double",
            grid: true,
            min: 1425333600000,
            max: 1425420000000,
            from: 1425333600000,
            to: 1425420000000,
            step: 300000,                // 5 minutes
            prettify: function (num) {
                return moment(num, 'x').format("h:mm A");
            },
            onUpdate: function (data) {
                alert("Update");
                $range.val(data)
            }
        });

        var src;
        var image;

        var description = $("#description");
        var descriptionAdviceNum = $("#description-advice-num");
        var descriptionWrapper = $("#description-wrapper");
        description.keyup(function () {
            updateDescriptionSpan(description, descriptionAdviceNum, descriptionWrapper, 500);
        });

        var imageInput = $("#input-image");
        $("#modal-csl").click(function () {
            imageInput.src = undefined;
        });
        imageInput.change(function () {
            if (jcrop_api != undefined) {
                jcrop_api.destroy();
            }

            $("#crop-it").modal('show');

        });

        $("#crop-it").on("show.bs.modal", function () {
            src = document.getElementById("input-image");
            image = document.getElementById("crop-this-shit");

            var fr = new FileReader();
            // when image is loaded, set the src of the image where you want to display it
            fr.readAsDataURL(src.files[0]);
            fr.onload = function (e) {
                image.src = this.result;
                initJcrop(image);
            };
        });
    });


    function sortArray(Array, regexp) {
        Array.sort(function (a, b) {

            var bMatch = b.search(regexp) != -1;
            var aMatch = a.search(regexp) != -1;

            if (aMatch && !bMatch) {
                return -1;
            }
            if (!aMatch && bMatch) {
                return 1;
            }
            return 0;
        });
        return Array;
    }

    function showCoords(c) {
        $('#x').val(c.x);
        $('#y').val(c.y);
        $('#w').val(c.w);
        $('#h').val(c.h);
    }

    function initJcrop(img) {
        jQuery(img).Jcrop({
            addClass: 'jcrop-centered',
            aspectRatio: 3.5 / 4,
            onChange: showCoords,
            setSelect: [0, 35, 40, 0],
            onSelect: showCoords
        }, function () {
            jcrop_api = this;
            $(".jcrop-centered").css({
                marginLeft: -img.width / 2
            });

        });
    }
    function updateDescriptionSpan(description, infoNum, wrapper, num) {
        var i = num - description.val().length;
        if (i >= 0) {
            wrapper.removeClass("has-error");
            infoNum.text(i);
            descriptionIsOk = true;
        }
        if (i < 0) {
            wrapper.addClass("has-error");
            infoNum.text("0");
            descriptionIsOk = false;
        }
    }


</script>
</body>
</html>
