<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String prdNo = request.getParameter("prdNo");

	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String A = "select prdNo, prdBrand, prdName, prdPrice, prdFilename ";
    String B = "from product ";
    String C = "where prdNo = '" + prdNo + "'";


    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(A + B + C);
%>
		{ 
  			"cart_info"  : [ 
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
            "prdNo" : "<%=rs.getString(1) %>", 
			"prdBrand" : "<%=rs.getString(2) %>", 
			"prdName" : "<%=rs.getString(3) %>", 
			"prdPrice" : "<%=rs.getString(4) %>", 
			"prdFilename" : "<%=rs.getString(5) %>"
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
