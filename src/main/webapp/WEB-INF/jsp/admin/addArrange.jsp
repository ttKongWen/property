<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>安排维修_校园故障报修系统</title>
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
<div id="wrap">

    <%--引入页面头部--%>
    <%@include file="header.jsp" %>

    <div class="container main-body">

        <div class="col-md-9 col-md-offset-1 layout-page">

            <section class="content">
                <h1> 安排检修 </h1>
                <div class="func pull-right">
                </div>

                <p>所有维修人员</p>

                <div class="content-text">

                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>姓名</th>
                        </tr>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="technicien" items="${techniciens}">
                            <tr>
                                <td>${technicien.id}</td>
                                <td>${technicien.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="layui-form-item" style="border-left:2px solid black;">
                    <label class="layui-form-label" style="width:100px;">保单详情</label>
                    <div class="layui-input-block">
                        <input name="title" class="layui-input" type="text" readonly="readonly" value="${repair.detail}" autocomplete="off" lay-verify="title">
                    </div>
                </div>

                <form class="form-horizontal" method="post"
                      action="<c:url value="/admin/maintenance/${repair.id}/add"/>"
                      enctype="multipart/form-data">
                    <fieldset>

                        <div class="layui-form-item" style="border-left:2px solid black;">
                            <label for="technicianId" class="layui-form-label" style="width:100px;">工员编号</label>
                            <div class="layui-input-block">
                                <input name="technicianId" placeholder="负责检修的维修人员编号" class="layui-input" type="text"  autocomplete="off" lay-verify="title">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label" style="width:100px;"></label>
                            <div class="layui-input-block">
                                <input class="layui-btn layui-btn-fluid" value="提交安排信息" type="submit" title="hi"/>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </section>
        </div>

        <div class="back-top-ab">
            <div class="back-top"><i class="wd-font">R</i></div>
        </div>
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
</body>

</html>