<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
    border-style: solid;
    border-width: 1px;
    text-align : center;
}

ul{
    list-style-type: none;
}

li {
    display: inline-block;
}
</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<meta charset="UTF-8">
<title>게시판 메인 페이지</title>
</head>
<body>
    <h1>게시판 메인</h1>
    <c:if test="${empty sessionScope.userid }">
        <a href="login.go">로그인</a>
    </c:if>
    <c:if test="${not empty sessionScope.userid }">
        ${userid }님, 환영합니다!!<a href="logout.go">로그아웃</a>
    </c:if>
    <br>
    <a href="register.do"><input type="button" value="글작성"></a>
    <hr>
    <table>
        <thead>
          <tr>
              <th style="width: 60px">번호</th>
              <th style="width: 700px">제목</th>
              <th style="width: 60px">작성자</th>
              <th style="width: 100px">작성일</th>
          </tr>
        </thead>
        <tbody>
            <c:forEach var="vo" items="${list }">
                <tr>
                    <td>${vo.bno }</td>
                    <td><a href="detail.do?bno=${vo.bno }">${vo.title }</a></td>
                    <td>${vo.userid }</td>
                    <td>${vo.cdate }</td>
                </tr> 
            
            </c:forEach>
        </tbody>        
    </table>
    
    <hr>
    <ul>
        <c:if test="${pageMaker.hasPrev }">
            <li><a href="list.do?page=${pageMaker.startPageNo - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startPageNo }" 
        end="${pageMaker.endPageNo }" var="num">
            <li><a href="list.do?page=${num }">${num }</a></li>
        </c:forEach>
        <c:if test="${pageMaker.hasNext }">
            <li><a href="list.do?page=${pageMaker.endPageNo + 1}">다음</a></li>
        </c:if>
    </ul>

</body>
</html>















