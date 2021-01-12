package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;


@WebServlet("/gbc") //방명록 기능 (등록 add , 등록폼 addlist, 삭제 delete , 삭제폼deleteForm )
public class GuestbookController extends HttpServlet {
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// method="post" 일때 한글깨짐 현상을 해결하는 법 , getParameter 전에 실행시켜야한다.
		request.setCharacterEncoding("UTF-8"); 		
		
		System.out.println("GuestBookController"); //연결 확인
		
		String action = request.getParameter("action");
		System.out.println("action=" + action); //action값 확인
		
		if("addList".equals(action)) { // 등록 및 리스트 출력
			
			//리스트 출력처리
		    GuestBookDao guestDao = new GuestBookDao();
			List<GuestBookVo> guestList = guestDao.getGuestList();
			   		   
			//데이터 전달 (리퀘스트 어트리뷰트)
			request.setAttribute("gList", guestList);
			   
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
			
		}else if("add".equals(action)) {
			
			System.out.println("방명록 등록");
			
			//파라미터 3개값 꺼내기 
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			//Vo 묶고			
			GuestBookVo guestVo = new GuestBookVo(name,password,content);
			
			//new dao 만들고			
			GuestBookDao guestDao = new GuestBookDao();
			
			//dao guestInsert() 에 저장 			
			guestDao.guestInsert(guestVo);
			
			WebUtil.rdirecte(request, response, "/mysite2/gbc?action=addList");// WebUtil사용
			
			
		}else if("delete".equals(action)) { //삭제기능
			
			System.out.println("방명록 삭제");
			
		    //패스워드 값을 받아와야함 (password)
			String password = request.getParameter("pass");
			int no = Integer.parseInt(request.getParameter("no"));
					
			GuestBookVo guestVo = new GuestBookVo(no ,password); //vo안에 넣어줌
			
		    //delete불러오기위한 dao선언
			GuestBookDao guestDao = new GuestBookDao();
			int count = guestDao.guestDelete(guestVo); //count 0일때 비밀번호 틀림, 1일때 삭제성공
			System.out.println("count:"+count); //count값 확인
			
			if(count == 0) { //삭제실패
				 System.out.println("비밀번호가 틀립니다.");

				 //포워드를 유틸에 넣어서 포워드 메소드로 이용하기
				 WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
				 //실패문구 나오게 하는걸 실패해서 일단은 deleteForm화면이 나오도록 설정함
				 
			}else {//삭제성공
				
				 WebUtil.rdirecte(request, response, "/mysite2/gbc?action=addList");// WebUtil사용
			}
			  // count값 여부로 삭제 성공 , 삭제 실패를 나타낼수 있다. 페이지를 따로 만들지 않고 
			  // count값을 deleteForm으로 보내줘서 실패했습니다. 문구를 표기하는법 attribute 시도
			  // count값을 보내지 않고 deleteForm에서 pass값을 이용
			  // 팝업창 표시는 아마도 자바스크립트...

				
			
		}else if("deleteForm".equals(action)) { 
			
			System.out.println("방명록 삭제 폼");
			
			//포워드를 유틸에 넣어서 포워드 메소드로 이용하기
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}
		
		
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
