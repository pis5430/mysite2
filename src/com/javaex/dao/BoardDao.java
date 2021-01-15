package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	
	//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs =null; //select문에 사용됨
	
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//생성자
	
	//디폴드 생성자 생략 (다른 생성자 없음)
	
	//메소드 g/s
	
	//메소드 일반
	
	//(공통되는부분 골라내기)
	
	//접속 메소드(DB접속)
	public void getConnection() {
		
		try {
			
			Class.forName(driver);
			
			//2. connection얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
			//System.out.println("[접속성공]");
			
		}catch(ClassNotFoundException e) {
			System.out.println("error:드라이버 로딩 실패 - " + e);
		}catch(SQLException e) {
			System.out.println("error:" + e );
		}	
		
	}
	
	//자원정리
	public void close() {	
		try {
			if(rs != null){
				 rs.close();
			}
				 
			if(pstmt != null) {
				pstmt.close();
					
			}
			if(conn != null) {
				conn.close();
			}
				
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	
	

	//게시판 관련
	
	//게시물 리스트 가져오기(board 데이터)
	
	public List<BoardVo> getBoradList(){
		
		List<BoardVo> bList = new ArrayList<BoardVo>();
		
		//db접속
		getConnection();	
		
		try {
			
			String query = "";
			
			//3.sql문 준비 / 바인딩 실행 (* 는 왠만하면 쓰지 않기)
			
			//--게시물식별번호,제목,내용,조회수,등록일,회원식별번호)
			//select  no 게시물번호,
	        //        title 제목,
	        //        content 내용,
	        //        hit 조회수,
	        //        reg_date 등록일,
	        //        user_no 회원번호
        	//from board;
			
			query += " select b.no, ";
			query += "        b.title, ";
			query += "        u.name, ";
			query += "        b.content, ";
			query += "        b.hit, ";
			query += "        b.reg_date, ";
			query += "        b.user_no ";
			query += " from board b , uesrs u ";
			query += " where b.user_no = u.no ";			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");

				
				BoardVo vo = new BoardVo(no,title,name ,content ,hit, reg_date,user_no);
				bList.add(vo);
			}
				
		}catch(SQLException e) {
			System.out.println("error:" + e );
		}
	
		//자원정리
		close();
				
		return bList;
	}
	
	
	

}
