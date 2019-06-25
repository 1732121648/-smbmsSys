<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 17321
  Date: 2019/5/9
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询用户列表</title>
</head>
<body>
<h1>查询用户列表</h1>
    <form action="${pageContext.request.contextPath}/user2/list" method="get">
        用户名称：<input type="text" name="username" value=""/>
        <br>
        <input type="submit" name="" value="查询"/>
    </form>

<c:forEach var="user" items="${userList}">
    <div>
        用户编码：${user.usercode}-----
        用户名称：${user.username}-----
        用户生日：${user.birthday}-----
        用户地址：${user.address}-----
    </div>
</c:forEach>
</body>
</html>
