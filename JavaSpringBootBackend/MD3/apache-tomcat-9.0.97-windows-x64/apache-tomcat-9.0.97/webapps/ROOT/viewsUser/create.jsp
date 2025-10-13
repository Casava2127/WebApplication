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
    <title>Create new User</title>
</head>
<body>
<form action="UserController?action=create" method="post">
    <!-- Trường nhập Tên đăng nhập -->
    <label for="userName">Tên đăng nhập:</label>
    <input type="text" id="userName" name="userName" required>
    <br><br>

    <!-- Trường nhập Email -->
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br><br>

    <!-- Trường nhập Mật khẩu -->
    <label for="password">Mật khẩu:</label>
    <input type="password" id="password" name="password" required>
    <br><br>

    <!-- Trường nhập Tên đầy đủ -->
    <label for="fullName">Họ và Tên:</label>
    <input type="text" id="fullName" name="fullName" required>
    <br><br>

    <!-- Trường chọn Trạng thái -->
    <label for="status">Trạng thái tài khoản:</label>
    <select id="status" name="status" required>
        <option value="1">Kích hoạt</option>
        <option value="0">Không kích hoạt</option>
    </select>
    <br><br>

    <!-- Nút gửi -->
    <button type="submit" value="create">Tạo người dùng</button>
</form>
</body>
</html>
