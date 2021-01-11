package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	

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
		

}
