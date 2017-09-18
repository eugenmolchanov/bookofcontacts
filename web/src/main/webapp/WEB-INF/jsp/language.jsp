<%--
  Created by IntelliJ IDEA.
  User: Yauhen Malchanau
  Date: 18.09.2017
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css?v=3">
</head>
<body>
<div class="language">
    <div id="en"><a href="/controller?command=language&language=en_US">En</a> </div>
    <div id="ru"><a href="/controller?command=language&language=ru_RU">Ru</a></div>
    <div id="by"><a href="/controller?command=language&language=be_BY">By</a></div>
</div>
</body>
</html>
