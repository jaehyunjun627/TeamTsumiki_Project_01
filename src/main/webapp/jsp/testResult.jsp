<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String level = (String) request.getAttribute("level");
    Integer group = (Integer) request.getAttribute("group");
    Integer totalCount = (Integer) request.getAttribute("totalCount");
    Integer correctCount = (Integer) request.getAttribute("correctCount");
    Integer wrongCount = (Integer) request.getAttribute("wrongCount");
    String contextPath = request.getContextPath();
    
    // 점수 계산
    int score = (int) Math.round((double) correctCount / totalCount * 100);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>테스트 결과</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/testResult.css">
</head>
<body>
    <div class="result-container">
        <div class="result-header">
            <h1>테스트 결과</h1>
            <p class="result-level"><%= level %> - 그룹 <%= String.format("%02d", group) %></p>
        </div>
        
        <div class="score-card">
            <div class="score-circle">
                <div class="score-number"><%= score %></div>
                <div class="score-label">점</div>
            </div>
        </div>
        
        <div class="result-summary">
            <div class="summary-item">
                <div class="summary-label">전체 문제</div>
                <div class="summary-value"><%= totalCount %>개</div>
            </div>
            <div class="summary-item correct">
                <div class="summary-label">정답</div>
                <div class="summary-value"><%= correctCount %>개</div>
            </div>
            <div class="summary-item wrong">
                <div class="summary-label">오답</div>
                <div class="summary-value"><%= wrongCount %>개</div>
            </div>
        </div>
        
        <div class="result-message">
            <% if (score == 100) { %>
                <p class="message-text">🎉 완벽해요! 모든 문제를 맞췄습니다!</p>
            <% } else if (score >= 80) { %>
                <p class="message-text">👏 잘했어요! 조금만 더 노력하면 완벽!</p>
            <% } else if (score >= 60) { %>
                <p class="message-text">💪 괜찮아요! 복습하면 더 좋아질 거예요!</p>
            <% } else { %>
                <p class="message-text">📚 다시 한번 공부해봐요!</p>
            <% } %>
        </div>
        
        <div class="result-buttons">
    		<% if (wrongCount > 0) { %>
        	<button class="result-btn review-btn" onclick="location.href='<%= contextPath %>/reviewWrong?index=0'">
            틀린 문제 검토하기 (<%= wrongCount %>개)
        	</button>
    		<% } else { %>
        	<button class="result-btn review-btn" disabled style="opacity: 0.5; cursor: not-allowed;">
            모든 문제를 맞췄습니다!
        	</button>
    		<% } %>
    		<button class="result-btn home-btn" onclick="location.href='<%= contextPath %>/index.jsp'">
        	메인으로 돌아가기
    		</button>
		</div>
        
    </div>
</body>
</html>