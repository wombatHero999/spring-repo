<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
img {
	width: 400px;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="content">
		<br>
		<br>
		<div class="innerOuter">
			<h2>게시글 상세보기</h2>
			<br>
			<table id="contentArea" align="center" class="table">
				<tr>
					<th width="100">제목</th>
					<td colspan="3">${board.boardTitle}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${board.userName }</td>
					<th>작성일</th>
					<td>${board.createDate }</td>
				</tr>
				<c:set var="imgList" value="${board.imgList}" />
				<c:if test="${not empty imgList }">
					<c:choose>
						<c:when test="${boardCd == 'P'}">
							<c:forEach var="i" begin="0" end="${fn:length(imgList) - 1}">
								<tr>
									<th>이미지${i+1 }</th>
									<td colspan="3"><a
										href="${contextPath }${imgList[i].changeName}"
										download="${imgList[i].originName }"> 
										<img
											src="${contextPath }${imgList[i].changeName}">
									</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<th>첨부파일</th>
								<td>
									<button type="button" class="btn btn-outline-success btn-block"
										onclick="location.href='${contextPath}/board/fileDownload/${board.boardNo }'">
										${imgList[0].originName } - 다운로드</button>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:if>
				<tr>
					<th>내용</th>
					<td></td>
					<th>조회수</th>
					<td>${board.count }</td>
				</tr>
				<tr>
					<td colspan="4">
						<p style="height: 150px;">${board.boardContent}</p>
					</td>
				</tr>
			</table>
			
			<c:set var="principal" value="${pageContext.request.userPrincipal }"/>
			<br>
			<sec:authorize access="hasRole('ROLE_ADMIN') or principal.userNo.toString() == #board.boardWriter">
			<div align="center">
				<a class="btn btn-primary"
					href="${contextPath }/board/update/${boardCd}/${board.boardNo}">수정하기</a>
			</div>
			</sec:authorize>
		</div>
	</div>
	<br><br>

    <table id="replyArea" class="table" align="center">
        <thead>
            <tr>
                <th colspan="2">
                    <textarea class="form-control" name="replyContent" id="replyContent" rows="2" cols="55"
                      style="resize:none; width:100%;"></textarea>
                </th>
                <th style="vertical-align:middle;">
                    <button class="btn btn-secondary" onclick="insertReply();">등록하기</button>
                </th>
            </tr>
            <tr>
                <td colspan="3">댓글(<span id="rcount">0</span>)</td>
            </tr>
        </thead>
        <tbody>


        </tbody>
    </table>
	
	<script>
		// 스프링시큐리티 추가시 모든 비동기 요청 중 post상태의 요청에 대해
		// 인증토큰을 보내야 한다.
		function insertReply(){
			$.ajax({
				url : '${contextPath}/reply/insert',
				type : 'POST',
				data : {
					replyContent : $("#replyContent").val(),
					refBno : '${board.boardNo}'
				},
				success : function(result) {
					//댓글조회 성공시 댓글목록 불러오기(비동기요청)
					selectReplyList();
					
					// 작성한 댓글내용 지우기
					$("#replyContent").val("");
				},
				error : function (error){
					console.log(error);	
				}				
			});
		} 	
		
		function selectReplyList(){
			$.ajax({
				url : "${contextPath}/reply/selectList",
				data : {
					boardNo : '${board.boardNo}'
				},
				success : function(result){
					console.log(result);
					// 객체배열
					let html = "";
					for(let reply of result){
						html += 
						(`<tr>
							<td>\${reply.replyWriter}</td>
							<td>\${reply.replyContent}</td>
							<td>\${reply.createDate}
						`)
						if(reply.replyWriter == <sec:authentication property="principal.userNo" /> ){
							html +=	`<button onclick='deleteReply(\${reply.replyNo})'>삭제</button>`
						}
						html += (`</td>
							</tr>`);
					}
					$("#replyArea tbody").html(html);
					$("#rcount").html(result.length);
				},
				error : function(xhr){
					console.log(xhr);	
				}
			})
		}
		
		function deleteReply(replyNo){
			if(confirm("정말로 삭제하시겠습니까?")){
				$.ajax({
					url : '${contextPath}/reply/delete/'+replyNo,
					type : 'POST' ,
					success : function(){
						alert("삭제 성공");
						selectReplyList();
					},
					error : function(xhr){
						console.log(xhr);
						alert("삭제 실패");
					}					
				})
			}
		}
		
		
		selectReplyList();
	</script>
	
	
	
	
	
	
	
	
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

</body>
</html>