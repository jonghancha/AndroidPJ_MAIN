<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String user_userEmail = request.getParameter("userEmail");
	String ordReceiver = request.getParameter("ordReceiver");	
	String ordRcvAddress = request.getParameter("ordRcvAddress");	
	String ordRcvPhone = request.getParameter("ordRcvPhone");	
	String orderRequest = request.getParameter("orderRequest");	
	String ordPay = request.getParameter("ordPay");	
	String ordCardNo = request.getParameter("ordCardNo");	
	String ordCardPass = request.getParameter("ordCardPass");	
	
		
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
	
	    String A = "insert into orderinfo (user_userEmail, ordDate, ordReceiver, ordRcvAddress, ordRcvPhone, orderRequest, ordPay, ordCardNo, ordCardPass) values (";
	    String B = "?, NOW(), ?, ?, ?, ?, ?, ?, ?)";
		
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, user_userEmail);
	    ps.setString(2, ordReceiver);
		ps.setString(3, ordRcvAddress);
		ps.setString(4, ordRcvPhone);
		ps.setString(5, orderRequest);
		ps.setString(6, ordPay);
		ps.setString(7, ordCardNo);
		ps.setString(8, ordCardPass);
		
		
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

