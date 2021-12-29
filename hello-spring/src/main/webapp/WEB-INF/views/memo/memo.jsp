﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="메모" name="title"/>
</jsp:include>

<style>
div#memo-container{width:60%; margin:0 auto;text-align:center;}
</style>
<div id="memo-container">
    <form action="${pageContext.request.contextPath}/memo/insertMemo.do" class="form-inline" method="post">
        <input type="text" class="form-control col-sm-8" name="memo" placeholder="메모" required/>&nbsp;
        <input type="password" class="form-control col-sm-2" name="password" maxlength="4" placeholder="비밀번호" required/>&nbsp;
        <button class="btn btn-outline-success" type="submit" >저장</button>
    </form>

    <br />
    <!-- 메모목록 -->
	<table class="table">
	    <tr>
	      <th>번호</th>
	      <th>메모</th>
	      <th>날짜</th>
	      <th>삭제</th>
	    </tr>
	    <c:forEach items="${memoList }" var="memo" varStatus="vs">
	    	<tr>
	    		<td>${memo.no }</td>
	    		<td>${memo.memo }</td>
	    		<td><fmt:formatDate value="${memo.regDate }" pattern="yyyy/MM/dd (E)"/> </td>
	    		<td>
	    			<form action="${pageContext.request.contextPath }/memo/deleteMemo.do" method="POST">
						<input type="hidden" name="memoNo" value="${memo.no }" />	    			
	    				<input type="submit" value="삭제하기" />
	    			</form>
    			</td>
	    	</tr>
	    
	    </c:forEach>
	</table>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
