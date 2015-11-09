<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<style>
  i.fa{
    padding-right: 4px;
  }
  .nav-tabs~i.fa{
    padding-right: 40px;
  }
  div.navbar{
    margin-bottom: 0px;
  }
  #authorization-group {
    left: 50%;
    margin-left: -113.5px;
    margin-bottom: 5px
  }
  #user-menu-dropdown {
    padding: 0;
  }

  #menu-item-my-page {
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  #enter-form {
    border: 1px solid #ddd;
    background-color: #fff;
  }

  .authentication-button {
    width: 114px;
  }

  #enter-form>li{
    font-size: 0;
  }
</style>
<div class="navbar navbar-inverse navbar-fixed-top">
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
                    <input class="form-control" type="password" name="j_password" placeholder="Password">
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

