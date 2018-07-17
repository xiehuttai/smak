<%@ taglib prefix="v" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xiehu
  Date: 2018/7/13
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>列表</title>
</head>
<body>

    <jsp:useBean id="consumers" class="java.util.ArrayList"></jsp:useBean>

    <v:forEach var="customer" items="customers"  >
        <p>${customer.id}</p>
        <p>${customer.name}</p>
        <p>${customer.telephone}</p>
        <p>${customer.contact}</p>
        <p>${customer.email}</p>
        <p>${customer.remark}</p>
    </v:forEach>

</body>
</html>
