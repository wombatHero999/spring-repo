<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    img{
        width: 100px;
    }
</style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성</h2>
            <br>

            <form:form modelAttribute="b" action="${contextPath}/board/insert/${boardCd}"
            id="enrollForm" method="post" enctype="multipart/form-data">
                <table align="center">
                    <tr>
                        <th>제목</th>
                        <td>
                        <form:input path="boardTitle" cssClass="form-control" id="title" required="required" />
                        </td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td><sec:authentication property="principal.userName"/>
                        </td>
                    </tr>
                    <c:if test="${boardCd ne 'P' }">
                        <tr>
                            <th>첨부파일</th>
                            <td><input type="file" id="upfile" class="form-control" name="upfile"></td>
                        </tr>
                    </c:if>
                    <c:if test="${boardCd eq 'P'}">
                        <tr>
                            <th><label  for="image">업로드 이미지1</label></th>
                            <td>
                            <img class="preview" >
                            <input type="file" name="upfile" class="form-control inputImage" accept="images/*" id="img1">
                            <span class="delete-image">&times;</span>
                            </td>
                        </tr>
                        <tr>
                            <th><label  for="image">업로드 이미지2</label></th>
                            <td>
                            <img class="preview" >
                            <input type="file" name="upfile" class="form-control inputImage" accept="images/*" id="img2">
                            <span class="delete-image">&times;</span>
                        </tr>
                        <tr>
                            <th><label  for="image">업로드 이미지3</label></th>
                            <td>
                            <img class="preview">
                            <input type="file" name="upfile" class="form-control inputImage" accept="images/*" id="img3">
                            <span class="delete-image">&times;</span>
                            </td>
                        </tr>
                        <tr>
                            <th><label  for="image">업로드 이미지4</label></th>
                            <td>
                            <img class="preview" >
                            <input type="file" name="upfile" class="form-control inputImage" accept="images/*" id="img4">
                            <span class="delete-image">&times;</span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>내용</th>
                        <td>
                            <form:textarea path="boardContent" rows="10" cssClass="form-control" id="content" style="resize:none;" required="required"/>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <button type="submit" class="btn btn-primary">등록</button>
                    <button type="reset" class="btn btn-danger">취소</button>
                </div>
            </form:form>
        </div>
    </div>
    <!--  zz -->


    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>