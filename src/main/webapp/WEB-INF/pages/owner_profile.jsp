<%--
  Created by IntelliJ IDEA.
  User: Dmitrij
  Date: 20.09.2015
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
</head>
<body>
<c:if test="${places ne null}">
    <div class="container">
        <div class="row masonry" data-columns>
            <c:forEach items="${places}" var="place" varStatus="i">
                <div class="cell">
                    <div class="thumbnail">
                        <img class="img-responsive" onerror="http://placehold.it/600x340"
                             src="/place/${place.id}/image/main" alt="">

                        <table class="table ">
                            <tr>
                                <td>
                                    <h3>${place.placeName}</h3><span class="badge">${place.placeOwner.userName}</span>
                                </td>
                                <td>
                                    <div><span style="float: right; margin-top: 12px" class="rating">
                        <span class="rating-star"></span>
                        <span class="rating-star"></span>
                        <span class="rating-star"></span>
                        <span class="rating-star"></span>
                        <span class="rating-star"></span>
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
                                     id="description-tab-${i}">
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
</c:if>

</body>
</html>
