<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.StudyProgressDTO" %>
<%
    String level = (String) request.getAttribute("level");
    Integer totalGroups = (Integer) request.getAttribute("totalGroups");
    StudyProgressDTO progress = (StudyProgressDTO) request.getAttribute("progress");
    
    // null 체크 추가
    if (level == null || totalGroups == null || progress == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= level %> - 그룹 선택</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/group.css">
</head>
<body>
    <div class="group-container">
        <div class="group-header">
            <h1 class="level-title"><%= level %></h1>
            <a href="<%= contextPath %>/index.jsp" class="back-link">← 레벨 선택</a>
        </div>
        
        <div class="progress-summary">
            <div class="progress-text">
                학습 진행도: <%= progress.getCompletedCount() %> / <%= totalGroups %> 그룹
            </div>
            <div class="progress-text">
                학습한 한자: <%= progress.getLearnedKanjiCount() %>개
            </div>
            <% if (progress.getCompletedCount() == totalGroups && totalGroups > 0) { %>
                <button class="reset-button" onclick="if(confirm('학습 진행도를 초기화하시겠습니까?')) location.href='<%= contextPath %>/resetProgress?level=<%= level %>'">
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
                        onclick="location.href='<%= contextPath %>/startGroup?level=<%= level %>&group=<%= i %>'">
                    <%= String.format("%02d", i) %>
                </button>
            <% } %>
        </div>
    </div>
</body>
</html>