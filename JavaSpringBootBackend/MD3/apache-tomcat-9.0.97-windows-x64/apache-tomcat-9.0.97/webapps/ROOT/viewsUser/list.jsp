 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11/15/2024
  Time: 9:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List User</title>
</head>
<body>
<h3>List User</h3>
<table border="1">
<thead>
    <th>No</th>
    <th>User ID</th>
    <th>User Name</th>
    <th>Password</th>
    <th>Email</th>
    <th>Full Name</th>
    <th>Status</th>
    <th>Created At</th>
    <th>Selection</th>
</thead>
<tbody>
<c:forEach items="${users}" var="user" varStatus="loop">
    <tr>
<%--        theo entity --%>
        <td>${loop.index+1}</td>
        <td>${user.userId}</td>
        <td>${user.username}</td>
        <td>${user.password}</td>
        <td>${user.email}</td>
        <td>${user.fullName}</td>
        <td>${user.status}</td>
        <td>${user.createdAt}</td>
        <td>
            <a href="UserController?userId=${user.userId}&action=initUpdate">Update</a>
            <a href="CategoriesController?catalogId=${user.userId}&action=delete">Delete</a>
        </td>
    </tr>
</c:forEach>
</tbody>
</table>
<a href="UserController?action=create">Create new User</a>
</body>
</html>
