package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		// method="post" 일때 한글깨짐 현상을 해결하는 법 , getParameter 전에 실행시켜야한다.
		request.setCharacterEncoding("UTF-8"); 
		
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action=" + action); //action값 확인
		
		
		if("joinForm".equals(action)) { //
			
			System.out.println("회원가입 폼");

			//indev.html 포워드(유틸이용)
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		}else if("join".equals(action)) {
			System.out.println("회원가입");
			
			//dao --> insert() 저장
			
			//http://localhost:8088/mysite2/user?uid=pis1234&pw=1234&uname=1234&gender=male&action=join
			//		파라미터 값 꺼내기
			
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			//		vo로 묶기 -->vo만들기 기본 vo
			UserVo vo = new UserVo(id,password,name,gender);
			System.out.println(vo.toString());
			
			
			//		dao클래스 insert(vo) 사용 -->저장 --> 회원가입			
			UserDao userDao = new  UserDao();
			userDao.insert(vo); //dao 선언하고 insert 메소드로 vo값을 그 안에 넣어줌
			
			
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");


		}else if("modifyForm".equals(action)) { // 수정품 , 수정기능 , no값으로 전체정보를 불러오는 기능
			//수정폼에 갓을때 id가 표시 되어야함 --> modify 기능으로 들어가기전에 표시되야함
			//순서중요 메인 -> 로그인폼 -> 회원정보수정 
			
			System.out.println("수정 폼");
			
			//힌트 : session에 있는 no값을 불러와야 한다.
			HttpSession session = request.getSession();
			UserVo auth = (UserVo)session.getAttribute("authUser"); 
			System.out.println("authVo :" + auth.toString());
			//형변환
			//로그인 표시할때 사용하는 authUser에서 no값 가져오기
			
			//dao
			UserDao userDao = new UserDao();
			
			//유저 한명 정보의 no를 불러옴 getUserNo() -->한명을 불러오는 값은 필요함. 
			UserVo userVo = userDao.getUserNo(auth.getNo()); // no값에 해당하는 한명의 정보
			System.out.println("userVo :" + userVo);
			
			
			//한명의 정보를 modifyForm에서 각각의 값을 불러올수 잇도록 보내줘야함 //session으로 통일
			session.setAttribute("userNo", userVo);			
			
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		}else if("modify".equals(action)) {
			
			//error:java.sql.SQLSyntaxErrorException: ORA-00971: missing SET keyword
			//수정이 안됨
			
			System.out.println("수정");
			
			//힌트 : session에 있는 no값을 불러와야 한다.
			HttpSession session = request.getSession();
			UserVo auth = (UserVo)session.getAttribute("authUser"); 
			
			//파라미터  id는 변경못함 no는 섹션에서
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			int no = auth.getNo();
			
			UserVo userVo = new UserVo(no,id,pw,name,gender);
			UserDao userDao = new UserDao();
			
			userDao.UserModify(userVo); //정보 업데이트
			System.out.println(userVo); //수정된 값을 가지고 있음 
			
			//수정된 값을 다른곳에도 불러와야함 (로그인 이름)--> no값도 일단 불러오자
			UserVo upVo = userDao.getUserNo(userVo.getNo());
			System.out.println("upVo :" + upVo);
			
			session.setAttribute("upVo", upVo);
			
			//WebUtil.rdirecte(request, response, "/mysite2/main");
			
			WebUtil.rdirecte(request, response, "/mysite2/main");// WebUtil사용
			
		}else if("login".equals(action)) {
			
			System.out.println("로그인 ");
			
			//파라미터 id,pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			//dap --? getUser();
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw); //
			System.out.println(authVo);
			
			if(authVo == null) {// 로그인실패
				
				System.out.println("로그인 실패"); //리다이렉트 --> 로그인폼
				WebUtil.rdirecte(request, response, "/mysite2/user?action=loginForm");
				
			}else { //성공일때
				
				System.out.println("로그인 성공");
	
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				System.out.println(authVo);
				
				WebUtil.rdirecte(request, response, "/mysite2/main");
			
			}
			
			
			
		}else if("loginForm".equals(action)) {
			
			System.out.println("로그인 폼");
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}else if("logout".equals(action)) {
			
			System.out.println("로그아웃");
			//세션 영역에 있는 vo를 삭제해야함 
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.rdirecte(request, response, "/mysite2/main");
		}
				
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
