<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.YearMonth" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%
    String contextPath = request.getContextPath();
    
    // 현재 날짜
    LocalDate today = LocalDate.now();
    YearMonth currentMonth = YearMonth.now();
    
    // 월의 첫 날과 마지막 날
    LocalDate firstDay = currentMonth.atDay(1);
    LocalDate lastDay = currentMonth.atEndOfMonth();
    
    // 첫 날의 요일 (1=월요일, 7=일요일)
    int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
    
    // 총 일수
    int daysInMonth = currentMonth.lengthOfMonth();
    
    // 세션에서 출석 기록 가져오기
    @SuppressWarnings("unchecked")
    Set<String> attendanceDates = (Set<String>) session.getAttribute("attendanceDates");
    if (attendanceDates == null) {
        attendanceDates = new HashSet<>();
    }
    
    // 월 표시 (일본어)
    String monthDisplay = today.getMonthValue() + "月";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일본어 한자 공부장</title>
    <link rel="stylesheet" href="css/index.css?v=2">
</head>
<body>
    <div class="main-layout">
        <!-- 왼쪽: 프로필/통계 영역 -->
        <div class="left-section">
            <div class="profile-card">
                <div class="profile-content">
                    <h2>학습 통계</h2>
                    <p>오늘도 화이팅!</p>
                </div>
            </div>
        </div>
        
        <!-- 오른쪽: 메인 컨텐츠 -->
        <div class="right-section">
            <!-- 한자 공부 섹션 -->
            <div class="study-section">
                <div class="section-header">
                    <h2>한자 공부</h2>
                    <p class="section-subtitle">단계별로 탄탄하게!</p>
                </div>
                
                <div class="level-buttons">
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/groupSelect?level=N5'">
                        N5
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/groupSelect?level=N4'">
                        N4
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/groupSelect?level=N3'">
                        N3
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/groupSelect?level=N2'">
                        N2
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/groupSelect?level=N1'">
                        N1
                    </button>
                </div>
            </div>
            
            <!-- 학습 도구 섹션 -->
            <div class="tools-section">
                <div class="tool-card">
                    <div class="tool-icon">📝</div>
                    <button class="tool-btn" onclick="showComingSoon()">
                        오답노트
                    </button>
                    <p class="tool-desc">틀린 문제를 한 눈에!</p>
                </div>
                
                <div class="tool-card">
                    <div class="tool-icon">🎯</div>
                    <button class="tool-btn" onclick="location.href='${pageContext.request.contextPath}/test'">
                        복습 테스트
                    </button>
                    <p class="tool-desc">오답 중심으로 복습 가능!</p>
                </div>
            </div>
            
            <!-- 달력 섹션 -->
            <div class="calendar-section">
                <div class="calendar-header">
                    <h2><%= monthDisplay %></h2>
                    <button class="attendance-btn" onclick="checkAttendance()">
                        오늘 출석 체크
                    </button>
                </div>
                
                <div class="calendar">
                    <div class="calendar-weekdays">
                        <div class="weekday">월</div>
                        <div class="weekday">화</div>
                        <div class="weekday">수</div>
                        <div class="weekday">목</div>
                        <div class="weekday">금</div>
                        <div class="weekday">토</div>
                        <div class="weekday">일</div>
                    </div>
                    
                    <div class="calendar-days">
                        <!-- 첫 주 빈 칸 -->
                        <% for (int i = 1; i < firstDayOfWeek; i++) { %>
                            <div class="calendar-day empty"></div>
                        <% } %>
                        
                        <!-- 날짜 -->
                        <% for (int day = 1; day <= daysInMonth; day++) { 
                            LocalDate date = currentMonth.atDay(day);
                            String dateStr = date.toString();
                            boolean isToday = date.equals(today);
                            boolean isAttended = attendanceDates.contains(dateStr);
                            
                            String dayClass = "calendar-day";
                            if (isToday) dayClass += " today";
                            if (isAttended) dayClass += " attended";
                        %>
                            <div class="<%= dayClass %>" data-date="<%= dateStr %>">
                                <span class="day-number"><%= day %></span>
                                <% if (isAttended) { %>
                                    <span class="check-mark">✓</span>
                                <% } %>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function showComingSoon() {
            alert('준비중입니다');
        }
        
        function checkAttendance() {
            fetch('<%= contextPath %>/attendance', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('출석 체크 완료! 🎉');
                    location.reload();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('출석 체크 실패');
            });
        }
    </script>
</body>
</html>