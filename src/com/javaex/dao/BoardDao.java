package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

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
			
			//select  b.no 게시물번호,
	        //u.name 이름,
	        //b.title 제목,
	        //b.content 내용,
	        //b.hit 조회수,
	        //b.reg_date 등록일,
	        //b.user_no 회원번호
	        //from board b , users u
	        //where b.user_no = u.no;
			
			//name은 users에서 가져와야하고 pk와 fk 일치 확인 
			query += " select b.no, ";
			query += "        b.title, ";
			query += "        u.name, ";
			query += "        b.content, ";
			query += "        b.hit, ";
			query += "        to_char(b.reg_date,'YYYY-MM-DD HH24:MI') reg_date, "; //표시형식 변경
			query += "        b.user_no ";
			query += " from board b , users u ";
			query += " where b.user_no = u.no "; 
			query += " order by b.reg_date desc"; //정렬추가 게시판 정렬순서 작성순서대로
			
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
				System.out.println(bList);
			}
				
		}catch(SQLException e) {
			System.out.println("error:" + e );
		}
	
		//자원정리
		close();
				
		return bList;
	}
	
	
	
	//한명의 게시물 정보 가져오기 (read 게시물 번호 no값으로 가져오기 게시물번호) 
	
	public BoardVo getBoardNo(int no) { 
		
		BoardVo boardVo = null;
		
		getConnection();
		
		
		try {
			
			//3. sql문 준비 /바인딩 /실행
			
			//select no,
	        //id,
	        //password,
	        //name,
	        //gender
			//from users
			//where no=1
			
			String query = "";	
			query += " select b.no, ";
			query += "        u.name, ";
			query += "        b.hit, ";
			query += "        to_char(b.reg_date,'YYYY-MM-DD HH24:MI') reg_date, "; //표시형식 변경
			query += "        b.title, ";
			query += "        b.content, ";
			query += "        b.user_no ";
			query += " from board b , users u ";
			query += " where b.user_no = u.no "; 
			query += " and b.no = ?"; //게시판번호로 가져오기


			
			pstmt = conn.prepareStatement(query); //쿼리로 만들기
			pstmt.setInt(1, no); //물음표 순서 중요 ,1번째
			
			rs = pstmt.executeQuery(); //쿼리문 실행
			
			//4. 결과처리
			
			// System.out.println("[" +count+ "등록되었습니다.]");
			
			while(rs.next()) {
				int bno = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				
				boardVo = new BoardVo(bno,title,name ,content ,hit, reg_date,user_no);
			}				
			 
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return boardVo;			
	}
	
	
	//조회수 올리기
	
	public int hitCount(int no) {
		
		getConnection();
		
		int count =0;
		
		try {
			
			//3. sql문 준비 /바인딩 /실행
			
			//--게시물 조회수 업데이트
			//--update문
			//update board
			//set hit = hit+1
			//where no = 1;
			
			String query = "";	
			query += " update board ";
			query += " set hit = hit+1 ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query); //쿼리로 만들기
			pstmt.setInt(1, no); //물음표 순서 중요 ,1번째
			
			count = pstmt.executeUpdate(); //쿼리문 실행(count, rs랑 다르게 쓰는거 잊지말기)
			
			//4. 결과처리
			
			System.out.println("[" +count+ "등록되었습니다.]");
			

			 
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return count;

	}
}
