<%--
  Created by IntelliJ IDEA.
  User: This MC
  Date: 12/11/2024
  Time: 6:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new categories</title>
</head>
<body>
<form action="CategoriesController?action=create" method="post">
    <label for="catalogName">Catalog Name</label>
<%--     con trên này thì for theo id bên dưới--%>
    <input type="text" id="catalogName" name="catalogName"/><br/>
<%--    vì sao lại dùng định dang id và name, id dùng trong css và js để điều chỉnh, còn name thì dùng trên server ( khi submit lên srver thì thông qua định danh name--%>
    <label for="description">Description</label>
    <input type="text" id="description" name="description"/><br/>
    <label>Status</label>
    <input type="radio" id="active" name="status" value="true" checked/><label for="active">Active</label>
    <input type="radio" id="inActive" name="status" value="false"/><label for="inActive">Inactive</label><br>
    <input type="submit" value="Create"/>
</form>
</body>
</html>
