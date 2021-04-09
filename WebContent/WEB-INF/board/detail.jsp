<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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
       <c:if test="${sessionScope.userid eq vo.userid }">
          <a href="update.do?bno=${vo.bno }"><input type="button" value="글수정"></a>
          <a href="delete.do?bno=${vo.bno }"><input type="button" value="글삭제"></a>
       </c:if>
    </div>
    <hr>
    
    <c:if test="${empty userid }">
        * 댓글은 로그인이 필요한 서비스입니다.
        <a href="login.go"></a>
    </c:if>
    <c:if test="${not empty userid }">
        ${userid }님, 이제 댓글을 작성할 수 있어요 !
    </c:if>
    <div style="text-align: center">
        <div>
        <input type="hidden" id="replyBno" value="${vo.bno }">
        <c:if test="${not empty userid }">
            <input type="text" id="replyId" value="${userid }" readonly="readonly">
            <input type="text" id="replyContent">
            <button type="button" id="btn_add">작성</button>        
        </c:if>
        </div>
    </div>
    <script type="text/javascript">
    	$(document).ready(function(){
    		
    		var replyBno = $('#replyBno').val();// 게시판 번호 값
    		// id= replyBno의 value 값을 가져옴
    		$('#btn_add').click(function(){
    			var replyContent = $('#replyContent').val();
    			var replyId = $('#replyId').val();
    			var obj = {
    					'replyBno' : replyBno,
    					'replyContent' : replyContent,
    					'replyId' : replyId
    			};// end obj data
    			
    			//$.ajax로 송수신
    			$.ajax({
    				type : 'post',
    				url : 'replies/add',
    				data : obj,
    				success : function(result){
    					if(result == 'success'){
    						alert('댓글 입력 성공');
    						$('#replyContent').val('');
    					}
    					
    				}
    			});//end ajax()
    			
    			//게시판 번호, 댓글 아이디, 댓글 내용의 값을 가져와서
    			//post 방식으로 전송
    			//url : 'replies/add'
    			//data : 게시판 번호(replyBno), 댓글 내용(replyContent), 댓글 아이디(replyId)
    		}); // end btn_add.click()
    			
    	}); // end document
    </script>    
</body>
</html>











