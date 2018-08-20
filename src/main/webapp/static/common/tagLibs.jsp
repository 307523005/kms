<%--
  Created by IntelliJ IDEA.
  User: Shawearn
  Date: 2017/1/11
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
<%--<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>--%>
<%--<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>--%>

<%--<%//用于实现jsp模板的继承关系,请查看:http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends %>--%>
<%--<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>--%>
<%--<c:set var="root" value="${pageContext.request.contextPath}"/>--%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}/manage"/>--%>

<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragrma", "no-cache");
    response.setDateHeader("Expires", 0);

    String basePath ;
    if(request.getServerPort()==80){
        basePath = request.getScheme() + "://"
                + request.getServerName() + request.getContextPath() + "/";
    }else{
        basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
    }
%>
<base href="<%=basePath%>">

<%--login --%>
<link rel="stylesheet" href="static/common/css/style.css">
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="static/common/js/vendors.js"></script>
<script src="static/common/js/index.js"></script>
<script type="text/javascript" src="static/common/js/dd_png_min.js"></script>
<%--下拉框--%>
<link rel="stylesheet" href="static/common/css/j-select.css" />
<%--<script src="http://www.jq22.com/jquery/1.8.3/jquery.min.js"></script>--%>
<script src="static/common/js/nicescroll/jquery-nicescroll.js"></script>
<script src="static/common/js/jquery-jSelect.min.js" ></script>

<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.js"></script>
<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>