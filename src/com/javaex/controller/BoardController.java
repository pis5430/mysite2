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
				
			
		}else if("read".equals(action)) { //이 페이지에 들어갈대마다 해당 조회수가 1씩 올라감
			
			System.out.println("게시판 글 읽기");
			//해당 이름, 조회수, 작성일 , 제목, 내용이 출력되야함
						
			//no를 이용해서 해당 내용을 출력하기			
			int no =Integer.parseInt(request.getParameter("no"));
			
			//dao
			BoardDao boardDao = new BoardDao();
			
			//조회수 올리기
			boardDao.hitCount(no);
			
			//글 정보를 불러옴
			BoardVo boardVo = boardDao.getBoardNo(no); //해당 넘버의 한명정보
			System.out.println(boardVo);
			
			request.setAttribute("boardVo", boardVo);
			
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

			
		}else if("d_modifyForm".equals(action)) {
			
			System.out.println("게시판 수정 폼");
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("writeForm".equals(action)) {
			
			System.out.println("게시판 쓰기");
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("delete".equals(action)) { //삭제기능
			
			System.out.println("게시글 삭제");
					
			//no를 이용해서 해당 내용 삭제		
			int no =Integer.parseInt(request.getParameter("no"));
			System.out.println();
			
		    //delete불러오기위한 dao선언
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			System.out.println("게시판번호 no:"+no); //count값 확인
			
			//삭제 실패시는 염두에 두지 않음 --> 본인것만 삭제 가능하게 , 본인글 삭제 버튼만 나오게 만들예정	
			WebUtil.rdirecte(request, response, "/mysite2/bc?action=list");// WebUtil사용
					
		}else if("addList".equals(action)) {
			System.out.println("게시판 등록");
			
			//제목, 내용 
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("userNo"));
			
			//vo로 묶음
			BoardVo boardVo = new BoardVo(title,content,no);
			
			BoardDao boardDao = new BoardDao();
			
			//저장
			boardDao.boardInsert(boardVo);
			System.out.println(boardVo);
			
			WebUtil.rdirecte(request, response, "/mysite2/bc?action=list");// WebUtil사용

			
		}
		
		
		
		
		
		
}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
