package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserVo;

public class UserDao {
	

	//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; //select문에 사용됨
	
	
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
	

	//2. 등록 (select)
		public int insert(UserVo userVo) {
			
			getConnection();
			
			int count = 0;
			
			try {
				
				//3. sql문 준비 /바인딩 /실행
				String query = "";	
				query += " insert into users ";
				query += " values (seq_users_no.nextval, ";
				query += " 			?, ";
				query += " 			?, ";
				query += " 			?, ";
				query += " 			? ";
				query += " )";
				
				pstmt = conn.prepareStatement(query); //쿼리로 만들기
				pstmt.setString(1, userVo.getId()); //물음표 순서 중요 ,1번째
				pstmt.setString(2, userVo.getPassword());
				pstmt.setString(3, userVo.getName());
				pstmt.setString(4, userVo.getGender());
				
				count = pstmt.executeUpdate();
				
				//4. 결과처리				
				System.out.println("[" +count+ "등록되었습니다.]");	
				
			}catch(SQLException e) {
				System.out.println("error:" + e);
			}
			close();			
			return count;
		}
		
		public UserVo getUser(String id, String pw) { //아이디와 패스워드 값을 불러옴
			
			UserVo userVo = null;			
			getConnection();
						
			try {
				//3. sql문 준비 /바인딩 /실행
				
				//select no,
		        //id,
		        //password,
		        //name,
		        //gender
				//from users
				//where id='1111'
				//and password ='1111';
				
				String query = "";	
				query += " select no, ";
				query += " 		  name ";
				query += " from users ";
				query += " where id= ? ";
				query += " and password = ? ";
				
				pstmt = conn.prepareStatement(query); //쿼리로 만들기
				pstmt.setString(1, id); //물음표 순서 중요 ,1번째
				pstmt.setString(2, pw);
				
				rs = pstmt.executeQuery(); //쿼리문 실행
				
				//4. 결과처리
				
				// System.out.println("[" +count+ "등록되었습니다.]");
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String name =rs.getNString("name");
					
					userVo = new UserVo(no,name); // no값으로 조회하기 --> 수정폼
				}				
				 
			}catch(SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			
			return userVo;			
		}
		
		
		public UserVo getUserNo(int no) { //no 값으로 한명의 전체값을 불러옴
			
			UserVo userVo = null;
			
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
				query += " select no, ";
				query += " 		  id, ";
				query += " 		  password, ";
				query += " 		  name, ";
				query += " 		  gender ";
				query += " from users ";
				query += " where no= ? ";
				query += " or id= ? ";
				
				pstmt = conn.prepareStatement(query); //쿼리로 만들기
				pstmt.setInt(1, no); //물음표 순서 중요 ,1번째
				pstmt.setString(2, id); //물음표 순서 중요 ,1번째
				
				rs = pstmt.executeQuery(); //쿼리문 실행
				
				//4. 결과처리
				
				// System.out.println("[" +count+ "등록되었습니다.]");
				
				while(rs.next()) {
					int num = rs.getInt("no"); 
					String id = rs.getString("id");
					String pw = rs.getString("password");
					String name = rs.getString("name");
					String gender = rs.getString("gender");
					
					userVo = new UserVo(num, id, pw, name, gender);
				}				
				 
			}catch(SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			
			return userVo;			
		}

		
		
		public int UserModify(UserVo userVo) { //수정
		
			getConnection();
			
			int count =0;
			
			try {
				
				//3. sql문 준비 /바인딩 /실행
				
				//--update문
				//update users
				//set password = '1234',
				//    name = '테스트',
				//    gender = 'female'
				//where no = 1;
				
				String query = "";	
				query += " update users ";
				query += " set password = ?, ";
				query += " 	   name = ?, ";
				query += "     gender = ? ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query); //쿼리로 만들기
				pstmt.setString(1, userVo.getPassword()); //물음표 순서 중요 ,1번째
				pstmt.setString(2, userVo.getName());
				pstmt.setString(3, userVo.getGender()); 
				pstmt.setInt(4, userVo.getNo());
				
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
