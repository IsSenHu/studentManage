$(function () {
    $("#timeOut").click(acceptSmsVerifyCode);
    $("#registUser").click(registUser);
    $("input[name='passwordAgain']").keyup(checkPasswordAgain);
    $("input[name='password']").keyup(checkPassword);
    $("input[name='mobilePhone']").keyup(checkPhone);
    $("input[name='mobilePhone']").blur(checkPhone);
    $("#userLogin").click(userLogin);
})
function acceptSmsVerifyCode() {
    if(!checkPhone()){
        $("#phoneInfo").text('手机号格式不正确！').css("color", "red");
        return;
    }
    var mobilePhone = $("input[name='mobilePhone']").val();
    $.ajax({
        type : 'post',
        url : '/sendSms.action',
        data : 'mobilePhone=' + mobilePhone,
        success : function (data) {
            var json = $.parseJSON(data);
            if("ok" == json.result){
                $(".clickA").attr("disabled", true);
                var timeOut = $("#timeOut");
                var time = 60;
                var showTime = self.setInterval(function () {
                    if(time >= 0){
                        timeOut.text('(' + time + ')秒后可重新获取');
                        time--;
                    }else {
                        timeOut.text('点击获取短信验证码');
                        $(".clickA").attr("disabled", false);
                        self.clearInterval(showTime)
                    }
                }, 1000);
            }else if (json.result == "existed"){
                $("#msg").text("该手机已被注册").css("color", "red").slideDown("slow");
                self.setTimeout(function () {
                    $("#msg").hide();
                }, 3000);
            }else {
                alert('验证码获取失败，请重试！');
            }
        }
    });
}
function registUser() {
    var m = checkPhone();
    var n = checkPassword();
    var l = checkPasswordAgain();
    if(!(m && n && l)){
        return;
    }
    var valiCode = $("input[name='valiCode']").val();
    if(valiCode == ''){
        showErrorMsg("请输入验证码");
        return;
    }
    var password = $("input[name='password']").val();
    var mobilePhone = $("input[name='mobilePhone']").val();
    $.ajax({
        type : 'post',
        url : '/userRegist.action',
        data : 'valiCode=' + valiCode + '&password=' + password + '&mobilePhone=' + mobilePhone,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result == "faile"){
                showErrorMsg("验证码错误");
            }else if (json.result == "existed"){
                showErrorMsg("该手机已被注册");
            }else {
                showOkMsg("注册成功");
                self.setTimeout(function () {
                    location.href="/toLogin.action";
                }, 2000);
            }
        }
    });
}
function checkPassword() {
    var password = $("input[name='password']").val();
    if(password.length < 6 || password.length > 16){
        $("#passwordInfo").text("密码长度在6-16之间！").css("color", "red");
        return false;
    }else if(password.indexOf(" ") != -1){
        $("#passwordInfo").text("密码不能有空格！").css("color", "red");
        return false;
    }else {
        $("#passwordInfo").text("输入正确！").css("color", "green");
        return true;
    }
}
function checkPasswordAgain() {
    var password = $("input[name='password']").val();
    var passwordAgain = $("input[name='passwordAgain']").val();
    if(password != passwordAgain){
        $("#passwordAgainInfo").text("两次密码不一致！").css("color", "red");
        return false;
    } else if(passwordAgain == ''){
        $("#passwordAgainInfo").text("请输入密码！").css("color", "red");
        return false;
    }else {
        $("#passwordAgainInfo").text("两次密码一致！").css("color", "green");
        return true;
    }
}
function checkPhone() {
    //获取手机号
    var mobilePhone = $("input[name='mobilePhone']").val();
    //手机号正则
    var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(!phoneReg.test(mobilePhone)){
        $("#phoneInfo").text('手机号格式不正确！').css("color", "red");
        return false;
    }else {
        $("#phoneInfo").text('输入正确！').css("color", "green");
        return true;
    }
}
function userLogin() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();
    var rememberMe = $("input[name='rememberMe']").val();
    $.ajax({
        type : 'post',
        url : '/userLogin.action',
        data : 'username=' + username + '&password=' + password + '&rememberMe=' + rememberMe,
        success : function (data) {
            var json = $.parseJSON(data);
            if(json.result == "ok"){
                location.assign("/toIndex.action");
            }else if(json.result == "forbidden"){
                showErrorMsg("暂时无法登录");
            }else if(json.result == "faile"){
                showErrorMsg("用户名或密码错误，请重试");
            }else {
                showErrorMsg("该用户不存在，请检查用户名");
            }
        }
    });
}
function showErrorMsg(msg) {
    var divMsg = $("#msg");
    divMsg.text(msg).css("color", "red").slideDown("slow");
    self.setTimeout(function () {
        divMsg.hide();
    }, 3000);
}
function showOkMsg(msg) {
    var divMsg = $("#msg");
    divMsg.text(msg).css("color", "green").slideDown("slow");
    self.setTimeout(function () {
        divMsg.hide();
    }, 3000);
}
function addIn() {
    
}