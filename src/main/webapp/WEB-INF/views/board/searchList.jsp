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
			<td><p>${boardList.boardNum}</p> 
			<input type="hidden" name="boardNum" value = "${boardList.boardNum} ">
			</td>
			<td><a href = "/board/selectBoard?boardNum=${boardList.boardNum}">${boardList.title}</a></td>
			<td>${boardList.contents }</td>
		</tr>
		</c:forEach>
	</table>
	<!-- 
	<c:forEach var="totalNumList" items="${totalNumList }">
		<a href = "/board/searchBoard?pageNum=${totalNumList }" >${totalNumList }</a>
	</c:forEach>	
	 -->
	<form action ="/board/searchBoard" method = "get">
	<p><input type ="text" name="contents"><input type="submit" value = "검색" ></p>
	</form>
</div>
<div>
<a href ="/board/insertBoardForm">새 글 쓰기</a>

</div>


</body>
</html>
