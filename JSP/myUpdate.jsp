<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String myEmail = request.getParameter("userEmail");
	String userName = request.getParameter("userName");
	String userTel = request.getParameter("userTel");
	String userColor = request.getParameter("userColor");	
		
//------
	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	String A = "update user set userName = ?, userTel = ?, userColor = ?";
        String B = " where userEmail = '" + myEmail + "'";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    
	    ps.setString(1, userName);
	    ps.setString(2, userTel);
	    ps.setString(3, userColor);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}
	
%>

