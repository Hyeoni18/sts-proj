<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<h1>목록</h1>

<form>  
	<input type="text" placeholder="검색" name="keyword" value="${keyword}" />  
	<input type="submit" value="검색" />  
</form>  

<table>  
	<thead>  
		<tr>  
			<td>닉네임</td>  
			<td>가입일자</td>  
		</tr>  
	</thead>  
	<tbody>  
		<c:forEach var="row" items="${data}">  
			<tr>  
				<td>  
					<a href="/detail?bookId=${row.account_id}">
						${row.nickname}  
					</a>  
				</td>  
				<td>${row.insert_date}</td>  
			</tr>  
		</c:forEach>  
	</tbody>  
</table>  

<p>  
	<a href="/create"> 생성 </a>  
</p>  

</body>
</html>