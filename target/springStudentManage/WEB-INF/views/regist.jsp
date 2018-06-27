<%--
  Created by IntelliJ IDEA.
  User: 11785
  Date: 2018/1/30
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" language="java" import="java.util.*"  contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 可选的Bootstrap主题文件（一般不使用） -->

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my.css">
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
    <title>用户注册</title>
</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="border-radius: 0px;">
    <div class="container-fluid">
        <div class="navbar-header">
            <b class="navbar-brand">爱动网库存管理系统</b>
        </div>
    </div>
</nav>
<div id="msg"></div>
<div class="topfield"></div>
<div class="row">
    <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4"></div>
    <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4" id="login">
            <table class="table">
                <caption><h3>管理员注册</h3></caption>
                <tbody>
                <tr>
                    <td style="width: 20%; text-align: right;">手机号：</td>
                    <td style="width: 50%;"><input type="text" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="mobilePhone"></td>
                    <td class="text-info" style="text-align: center;"><button id="phoneInfo" class="btn">请输入手机号</button></td>
                </tr>
                <tr>
                    <td style="width: 20%; text-align: right;">密码：</td>
                    <td style="width: 50%;"><input type="password" class="form-control" name="password"></td>
                    <td class="text-info" style="text-align: center;"><button id="passwordInfo" class="btn">请输入密码</button></td>
                </tr>
                <tr>
                    <td style="width: 20%; text-align: right;">重复密码：</td>
                    <td style="width: 50%;"><input type="password" class="form-control" name="passwordAgain"></td>
                    <td class="text-info" style="text-align: center;"><button id="passwordAgainInfo" class="btn">请再次输入密码</button></td>
                </tr>
                <tr>
                    <td style="width: 20%; text-align: right;">短信验证码：</td>
                    <td style="width: 50%;">
                        <table>
                            <tr>
                                <td style="width: 40%"><input type="text" class="form-control" name="valiCode"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="text-info" style="text-align: center;"><button id="timeOut" class="clickA btn">点击获取短信验证码</button></td>
                    <script>

                    </script>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input id="registUser" class="btn btn-primary" type="button" name="注册" value="注册">
                        <a href="${pageContext.request.contextPath}/toLogin.action"><button type="button" class="btn btn-info">登录</button></a>
                    </td>
                    <td></td>
                </tr>
                </tbody>
            </table>
    </div>
    <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4"></div>
</div>
<footer class="footer navbar-fixed-bottom ">
    <div class="container-fluid" style="text-align: right; color: greenyellow;">
        爱动网库存管理系统&copy;HuSen
    </div>
</footer>
</body>
<script type="text/javascript">
    /**
     * 1点击手机获取验证码以后需要60s后才能再次获取。
     * 2当60s过后，手机号码可以重新输入
     */
    $(".clickA").click(showInput);
    function showInput() {
        $("#regist").attr("disabled", false);
    }
//    $("#timeOut").text("60S后可再次获取");
</script>
</html>
