<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>修改资料_校园故障报修系统</title>
    <meta name="keywords"
          content="wilddog, WildDog, realtime, PaaS, BaaS, HTMl5, CoAP, Thread, REST, Javascript, DTLS, websockets, realtime sync, UDP, JSON, developer, B2B, SDK, iOS, Android, Mac OS, Windows, 野狗, 野狗实时, 野狗云, 实时应用, 实时同步, 实时数据库, 跨平台, 物联网, 构建实时应用, 受限网络, 传感网, 解决方案, 云平台, 云计算, 云服务, 公有云, 私有云, 开发者">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="email=no"/>
    <meta name="format-detection" content="address=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <!--[if lt IE 9]>
    <script type="text/javascript">
        window.location.href = "/outmen";
    </script>
    <![endif]-->
    <link rel="stylesheet" href="https://z.wilddog.com/bower_components/highlightjs/styles/color-brewer.css">
    <link rel="stylesheet" href="https://z.wilddog.com/css/base.css">
    <link rel="stylesheet" href="https://z.wilddog.com/css/fonts.css">
    <link rel="stylesheet" href="https://z.wilddog.com/css/header_public.css">
    <link rel="stylesheet" href="https://z.wilddog.com/css/footer.css">
    <link rel="stylesheet" href="https://z.wilddog.com/css/doc.css">
    <link rel="stylesheet" href="css/layui/css/layui.css"  media="all">
</head>

<body>

<div id="wrap">

    <%--引入页面头部--%>
    <%@include file="header.jsp" %>

    <div class="container main-body" style=" width:500px;
            height: 500px;
            position:absolute;
            left:0;
            top: 0;
            bottom: 0;
            right: 0;
            margin: auto;
            ">



        <form class="layui-form" action="">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">原姓名：</label>
                <div class="layui-input-block">
                    <input name="title" class="layui-input" type="text" readonly="readonly" value="${student.name}" autocomplete="off" lay-verify="title">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">新姓名：</label>
                <div class="layui-input-block">
                    <input name="newName" class="layui-input" type="text" placeholder="输入新姓名" autocomplete="off" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">原电话：</label>
                <div class="layui-input-block">
                    <input name="title" class="layui-input" type="text" readonly="readonly" value="${student.phone}" autocomplete="off" lay-verify="title">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">新电话：</label>
                <div class="layui-input-block">
                    <input name="newPhone" class="layui-input" type="text" placeholder="输入新电话" autocomplete="off" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">原性别：</label>
                <div class="layui-input-block">
                    <input name="title" class="layui-input" type="text" readonly="readonly" value="${student.sexualInfo}" autocomplete="off" lay-verify="title">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">新性别：</label>
                <div class="layui-input-block">
                    <input name="newSexual" class="layui-input" type="text" placeholder="输入新性别" autocomplete="off" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;">新密码：</label>
                <div class="layui-input-block">
                    <input type="password" name="newPassword" placeholder="输入新密码" class="layui-input" placeholder="请输入" autocomplete="off" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:100px;"></label>
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-fluid" onclick="changeMessage();">更新资料</button>
                </div>
            </div>

        </form>

    </div>

</div>


<script src="https://z.wilddog.com/bower_components/jquery/dist/jquery.min.js"></script>
<!--
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/bower_components/fitvids/jquery.fitvids.js"></script>
<script src="/bower_components/masonry/dist/masonry.pkgd.min.js"></script>
-->
<script src="https://z.wilddog.com/bower_components/highlightjs/highlight.pack.js"></script>
<script src="https://z.wilddog.com/scripts/raneto.js" type="text/javascript"></script>
<script src="https://z.wilddog.com/scripts/mobile.js" type="text/javascript"></script>
<script type="text/javascript">
    //        window.onload = loadOver;
    $(document).ready(function () {
        loadOver();
    })
</script>
<!-- Google Tag Manager -->
<script>
    window.addEventListener('load', function () {
        (function (w, d, s, l, i) {
            w[l] = w[l] || [];
            w[l].push({
                'gtm.start': new Date().getTime(),
                event: 'gtm.js'
            });
            var f = d.getElementsByTagName(s)[0],
                    j = d.createElement(s),
                    dl = l != 'dataLayer' ? '&l=' + l : '';
            j.async = true;
            j.src =
                    '//www.wilddog.com/gtm.js?id=' + i + dl;
            f.parentNode.insertBefore(j, f);
        })(window, document, 'script', 'dataLayer', 'GTM-53PGC2');
    })
</script>
<!-- End Google Tag Manager -->

<script>

    function changeMessage()
    {
        changePassword();
        changeInfo();
    }

    function changePassword() {
        var b = $("input[name=newPassword]").val();

        $.ajax({
            url: "/student/changepassword",
            type: "POST",
            data: {
                password: b
            },
            cache: !1,
            timeout: 3e4,
            dataType: "json",
            success: function (data) {

                if (data.isSuccess == "true") {
                    alert("修改密码成功");

                }
                else if (data.resultInfo == "invalid password") {
                    alert("修改失败");
                }
            },
            error: function () {
                alert("fail\n");
            }
        })
    }

    function changeInfo() {
        var a = $("input[name=newName]").val();
        var b = $("input[name=newPhone]").val();
        var c = $("input[name=newSexual]").val();


        $.ajax({
            url: "/student/changeinfo",
            type: "POST",
            data: {
                name: a,
                phone: b,
                sexual: c
            },
            cache: !1,
            timeout: 3e4,
            dataType: "json",
            success: function (data) {
                if (data.isSuccess() == "true") {
                    alert("修改密码成功");
                }
                else if (data.isSuccess() == false) {
                    alert("修改失败");
                }

            },
            error: function () {
                alert("fail\n");
            }
        })
    }
</script>
</body>

</html>