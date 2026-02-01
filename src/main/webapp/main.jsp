<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AccountDTO" %>
<%
    AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인보드</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= user.getNickname() %>님 환영합니다!</h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
        </div>
        
        <div class="level-section">
            <h2>한자 공부</h2>
            <p class="sub-text">단계별로 탄탄하게!</p>
            <div class="level-buttons">
                <button class="level-btn n5" onclick="location.href='sectorSelect.jsp?level=N5'">N5</button>
                <button class="level-btn n4" onclick="location.href='sectorSelect.jsp?level=N4'">N4</button>
                <button class="level-btn n3" onclick="location.href='sectorSelect.jsp?level=N3'">N3</button>
                <button class="level-btn n2" onclick="location.href='sectorSelect.jsp?level=N2'">N2</button>
                <button class="level-btn n1" onclick="location.href='sectorSelect.jsp?level=N1'">N1</button>
            </div>
        </div>
        
        <div class="menu-section">
            <a href="#" class="menu-card note">
                <h3>📝 오답노트</h3>
                <p>틀린 문제를 한 눈에!</p>
            </a>
            <a href="#" class="menu-card review">
                <h3>🎯 복습 테스트</h3>
                <p>오답 중심으로 복습 가능!</p>
            </a>
        </div>
        
        <div class="calendar-section">
            <h3>📅 출석 현황</h3>
            <p class="sub-text">출석 캘린더 (추후 구현 예정)</p>
        </div>
        
        <button class="logout-btn" onclick="location.href='LogoutCon.do'">로그아웃</button>
    </div>
</body>
</html>