<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Ecommerce</title>
</head>
<body>
<%--<h1><%= "Ecommerce Management!" %></h1>--%>
<br/>
<h1><%= "User Management" %></h1>
<ul>
<%--  <li><a href="${pageContext.request.contextPath}/CategoriesController?action=findAll">Quản lý danh mục</a></li>--%>
<%--  <li><a href="">Quản lý sản phẩm</a></li>--%>

    <li><a href="${pageContext.request.contextPath}/UserController?action=findAll">Danh sach User</a></li>

</ul>
</body>
</html>