<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h1>상세 화면</h1>
<p>${data.nickname}님 환영합니다.</p>

<p><a href="/update?accountId=${accountId}">수정</a></p>

<form method="POST" action="/delete">
   <input type="hidden" name="accountId" value="${accountId}" />
   <input type="submit" value="삭제" />
</form>

<p>
   <a href="/list">목록으로</a>
</p>
</body>
</html>