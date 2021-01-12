<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo" %>

<%

	int no = Integer.parseInt(request.getParameter("no")); // 삭제할 정보의 특정 번호 가져오기
	
	String password = request.getParameter("pass"); 
	//int count = (int)request.getAttribute("count");	 //int로 형변환
	//numberFormatExseption : null 오류??
			
	UserVo authUser = (UserVo)session.getAttribute("authUser");


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<div id="header">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>

			<%if(authUser == null){ %>
				<ul>
					<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
					<li><a href="/mysite2/user?action=modifyForm">회원가입</a></li>
				</ul>
			<%}else{ %>
				<!-- if 로그인 안햇으면  -->
				<ul>
					<li><%=authUser.getName() %> 님 안녕하세요^^</li>
					<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
					<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
				</ul>
			<%} %>
		</div>
		<!-- //header -->

		<div id="nav">
			<ul>
				<li><a href="/mysite2/gbc?action=addList">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->

		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li>일반방명록</li>
				<li>ajax방명록</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>일반방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">일반방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<form action="/mysite2/gbc" method="get">
					<table id="guestDelete">
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 25%;">
							<col style="width: 25%;">
						</colgroup>
						<tr>
							<td>비밀번호</td>
							<td><input type="password" name="pass"></td>
							<!-- pass값을 delete에서 getParameter값으로 불러와서 비밀번호 확인 -->
							<td class="text-left"><button type="submit">삭제</button></td>
							<td><a href="/mysite2/main">[메인으로 돌아가기]</a></td>
						</tr>
					</table>
					<!-- 
					java.lang.NumberFormatException: null 오류때문에 생략 count말고 pass값으로 비교해보기
					password의 초기값이 null , 
					비밀번호 입력시 delete에서 비밀번호 성공하면 addlist로 가고
					비밀번호 실패할시 else로 넘어가서 비밀번호 틀림 나오기 
					(원래는 password값과 입력값 pass를 비교하려 햇으나 이게 더 간단한거같음..) 
					-->
					<% if(password != null ) { %>
					
						<p>비밀번호가 틀립니다. 다시입력해주세요</p>
						
						<input type='hidden' name="pass" value="<%=password%>">
													
					<%}%> 
					

					
					<!-- action값 delete / no값과, pass값으로 비밀번호 확인 -->
					<input type='hidden' name="action" value="delete">
					<input type='hidden' name="no" value="<%=no %>">
				</form>	
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<div id="footer">
			Copyright ⓒ 2020 황일영. All right reserved
		</div>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>
