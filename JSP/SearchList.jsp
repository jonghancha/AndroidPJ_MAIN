<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- test 폴더에 넣어주세요  --%>
<%
    request.setCharacterEncoding("utf-8");
    String searchText = request.getParameter("search_text");

	String url_mysql = "jdbc:mysql://localhost/one?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select * from product";

    String Condition = " where (prdName LIKE '%" + searchText + "%' OR " +
                        "ctgType LIKE '%" + searchText + "%' OR " +
                        "prdBrand LIKE '%" + searchText + "%' OR " +
			"prdPrice LIKE '%" + searchText + "%') order by prdName asc";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault + Condition); // 
%>
		{ 
  			"like_select"  : [ 
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
			"prdColor" : "<%=rs.getString(3) %>", 
			"ctgType" : "<%=rs.getString(4) %>",
			"prdBrand" : "<%=rs.getString(5) %>",  
            		"prdPrice" : "<%=rs.getString(6) %>",
            		"prdFilename" : "<%=rs.getString(7) %>",
            		"prdDFilename" : "<%=rs.getString(8) %>",
            		"prdNFilename" : "<%=rs.getString(9) %>"
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
