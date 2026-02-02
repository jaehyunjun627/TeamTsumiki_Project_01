<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login_process</title>
<link rel="stylesheet" type="text/css" href="../css/member.css">
</head>
<body>
	<jsp:useBean class="model.MemberDAO" id="mdao" />

	<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");

/* 	boolean isValid = mdao.checkLogin(username, password);

	if (isValid) {
		session.setAttribute("username", username);
		response.sendRedirect("main.jsp");
	} else {
		response.sendRedirect("login.jsp?error=1");
	} */
	%>


</body>
</html>