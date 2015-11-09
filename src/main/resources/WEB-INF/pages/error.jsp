<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 23.09.2015
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title><c:out value="${error}"></c:out></title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

  <!-- Bootstrap -->
  <link href="/webresources/bootstrap-3.3.5-dist/css/bootstrap.css" rel="stylesheet">
  <link href="/webresources/css/style.css" rel="stylesheet" type="text/css">
  <link href="/webresources/font-awesome-4.4.0/css/font-awesome.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

  <![endif]-->
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-xs-10 col-xs-offset-1">
      <div class="thumbnail">
        <p><c:out value="${error}"></c:out> </p>
      </div>
    </div>
  </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/webresources/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/webresources/bootstrap-3.3.5-dist/js/bootstrap.js"></script>

<script src="/webresources/js/salvattore.min.js"></script>
</body>
</html>
