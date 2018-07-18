<%--
  Created by IntelliJ IDEA.
  User: xiehu
  Date: 2018/7/13
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>编辑</title>
</head>
<body>

<form action="customer_edit?id=${customer.id}" method="post" accept-charset="UTF-8" >

    <label>姓名：</label> <input name="name" type="text" aria-valuetext="${customer.name}">${customer.name}<br>
    <label>联系人：</label> <input name="contact" type="text">${customer.contact}<br>
    <label>电话：</label> <input name="telephone" type="text">${customer.telephone}<br>
    <label>邮箱：</label> <input name="email" type="text">${customer.email}<br>
    <label>备注：</label> <input name="remark" type="text">${customer.remark}<br>

    <input type="submit">
</form>
</body>
</html>
