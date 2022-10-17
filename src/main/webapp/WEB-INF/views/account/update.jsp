<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h1>update view</h1>

<form method="POST">
   <p>닉네임 : <input type="text" name="nickname" value="${data.nickname}" /></p>
   <p>패스워드 : <input type="password" name="password" /></p>
   <p><input type="submit" value="저장" />
</form>
</body>
</html>