<%@ page import="com.ilovecl.demo._const.StudentConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("utf-8");%>
<head>
    <meta charset="utf-8">
</head>
<div class="header-bg">
    <div class="container rm-l-p rm-r-p">
        <div class="header">
            <div class="col-md-2 col-md-offset-0 rm-l-p rm-r-p header-logo">
                <a href="">
                   <span>校园故障报修系统</span>
                </a>
            </div>
            <div class="col-md-10  rm-l-p rm-r-p header-right">
                <ul class="header-menu">
                    <li class="menu-item menu-item-index"><a href="<c:url value="/student/dashboard"/>"
                                                             class="menu-link">校园故障报修系统</a></li>
                    <li class="menu-item menu-item1"><a href="<c:url value="/student/dashboard"/>" class="menu-link">管理面板</a>
                    </li>
                    <li class="menu-item menu-item2"><a href="<c:url value="/student/introduce"/>" class="menu-link">系统介绍</a>
                    </li>
                    <li class="menu-item menu-item3"><a href="<c:url value="/student/info"/>" class="menu-link">
                        <%--定义JSP中的全局变量name--%>
                        <%!
                            String name;
                        %>
                        <%--寻找cookie内容并赋值给全局变量name--%>
                        <%
                            Cookie cookie = null;
                            Cookie[] cookies = null;

                            cookies = request.getCookies();
                            if (cookies != null) {
                                for (Cookie cooky : cookies) {
                                    if (cooky.getName().equals(StudentConst.STUDENT_NAME)) {
                                        System.out.println(cooky.getName() + " : " + cooky.getValue());
                                        name = cooky.getValue();
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("cookie为空！！！！！");
                            }
                        %>
                        <%--html引用全局变量name的值--%>
                        <%=name%>

                    </a></li>
                    <li class="menu-item menu-item4"><a href="<c:url value="/student/logout"/>" class="menu-link">退出</a>
                    </li>
                    <!--  <li class="menu-item menu-item5"><a href="https://www.wilddog.com/my-account/login" class="menu-link menu-link-login">登录</a></li>
                    <li class="menu-item menu-item6"><a href="https://www.wilddog.com/my-account/signup" class="menu-link btn-org-full">注册</a></li>-->
                </ul>
            </div>
        </div>

        <div class="header-mobile">
            <div class="header-logo">
                <a href="/">
                    <img src="/images/z-header-logo.svg" alt="红叶" class="wd-logo">
                    <img src="/images/z-header-logo-text.svg" alt="红叶" class="logo-text">
                </a>
            </div>
        </div>

        <button class="navbar-toggle collapsed" type="button">
            <span class="icon-bar menu-line-t"></span>
            <span class="icon-bar menu-line-m"></span>
            <span class="icon-bar menu-line-b"></span>
        </button>
    </div>
</div>

