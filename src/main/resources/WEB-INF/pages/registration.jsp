<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Registration</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="/css/font-awesome.css" rel="stylesheet"/>
    <link href="/css/pages/registration.css" rel="stylesheet"/>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
</head>
<body>
<div id="wraper">
    <sf:form method="post" id="f" modelAttribute="${user}"
             action="/browzer/registration" onsubmit="doSubmit()">
        <fieldset>
            <div id="wrapper">
                <p>Required fields markd by *</p>

                <label for="input name" class="parametr">Name</label>
                <sf:input path="${user.userName}" id="input name"/>*

                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <sf:errors path="${user.userName}"/>
                </div>

                <label for="input email" class="parametr">Email</label>
                <sf:input path="${user.userEmail}" id="input email"/>*
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <sf:errors path="${user.userEmail}"/>
                </div>

                <label for="input pass" class="parametr">Password</label>
                <sf:password path="${user.userPass}" id="input pass"/>*
                <sf:password path="${user.userPassConf}" id="input pass"/>*
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <sf:errors path="${user.userPass}"/><sf:errors/>
                </div>

                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <sf:errors path="${user}"/><sf:errors/>
                </div>

                <input type="submit" value="Register" name="submit"/>

            </div>
        </fieldset>
    </sf:form>
</div>
</body>
</html>