<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
    <h2>업데이트 페이지</h2>
    <form action="update.do?bno=${vo.bno }" method="POST">
        <div>
            <p>글 번호 : ${vo.bno}</p>
            <input type="hidden" name="bno" value="${vo.bno }">
        </div>
        <div>
            <p>제목 :
            <input type="text" name="title" value="${vo.title }" required="required">
            </p>
        </div>
        <div>
            
        <p>작성자 : ${vo.userid }</p>
        <p>작성일 : ${vo.cdate }</p>
        </div>
        <div>
            <textarea rows="20" cols="120" name="content" value="${vo.content }">${vo.content }</textarea>
        </div>
        <div>
            <input type="submit" value="등록 ">
        </div>
    </form>

</body>
</html>