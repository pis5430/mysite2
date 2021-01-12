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
		
		
		if("joinForm".equals(action)) {

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


		}else if("modifyForm".equals(action)) {
			System.out.println("수정 폼");
			
			
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

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
