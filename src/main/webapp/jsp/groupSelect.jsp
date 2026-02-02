<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.KanjiDTO" %>
<%@ page import="model.StudyProgressDTO" %>
<%@ page import="repository.KanjiRepository" %>
<%@ page import="util.StudyManager" %>
<%
    String level = request.getParameter("level");
    if (level == null || level.isEmpty()) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    // 해당 레벨의 한자 리스트 가져오기
    List<KanjiDTO> kanjiList = KanjiRepository.findByLevel(level);
    int totalGroups = StudyManager.getTotalGroups(kanjiList);
    
    // 세션에서 학습 진행 상태 가져오기
    StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
    if (progress == null) {
        progress = new StudyProgressDTO(level);
        session.setAttribute("studyProgress_" + level, progress);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= level %> - 그룹 선택</title>
    <link rel="stylesheet" href="../css/group.css">
</head>
<body>
    <div class="group-container">
        <div class="group-header">
            <h1 class="level-title"><%= level %></h1>
            <a href="index.jsp" class="back-link">← 레벨 선택</a>
        </div>
        
        <div class="progress-summary">
            <div class="progress-text">
                학습 진행도: <%= progress.getCompletedCount() %> / <%= totalGroups %>
            </div>
            <% if (progress.getCompletedCount() == totalGroups) { %>
                <button class="reset-button" onclick="if(confirm('학습 진행도를 초기화하시겠습니까?')) location.href='resetProgress.jsp?level=<%= level %>'">
                    처음부터 다시 시작
                </button>
            <% } %>
        </div>
        
        <div class="group-grid">
            <% for (int i = 1; i <= totalGroups; i++) { %>
                <% 
                    boolean isCompleted = progress.isGroupCompleted(i);
                    String buttonClass = isCompleted ? "group-button completed" : "group-button";
                %>
                <button class="<%= buttonClass %>" 
                        onclick="location.href='startGroup.jsp?level=<%= level %>&group=<%= i %>'">
                    <%= String.format("%02d", i) %>
                </button>
            <% } %>
        </div>
    </div>
</body>
</html>