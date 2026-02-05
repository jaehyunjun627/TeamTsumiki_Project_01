<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 로그인 체크 -->
<c:if test="${empty sessionScope.loginUser}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인보드</title>
    <link rel="stylesheet" href="css/main.css">
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
                <button class="level-btn n5" onclick="location.href='sectorSelect?level=N5'">N5</button>
                <button class="level-btn n4" onclick="location.href='sectorSelect?level=N4'">N4</button>
                <button class="level-btn n3" onclick="location.href='sectorSelect?level=N3'">N3</button>
                <button class="level-btn n2" onclick="location.href='sectorSelect?level=N2'">N2</button>
                <button class="level-btn n1" onclick="location.href='sectorSelect?level=N1'">N1</button>
            </div>
        </div>

        <!-- ========== 메뉴 섹션 ========== -->
        <div class="menu-section">
            <a href="#" class="menu-card note">
                <h3>오답노트</h3>
                <p>틀린 문제를 한 눈에!</p>
            </a>
            <a href="#" class="menu-card review">
                <h3>복습 테스트</h3>
                <p>오답 중심으로 복습 가능!</p>
            </a>
        </div>

        <!-- ========== 출석 캘린더 섹션 ========== -->
        <div class="calendar-section">
            <h3>${todayMonth}月</h3>

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
                    <c:set var="dayCount" value="1"/>
                    <c:set var="totalWeeks" value="${((firstDayOfWeek - 1 + lastDay + 6) / 7)}"/>

                    <c:forEach var="week" begin="0" end="${totalWeeks - 1}">
                        <tr>
                            <c:forEach var="dow" begin="1" end="7">
                                <c:choose>
                                    <c:when test="${(week == 0 && dow < firstDayOfWeek) || dayCount > lastDay}">
                                        <td class="empty"></td>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="dayClass" value="gray"/>
                                        <c:choose>
                                            <c:when test="${dayCount < regDay}">
                                                <c:set var="dayClass" value="gray"/>
                                            </c:when>
                                            <c:when test="${dayCount < todayDay}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(attendedDays, dayCount)}">
                                                        <c:set var="dayClass" value="green"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="dayClass" value="red"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:when test="${dayCount == todayDay}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(attendedDays, dayCount)}">
                                                        <c:set var="dayClass" value="green"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="dayClass" value="gray"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="dayClass" value="gray"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <td class="day ${dayClass}">${dayCount}</td>
                                        <c:set var="dayCount" value="${dayCount + 1}"/>
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
