<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<link rel="stylesheet" type="text/css" href="../css/member.css">
</head>
<body>

<div class="container">
		<form action="login_process.jsp" method="post">
				<label>이름</label>
				<input type="text" name="username" placeholder="이름 입력" required /><br />
			
				<label>비밀번호</label>
				<input type="password" name="password" placeholder="비밀번호 입력" required><br />
						
				<p></p>
				<input type="submit" value="로그인"/>
				<p></p>

		</form>
		
    <p>계정이 없으신가요? <a href="register.jsp">회원가입</a></p>
</div>


</body>
</html>