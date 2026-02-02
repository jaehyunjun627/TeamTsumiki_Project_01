<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일본어 한자 공부장</title>
    <link rel="stylesheet" href="css/index.css">
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
                    <button class="level-btn" onclick="location.href='<%= contextPath %>/groupSelect?level=N5'">
                        N5
                    </button>
                    <button class="level-btn" onclick="location.href='<%= contextPath %>/groupSelect?level=N4'">
                        N4
                    </button>
                    <button class="level-btn" onclick="location.href='<%= contextPath %>/groupSelect?level=N3'">
                        N3
                    </button>
                    <button class="level-btn" onclick="location.href='<%= contextPath %>/groupSelect?level=N2'">
                        N2
                    </button>
                    <button class="level-btn" onclick="location.href='<%= contextPath %>/groupSelect?level=N1'">
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
                    <button class="tool-btn" onclick="showComingSoon()">
                        복습 테스트
                    </button>
                    <p class="tool-desc">오답 중심으로 복습 가능!</p>
                </div>
            </div>
            
            <!-- 달력 섹션 -->
            <div class="calendar-section">
                <div class="calendar-header">
                    <h2>2月</h2>
                </div>
                <div class="calendar-placeholder">
                    <p>달력이 들어갈 예정입니다</p>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function showComingSoon() {
            alert('준비중입니다');
        }
    </script>
</body>
</html>