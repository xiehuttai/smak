<%--
  Created by IntelliJ IDEA.
  User: xiehu
  Date: 2018/7/13
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
    <title>创建</title>
</head>
<body>
    <form action="customer_create" method="post" accept-charset="UTF-8">

        <label>姓名：</label> <input name="name" type="text"><br>
        <label>联系人：</label> <input name="contact" type="text"><br>
        <label>电话：</label> <input name="telephone" type="text"><br>
        <label>邮箱：</label> <input name="email" type="text"><br>
        <label>备注：</label> <input name="remark" type="text"><br>

        <input type="submit">
    </form>
</body>
</html>
