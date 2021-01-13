<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
	String tipTitle = request.getParameter("tipTitle");

	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select * from honeytip where tipTitle = ?";
    int count = 0;

PreparedStatement ps = null;
    ResultSet rs =null;	
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();		

        ps = conn_mysql.prepareStatement(WhereDefault);
ps.setString(1, tipTitle);
	rs = ps.executeQuery();
%>
		{ 
  			"tip_select"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			
			{
			"tipNo" : "<%=rs.getString(1) %>", 
			"tipTitle" : "<%=rs.getString(2) %>",  
			"tipContent" : "<%=rs.getString(3) %>", 
			"tipImg" : "<%=rs.getString(4) %>"
			}

<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
