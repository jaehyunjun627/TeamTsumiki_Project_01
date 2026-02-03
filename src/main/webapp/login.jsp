<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <style>
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
    <h2>로그인</h2>
    
    <%
        String error = request.getParameter("error");
        String msg = request.getParameter("msg");
        
        if ("empty".equals(error)) {
    %>
        <p class="error">아이디와 비밀번호를 전부 입력해주세요.</p>
    <%
        } else if ("id".equals(error)) {
    %>
        <p class="error">존재하지 않는 아이디입니다.</p>
    <%
        } else if ("pw".equals(error)) {
    %>
        <p class="error">패스워드가 일치하지 않습니다.</p>
    <%
        } else if ("success".equals(msg)) {
    %>
        <p class="success">회원가입이 완료되었습니다. 로그인해주세요.</p>
    <%
        }
    %>
    
    <form action="LoginCon.do" method="post">
        <p>이메일: <input type="email" name="userId" required></p>
        <p>패스워드: <input type="password" name="userPw" required></p>
        <p><input type="submit" value="시작하기"></p>
    </form>
    <a href="register.jsp">회원가입으로 이동</a>
</body>
</html>