<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- test 폴더에 넣어주세요  --%>
<%
    request.setCharacterEncoding("utf-8");
    String user_email = request.getParameter("user_userEmail");

	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
     String pw_mysql = "qwer1234";
     
    String WhereDefault = "select userColor from user";
    String Condition = " where userEmail = '" + user_email + "'";
    int count = 0;
    String result = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault + Condition); // 
%>
		
<%
        while (rs.next()) {
            if (count == 0) {
%>
        { 
            "userColor_select"  : "<%=rs.getString(1) %>"
<%            
            }else{
%>
            , 
<%
            }
%>            
			
			
			 
			

<%		
        count++;
        }
%>
		  
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
