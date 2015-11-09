<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page contentType="text/html; ISO-8859-1" pageEncoding="UTF-8" %>
<html>
<head>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>New place</title>
    <link href="/resources/css/newplace_style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>

</head>
<body>
<div id="wraper">
    <form method="POST" enctype="multipart/form-data" id="f" action="/browzer/newplace" name="main">
        <p class="parametr">Place name</p>
        <input type="text" class="input name" name="placeName"/>

        <p class="parametr">Place city</p>
        <input type="text" class="input address" name="placeCity"/>

        <div>
            <p class="parametr" style="width:66%; display: inline-block" ;>Place street</p>

            <p class="parametr" style="display: inline-block; width: 25%">Number</p>
            <input style="display: inline; width: 65%" type="text" class="input address" name="placeStreet"/>
            <input style="width: 25%" type="text" class="input address" name="placeAddress"/>
        </div>
        <p>Input short description</p>
        <textarea class="input desc" name="placeDescription"></textarea>

        <p>Choose the type of place you want to add</p>
        <select class="input types" name="placeSpeciality"><br/>
            <option>Kaffe</option>
            <option>Night club</option>
            <option>Work zone</option>
            <option>Restaurant</option>
            <option>Hotell</option>
            <option>Motell</option>
            <option>Fast Food</option>
        </select>

        <p>Select photos</p>
        <input type="file" class="input file" name="file"
               style="width: 90%"/>
        <input type="submit" name="submit" style="margin: 15px 0 0 0" value="Create new place"/>
    </form>
</div>


<script type="text/javascript">

    $(':file').change(function(){
        var file = this.files[0];
        var type =file.type;
       if(type!='image/jpeg'||'image/png'){

       }
    });
/*
    $(".submit").click(function () {
        var formData = new FormData($("#f"));
        $.ajax({
                    url:'/browzer/newplace',
                    type:'POST',
                    xhr:function(){
                        var myXHR= $.ajaxSettings.xhr();
                        return myXHR;
                    },
                    data:formData,
                    cache:false,
                    contentType:false,
                    processData:false
                }
        )
    });
*/

    function validateData() {

        var placeName = document.getElementsByName("placeName")[0];
        var placeDescription = document.getElementsByName("placeDescription")[0];
        var placeCity = document.getElementsByName("placeCity")[0];
        var placeStreet = document.getElementsByName("placeStreet")[0];
        var placeSpeciality = document.getElementsByName("placeSpeciality")[0];


        return !(placeName.value == '' ||
        placeDescription.value == '' ||
        placeCity.value == "" ||
        placeStreet.value == "" ||
        placeSpeciality.value == "");
    }

    function submitFails() {
        alert("Пожалуйста заполните все поля");
        return false;
    }

    function sendData() {
        var mainForm= document.forms[0];
        var formData = new FormData(mainForm);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/browzer/newplace");
        xhr.send(formData);
    }

</script>

</body>
</html>
