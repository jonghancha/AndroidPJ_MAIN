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
     
    String WhereDefault = "select o.ordNo, o.ordDate, o.ordDelivery, p.prdName, p.prdPrice, p.prdFileName from orderinfo o, orderdetail d, product p";
    String Condition = " where o.ordNo = d.orderinfo_ordNo and d.goods_prdNo = p.prdNo and o.user_userEmail = '" + user_email + "'";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault + Condition); // 
%>
		{ 
  			"order_select"  : [ 
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
            "ordNo" : "<%=rs.getString(1) %>", 
			"ordDate" : "<%=rs.getString(2) %>", 
			"ordDelivery" : "<%=rs.getString(3) %>",  
			"prdName" : "<%=rs.getString(4) %>", 
			"prdPrice" : "<%=rs.getString(5) %>",
			"prdFileName" : "<%=rs.getString(6) %>"  
        
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
