<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작성 글 확인 페이지</title>
</head>
<body>
    <h2>글보기</h2>
    <div>
        <p>글 번호 : ${vo.bno }</p>
    </div>
    <div>
        <p>
              제목 <input type="text" value="${vo.title }" readonly="readonly">
        </p>
    </div>
    <div>
        <p>작성자 : ${vo.userid }</p>
        <p>작성일 : ${vo.cdate }</p>
    </div>
    <div>
        <textarea rows="20" cols="120" readonly="readonly">${vo.content }</textarea>
    </div>
    <div>
    <a href="update.do?bno=${vo.bno }"><input type="button" value="글수정"></a>
    <a href="delete.do?bno=${vo.bno }"><input type="button" value="글삭제"></a>
    </div>
    
    
    
</body>
</html>