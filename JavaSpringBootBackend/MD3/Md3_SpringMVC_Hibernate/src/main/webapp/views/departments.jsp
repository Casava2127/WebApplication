<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: This MC
  Date: 21/11/2024
  Time: 7:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Department</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>No</th>
        <th>Department Id</th>
        <th>Department Name</th>
        <th>Description</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${listDepartments}" var="dept" varStatus="loop">
        <tr>
            <td>${loop.index+1}</td>
            <td>${dept.deptId}</td>
            <td>${dept.deptName}</td>
            <td>${dept.description}</td>
            <td>${dept.status?"Active":"Inactive"}</td>
            <td>
                <a href="<%=request.getContextPath()%>/departmentController/initUpdate?deptId=${dept.deptId}">Update</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>