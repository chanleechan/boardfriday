<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>상세 내용</title>
</head>

<body>
<div>	
	<form action = "/board/updateBoard" method = "get" name = "boardForm">
		<p>글번호 : ${selectBoard.boardNum }
		<input type = "hidden"  name = "boardNum" value = " ${selectBoard.boardNum }">
		</p>
		<p>제목<input type = "text" name ="title" value = "${selectBoard.title }"></p>
		<p>내용<textarea name = "contents"  >${selectBoard.contents }</textarea></p>
		<input type="submit" value = "수정하기">
	</form>
	<form action = "/board/deleteBoard" method = "get">
		<input type="hidden" name="boardNum" value = "${selectBoard.boardNum }">
		<input type = "submit"  value = "삭제하기">
	</form>
	<form action ="/board/replyBoard" method = "post">
		<input type ="hidden" name = "boardNum" value = "${selectBoard.boardNum }">
		<p>제목<input type ="text" name = "title"></p>
		<p>내용<textarea name ="contents" ></textarea></p>
		<input type ="submit" value = "답글달기">
	</form>
	
</div>
<div>

</div>


</body>
</html>
