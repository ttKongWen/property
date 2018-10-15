<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>校园故障报修系统</title>
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
</head>

<body>
<div id="wrap">

    <%--引入页面头部--%>
    <%@include file="header.jsp" %>


    <div class="container main-body">
        <div class="col-md-2 menu">
            <li class="category  active">
                <div class="category-title"><a href="<c:url value="/admin/dashboard"/>">报修单</a></div>
                <ul class="pages">
                    <li class="page  "><a href="<c:url value="/admin/dashboard"/>" class="unvisited">查看未完成</a>
                    </li>
                    <li class="page active"><a href="<c:url value="/admin/finish"/>" class="unvisited">查看已完成</a>
                    </li>
                </ul>
            </li>
            <li class="category ">
                <div class="category-title"><a href="<c:url value="/admin/urgent"/>">催单</a></div>
                <ul class="pages">
                    <li class="page  active"><a href="<c:url value="/admin/urgent"/>" class="unvisited">查看</a>
                    </li>
                </ul>
            </li>
            <li class="category  ">
                <div class="category-title"><a href="<c:url value="/admin/arrange"/>">维修安排</a></div>
                <ul class="pages">
                    <li class="page  active"><a href="<c:url value="/admin/arrange"/>" class="unvisited">查看</a>
                    </li>
                </ul>
            </li>
            <li class="category ">
                <div class="category-title"><a href="<c:url value="/admin/student"/>">学生管理</a></div>
                <ul class="pages">
                    <li class="page  active"><a href="<c:url value="/admin/student"/>" class="unvisited">查看</a>
                    </li>
                    <li class="page  active"><a href="<c:url value="/admin/addstudent"/>" class="unvisited">添加</a>
                    </li>
                </ul>
            </li>
            <li class="category ">
                <div class="category-title"><a href="<c:url value="/admin/technician"/>">维修人员管理</a></div>
                <ul class="pages">
                    <li class="page  active"><a href="<c:url value="/admin/technician"/>" class="unvisited">查看</a>
                    </li>
                    <li class="page  active"><a href="<c:url value="/admin/addtechnician"/>" class="unvisited">添加</a>
                    </li>
                </ul>
            </li>
        </div>

        <div class="col-md-9 col-md-offset-1 layout-page">
            <section class="content">
                <h1> 所有报修单 </h1>
                <div class="func pull-right">
                </div>

                <div class="content-text">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>状态</th>
                            <th>详情</th>
                            <th>地点</th>
                            <th>提交时间</th>
                            <th>提交人</th>
                            <th></th>
                            详情
                        </tr>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="repairInfoVo" items="${repairInfoVos}">
                            <tr>
                                <td>${repairInfoVo.statesInfo}</td>
                                <td>${repairInfoVo.repair.detail}</td>
                                <td>${r.place}</td>
                                <td>
                                    <fmt:formatDate value="${repairInfoVo.repair.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>${repairInfoVo.student.name}</td>
                                <td>
                                    <a class="btn btn-link" href="/admin/repair/${repairInfoVo.repair.id}/detail">详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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