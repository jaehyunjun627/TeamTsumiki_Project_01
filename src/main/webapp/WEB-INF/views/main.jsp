<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인보드</title>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css"/>
</head>
<body>
    <div class="container">
        <!-- ========== 헤더 ========== -->
        <div class="header">
            <h1>${sessionScope.loginUser.nickname}님 환영합니다!</h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
        </div>

        <!-- ========== 한자 공부 섹션 ========== -->
        <div class="level-section">
            <h2>한자 공부</h2>
            <p class="sub-text">단계별로 탄탄하게!</p>
            <div class="level-buttons">
                <button class="level-btn n5" onclick="location.href='groupSelect.jsp?level=N5'">N5</button>
                <button class="level-btn n4" onclick="location.href='groupSelect.jsp?level=N4'">N4</button>
                <button class="level-btn n3" onclick="location.href='groupSelect.jsp?level=N3'">N3</button>
                <button class="level-btn n2" onclick="location.href='groupSelect.jsp?level=N2'">N2</button>
                <button class="level-btn n1" onclick="location.href='groupSelect.jsp?level=N1'">N1</button>
            </div>
        </div>

        <!-- ========== 메뉴 섹션 ========== -->
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

        <!-- ========== 출석 캘린더 섹션 ========== -->
        <div class="calendar-section">
            <h3>　　　 ${todayMonth}月</h3>

            <table class="calendar-table">
                <thead>
                    <tr>
                        <th>日</th>
                        <th>月</th>
                        <th>火</th>
                        <th>水</th>
                        <th>木</th>
                        <th>金</th>
                        <th>土</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="week" items="${calendarWeeks}">
                    <tr>
                    <c:forEach var="calDay" items="${week}">
                        <c:choose>
                            <c:when test="${calDay.blank}">
                                <td class="blank"></td>
                            </c:when>
                            <c:otherwise>
                                <td class="day ${calDay.dayClass}">${calDay.day}</td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- 범례 -->
            <div class="calendar-legend">
                <span class="legend-item"><span class="dot green"></span> 출석</span>
                <span class="legend-item"><span class="dot red"></span> 미출석</span>
                <span class="legend-item"><span class="dot gray"></span> 해당없음</span>
            </div>
        </div>

        <!-- ========== 로그아웃 버튼 ========== -->
        <button class="logout-btn" onclick="location.href='LogoutCon.do'">로그아웃</button>
    </div>
</body>
</html>
