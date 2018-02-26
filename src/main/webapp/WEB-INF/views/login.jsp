<%--
  Created by IntelliJ IDEA.
  User: 11785
  Date: 2018/1/30
  Time: 12:21
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <title>用户登录</title>
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
            <caption><h3>管理员登录</h3></caption>
            <tbody>
            <tr>
                <td style="width: 15%; text-align: right;">用户名：</td>
                <td style="width: 55%;"><input type="text" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="username"></td>
                <td class="text-info" style="text-align: center;">请输入用户名</td>
            </tr>
            <tr>
                <td style="width: 15%; text-align: right;">密码：</td>
                <td style="width: 55%;"><input type="password" class="form-control" name="password"></td>
                <td class="text-info" style="text-align: center;">请输入密码</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="rememberMe" value="yes"> 记住我
                        </label>
                    </div>
                    <input class="btn btn-primary" id="userLogin" type="submit" name="登录" value="登录">
                    <a href="${pageContext.request.contextPath}/toRegist.action"><button type="button" class="btn btn-info">注册</button></a>
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
</html>
