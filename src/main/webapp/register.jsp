<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h2>회원가입</h2>

    <c:choose>
        <c:when test="${param.error == 'empty'}">
            <p class="error">모든 항목을 입력해주세요.</p>
        </c:when>
        <c:when test="${param.error == 'id'}">
            <p class="error">이미 존재하는 아이디입니다.</p>
        </c:when>
        <c:when test="${param.error == 'nickname'}">
            <p class="error">이미 사용 중인 닉네임입니다.</p>
        </c:when>
        <c:when test="${param.error == 'pw'}">
            <p class="error">비밀번호가 일치하지 않습니다.</p>
        </c:when>
    </c:choose>

    <form action="RegisterCon.do" method="post">
        <p>닉네임: <input type="text" name="nickname" required></p>
        <p>이메일(아이디): <input type="email" name="email" required></p>
        <p>패스워드: <input type="password" name="userPw" required></p>
        <p>패스워드 확인: <input type="password" name="userPw2" required></p>
        <p><input type="submit" value="가입하기"></p>
    </form>
    <a href="login.jsp">로그인으로 이동</a>
</body>
</html>
