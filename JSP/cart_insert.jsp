<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String user_userEmail = request.getParameter("userEmail");
	String cartQty = request.getParameter("cartQty");
	String goods_prdNo = request.getParameter("prdNo");	
	
		
//------
	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into cartdetail (cartinfo_cartNo, user_userEmail, cartQty, goods_prdNo) values (";
	    String B = "(select cartNo from cartinfo where user_userEmail = ?)";
		String C = ", ?, ?, ?)";
	
	    ps = conn_mysql.prepareStatement(A+B+C);
	    ps.setString(1, user_userEmail);
	    ps.setString(2, user_userEmail);
	    ps.setString(3, cartQty);
		ps.setString(4, goods_prdNo);
		
		
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

