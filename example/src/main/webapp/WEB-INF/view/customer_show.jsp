<%--
  Created by IntelliJ IDEA.
  User: xiehu
  Date: 2018/7/13
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
    <title>展示</title>
</head>
<body>

<table>
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>联系人</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>备注</th>
    </tr>
    <tr>
        <td>${customer.id}</td>
        <td>${customer.name}</td>
        <td>${customer.contact}</td>
        <td>${customer.telephone}</td>
        <td>${customer.email}</td>
        <td>${customer.remark}</td>
    </tr>
</table>

</body>
</html>
