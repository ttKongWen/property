<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>校园故障报修系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="http://47.107.77.7:82/layui/css/layui.css"  media="all">
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<div class="page-header">

</div>

<div style=" width:700px;
            height: 600px;
            position:absolute;
            left:0;
            top: 0;
            bottom: 0;
            right: 0;
            margin: auto;
            ">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend> 报修单 <span>编号：${repairInfoVo.repair.id}</span></legend>
    </fieldset>

    <table class="layui-table" lay-size="lg">
        <colgroup>
            <col width="200">
            <col width="500">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>特征</th>
            <th>描述</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>状态</td>
            <td> ${repairInfoVo.statesInfo}</td>
        </tr>
        <tr>
            <td>时间</td>
            <td>${repairInfoVo.repair.submitTime}</td>
        </tr>
        <tr>
            <td>故障地点</td>
            <td> ${repairInfoVo.repair.place}</td>
        </tr>

        <tr>
            <td>问题详情</td>
            <td> ${repairInfoVo.repair.detail}</td>
        </tr>

        <tr>
            <td>描述图片</td>
            <td><img alt="140x140" src="${repairInfoVo.repair.picMD5}" class="img-rounded"/></td>
        </tr>

        </tbody>
        <tfoot>
        <tr>
            <div style="display:flex;flex-direction: row;justify-content: space-between">
                <a href="/admin/dashboard" class="layui-btn">返回上一级</a>
                <c:if test="${repairInfoVo.repair.status < 3}">
                    <a href="/admin/repair/${repairInfoVo.repair.id}/arrange" class="layui-btn">安排检修</a>
                    <a href="/admin/repair/${repairInfoVo.repair.id}/cancel" class="layui-btn">取消该报修单</a>
                </c:if>
                <c:if test="${repairInfoVo.repair.status < 6}">
                    <a href="/admin/repair/${repairInfoVo.repair.id}/confirm" class="layui-btn">确认检修完毕</a>
                </c:if>
            </div>
        </tr>
        </tfoot>

    </table>


</div>



<script src="http://47.107.77.7:82/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
</body>
</html>