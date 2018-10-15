<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>提交报修单_校园故障报修系统</title>
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
    <link rel="stylesheet" href="http://47.107.77.7:82/layui/css/layui.css">
    <script src="http://47.107.77.7:82/layui/layui.js"></script>

</head>

<body>
<div id="wrap" style=" width:700px;
            height: 600px;
            position:absolute;
            left:0;
            top: 0;
            bottom: 0;
            right: 0;
            margin: auto;
            ">
    <section class="content">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
            <legend> 报修单 <span>编号：${repair.id}</span></legend>
        </fieldset>

        <div class="content-text">

            <form class="form-horizontal" method="post" action="<c:url value="/student/repair/${repair.id}/update"/>"
                  enctype="multipart/form-data">
                <fieldset  class="layui-elem-field layui-field-title" style="margin-top: 50px;">
                    <legend>故障修改提交</legend>

                    <input type=hidden name="id" value=${repair.id}>

                    <table class="layui-table" lay-size="lg">
                        <colgroup>
                            <col width="150">
                            <col width="400">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>特征</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>故障详情</td>
                            <td><textarea type="text" class="repairDetail" name="detail" title="" style="width:100%;height:50px;" placeholder="${repair.detail}"> </textarea></td>
                        </tr>
                        <tr>
                            <td>故障地点</td>
                            <td><input type="text" class="repairPlace" name="place" placeholder="${repair.place}" class="input-xlarge" style="width:100%;height:50px;"></td>
                        </tr>

                        </tbody>
                    </table>

                    <div class="layui-upload" style="margin-bottom:30px;">
                        <input class="input-file" id="test8" name="file" type="file" style="width:100%;height:50px;"></input>
                    </div>

                    <input type="submit" title="hi" value="提交修改内容" class="layui-btn layui-btn-fluid"></input>
                </fieldset>
            </form>

        </div>
    </section>
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
</body>


<script>
    function comitBtn() {
        $.ajax({
            url: "/student/commit",
            method: "POST",
            fileElementId: "img",
            dataType: "json",
            contentType: "json",
            success: function () {
                alert("nothing");
            }
        });
    }
</script>
<script>
    function showMessage() {
        var detail = document.getElementsByClassName("repairDetail").values();
        var place = document.getElementsByClassName("repairPlace").values();

        window.alert("detail:"+detail+";place:"+place);
    }
</script>
</html>