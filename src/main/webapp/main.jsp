<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AccountDTO" %>
<%@ page import="model.AttendanceDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // ========== 로그인 체크 ==========
    AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // ========== 오늘 날짜 정보 ==========
    Calendar cal = Calendar.getInstance();　
    int todayYear = cal.get(Calendar.YEAR);
    int todayMonth = cal.get(Calendar.MONTH) + 1; // 0부터 시작하므로 +1
    int todayDay = cal.get(Calendar.DAY_OF_MONTH);
    
    // ========== 이번 달 정보 ==========
    cal.set(Calendar.DAY_OF_MONTH, 1); 
    int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=일, 2=월, ...
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 이번 달 마지막 날
    
    // ========== 가입일 파싱 ==========
    int regDay = 1; // 기본값
    String regDateStr = user.getRegDate();
    if (regDateStr != null && !regDateStr.isEmpty()) {
        // regDate 형식: "2026-02-03" 또는 "2026-02-03 00:00:00"
        String[] parts = regDateStr.split(" ")[0].split("-");
        if (parts.length >= 3) {
            int regYear = Integer.parseInt(parts[0]);
            int regMonth = Integer.parseInt(parts[1]);
            regDay = Integer.parseInt(parts[2]);
            // 다른 달이면 가입일을 0으로 (이번 달 전체 표시)
            if (regYear != todayYear || regMonth != todayMonth) {
                regDay = 0;
            }
        }
    }
    
    // ========== 출석 날짜 가져오기 ==========
    AttendanceDAO attendanceDAO = new AttendanceDAO();
    List<Integer> attendedDays = attendanceDAO.getMonthAttendance(user.getUserID(), todayYear, todayMonth);
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
        <!-- ========== 헤더 ========== -->
        <div class="header">
            <h1><%= user.getNickname() %>님 환영합니다!</h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
        </div>
        
        <!-- ========== 한자 공부 섹션 ========== -->
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
            <h3>　　　 <%= todayMonth %>月</h3>
            
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
                <%
                    int dayCount = 1;
                    int totalWeeks = (int) Math.ceil((firstDayOfWeek - 1 + lastDay) / 7.0);
                    
                    for (int week = 0; week < totalWeeks; week++) {
                %>
                    <tr>
                    <%
                        for (int dow = 1; dow <= 7; dow++) {
                            // 첫 주에서 시작 요일 전이거나, 마지막 날 이후
                            if ((week == 0 && dow < firstDayOfWeek) || dayCount > lastDay) {
                    %>
                                <td class="empty"></td>
                    <%
                            } else {
                                // 색상 결정
                                String dayClass = "";
                                
                                if (dayCount < regDay) {
                                    // 가입일 이전 → 회색
                                    dayClass = "gray";
                                } else if (dayCount < todayDay) {
                                    // 과거 (어제 이전)
                                    if (attendedDays.contains(dayCount)) {
                                        dayClass = "green"; // 출석함
                                    } else {
                                        dayClass = "red";   // 출석 안 함
                                    }
                                } else if (dayCount == todayDay) {
                                    // 오늘
                                    if (attendedDays.contains(dayCount)) {
                                        dayClass = "green"; // 오늘 출석함
                                    } else {
                                        dayClass = "gray";  // 아직 학습 안 함
                                    }
                                } else {
                                    // 미래 → 회색
                                    dayClass = "gray";
                                }
                    %>
                                <td class="day <%= dayClass %>"><%= dayCount %></td>
                    <%
                                dayCount++;
                            }
                        }
                    %>
                    </tr>
                <%
                    }
                %>
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