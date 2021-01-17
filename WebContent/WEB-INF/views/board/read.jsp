<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">


</head>


<body>
	<div id="wrap">

		<!-- header + navi 공통으로 옮겼음 -->		
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="read">
					<form action="#" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${boardVo.name}</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${boardVo.hit}</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${boardVo.date}</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${boardVo.title}</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >
								${boardVo.content}<br>
							</span>
						</div>
						<!-- 수정 버튼이 작성한 사람에게만 보이도록 -->
						<c:choose>
							<c:when test="${empty sessionScope.authUser}">
								 <!-- 로그인 안되어 있을때  아무것도 안보임-->
													
							</c:when>
							<c:when test="${authUser.no == boardVo.user_no}">
						        <a id="btn_modify" href="/mysite2/bc?action=d_modifyForm&no=${boardVo.no}">수정</a>
							</c:when>
						</c:choose>
						
						<a id="btn_modify" href="/mysite2/bc?action=list">목록</a>
						<!-- 로그인한 사람의 user_no와 작성한 사람의 user_no가 일치할때 수정버튼 생겨나기  bno게시판번호-->
						<input type="hidden" name="no" value="${authUser.no}" >
						<input type="hidden" name="user_no" value="${boardVo.user_no}" >
						<input type="hidden" name="bno" value="${boardVo.no}" >
						
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- footer 공통으로 옮겼음 -->		
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	</div>
	<!-- //wrap -->

</body>

</html>
