<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- test 폴더에 넣어주세요  --%>
<%
    request.setCharacterEncoding("utf-8");
    String prdName = request.getParameter("prdName");

	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
     String pw_mysql = "qwer1234";
     
    String WhereDefault = "select prdNo, prdName, ctgType, prdBrand, prdPrice, prdFilename, prdDFilename, prdNFilename from product";
    String Condition = " where prdName = " + prdName;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault + Condition); // 
%>
		{ 
  			"product_select"  : [ 
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
			"prdName" : "<%=rs.getString(2) %>",   
			"ctgType" : "<%=rs.getString(3) %>",
			"prdBrand" : "<%=rs.getString(4) %>",  
            "prdPrice" : "<%=rs.getString(5) %>",
            "prdFilename" : "<%=rs.getString(6) %>",
            "prdDFilename" : "<%=rs.getString(7) %>",
            "prdNFilename" : "<%=rs.getString(8) %>"
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
