<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%> 
	
<%@ page 
import="com.oreilly.servlet.MultipartRequest" 
import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"
import="java.util.*" 
import="java.io.*"
import="java.text.SimpleDateFormat"
%> 
 
<% 
request.setCharacterEncoding("utf-8");
String user_userEmail = request.getParameter("userEmail");


String fileName;
 String realPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\ROOT\\Images"; // 저장할 디렉토리 (절대경로)

  int sizeLimit = 5 * 1024 * 1024;

  String now = new SimpleDateFormat("yyyyMMddHmsS").format(new java.util.Date());  //현재시간

  // 5메가까지 제한 넘어서면 예외발생 

try {
	MultipartRequest multi = new MultipartRequest(request, realPath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
	
	/* Enumeration formNames = multi.getFileNames(); // 폼의 이름 반환 
	String formName = (String) formNames.nextElement(); // 자료가 많을 경우엔 while 문을 사용 
	String fileName = multi.getFilesystemName(formName); // 파일의 이름 얻기 */ 
	/* multi.getFile("images"); */ 
	fileName = multi.getFilesystemName("image");
	
	request.setCharacterEncoding("utf-8");
	
		
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
	
	    String A = "update user set userFilename = ?";
	    String B = " where userEmail = ?";
	
	    ps = conn_mysql.prepareStatement(A + B);
	    ps.setString(1, fileName);
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
	
	} catch (Exception e) {
		out.print(e); out.print("예외 상황 발생..! ");
	}



%>
