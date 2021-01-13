<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String goods_prdNo = request.getParameter("goods_prdNo");
	String user_userEmail = request.getParameter("userEmail");
	
		
//------
	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";


	int result = 0; // 삭제 확인 
	

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "delete from cartdetail";
        String B = " where goods_prdNo = ? and user_userEmail = ?";
	
	    ps = conn_mysql.prepareStatement(A+B);
	   
		ps.setString(1, goods_prdNo);
		ps.setString(2, user_userEmail);
	    
	    result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%	
	    e.printStackTrace();
	}
	
%>

