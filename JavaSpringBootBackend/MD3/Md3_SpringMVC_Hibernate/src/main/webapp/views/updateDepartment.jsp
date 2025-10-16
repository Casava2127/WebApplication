
<%--
  Created by IntelliJ IDEA.
  User: This MC
  Date: 21/11/2024
  Time: 7:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Department</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/departmentController/update" method="post">
    Dept ID: <input type="text" id="deptId" name="deptId" value="${deptUpdate.deptId}" readonly/> <br>
    Dept Name: <input type="text" id="deptName" name="deptName" value="${deptUpdate.deptName}"/> <br>
    Description: <input type="text" id="description" name="description" value="${deptUpdate.description}"/> <br>
    Dept ID: <input type="text" id="status" name="status" value="${deptUpdate.status}"/> <br>
    <input type="submit" value="Update"/>
</form>
</body>
</html>
