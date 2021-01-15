package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/bc")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// method="post" 일때 한글깨짐 현상을 해결하는 법 , getParameter 전에 실행시켜야한다.
		request.setCharacterEncoding("UTF-8"); 		
		
		System.out.println("BoardController"); //연결 확인
		
		String action = request.getParameter("action");
		System.out.println("action=" + action); //action값 확인
		
		if("list".equals(action)) {
			
			System.out.println("게시판 리스트 출력");
			
			//리스트 출력
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoradList();
			
			//데이터 전달
			request.setAttribute("bList", boardList);
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				
			
		}else if("writeForm".equals(action)) {
			
			System.out.println("게시판 글 읽기");
			
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

			
		}

		
		
		
		
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
