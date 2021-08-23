<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>게시판 목록</title>
</head>
<body>
<div>
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>내용</th>
		</tr>
		<c:forEach var="boardList"  items="${boardList }">
		<tr>
			<td>${boardList.boardNum} </td>
			<td>${boardList.title }</td>
			<td>${boardList.contents }</td>
		</tr>
		</c:forEach>
	</table>
</div>


</body>
</html>
