<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.KanjiDTO" %>
<%@ page import="model.StudyProgressDTO" %>
<%@ page import="repository.KanjiRepository" %>
<%
    String level = request.getParameter("level");
    if (level == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
    List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
    
    int learnedCount = progress != null ? progress.getLearnedKanji().size() : 0;
    int totalCount = allKanji.size();
    int remainCount = totalCount - learnedCount;
    
    boolean isAllCompleted = (remainCount == 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>학습 완료</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
    <div class="main-container">
        <div class="complete-icon">✓</div>
        <h1 class="main-title">오늘의 학습 완료!</h1>
        
        <div class="progress-info">
            <div class="progress-card">
                <div class="progress-label">학습한 한자</div>
                <div class="progress-number"><%= learnedCount %> / <%= totalCount %></div>
            </div>
            
            <% if (!isAllCompleted) { %>
                <div class="progress-card">
                    <div class="progress-label">남은 한자</div>
                    <div class="progress-number"><%= remainCount %>개</div>
                </div>
            <% } else { %>
                <p class="congrats-message">🎉 모든 한자를 학습했습니다!</p>
            <% } %>
        </div>
        
        <div class="menu-buttons">
            <% if (isAllCompleted) { %>
                <button class="menu-button continue-button" onclick="location.href='resetProgress.jsp?level=<%= level %>'">
                    처음부터 다시 학습하기
                </button>
            <% } else { %>
                <button class="menu-button continue-button" onclick="location.href='dailyCount.jsp?level=<%= level %>'">
                    계속 학습하기
                </button>
            <% } %>
            
            <button class="menu-button home-button" onclick="location.href='index.jsp'">
                메인으로 돌아가기
            </button>
        </div>
    </div>
</body>
</html>