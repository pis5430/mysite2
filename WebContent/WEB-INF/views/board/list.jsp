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
				<div id="list">
					<form action="" method="">
						<div class="form-group text-right">
							<input type="text">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					<table >
						<colgroup>
						<col style="width: 5%;">
						<col style="width: 50%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 20%;">
						<col style="width: 5%;">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
						<!-- 반복문 처리  -->
						<c:forEach items="${bList}" var="vo" varStatus="status">
							<tr>
								<td>${vo.no}</td>
								<td class="text-left"><a href="/mysite2/bc?action=read&no=${vo.no}">${vo.title}</a></td>
								<td>${vo.name}</td>
								<td>${vo.hit}</td>
								<td>${vo.date}</td>
								<td>
									<!-- 로그인시에만 보이도록  -->
									<c:choose>
										<c:when test="${empty sessionScope.authUser}">
										      <!-- 로그인 안되어 있을때  아무것도 안보임-->
													
										</c:when>
										<c:when test="${authUser.no == vo.user_no}">
						                 	<a href="/mysite2/bc?action=delete&no=${vo.no}">[삭제]</a>
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
						<!-- /반복문처리 -->	
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>					
						<!-- 로그인시에만 보이도록  -->
						<c:choose>
							<c:when test="${empty sessionScope.authUser}">
							 <!-- 로그인 안되어 있을때  아무것도 안보임-->												
							</c:when>
							<c:otherwise>
								<a id="btn_write" href="/mysite2/bc?action=writeForm">글쓰기</a>
							</c:otherwise>
						</c:choose>
				
				
				</div>
				<!-- //list -->
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
