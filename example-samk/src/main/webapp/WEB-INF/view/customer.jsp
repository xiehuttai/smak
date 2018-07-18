<%@ taglib prefix="v" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xiehu
  Date: 2018/7/13
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--获取上下文路径--%>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>列表</title>
</head>
<body>

<h1>列表</h1>

<a href="${BASE}/customer_create">创建</a>

<table>

    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>联系人</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>备注</th>
    </tr>

    <c:forEach var="customer" items="${customers}"  >
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.email}</td>
            <td>${customer.remark}</td>
            <td>
                <a href="${BASE}/customer_edit?id=${customer.id}">编辑</a>
                <a href="${BASE}/customer_delete?id=${customer.id}">删除</a>
                <a href="${BASE}/customer_show?id=${customer.id}">查看</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
