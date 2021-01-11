package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


		}else if("loginForm".equals(action)) {
			System.out.println("로그인 폼");
						
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}else if("modifyForm".equals(action)) {
			System.out.println("수정 폼");
			
			
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		}
				
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
