<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String level = (String) request.getAttribute("level");
    Integer totalGroups = (Integer) request.getAttribute("totalGroups");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= level %> - 테스트 그룹 선택</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/group.css?v=3">
</head>
<body>
    <div class="group-container">
        <div class="group-header">
            <h1 class="level-title"><%= level %> 테스트</h1>
            <a href="<%= contextPath %>/test" class="back-link">← 레벨 선택</a>
        </div>
        
        <div class="progress-summary">
            <div class="progress-text">
                테스트할 그룹을 선택하세요
            </div>
        </div>
        
        <div class="group-grid">
            <% for (int i = 1; i <= totalGroups; i++) { %>
                <button class="group-button" 
                        onclick="location.href='<%= contextPath %>/startTest?level=<%= level %>&group=<%= i %>'">
                    <%= String.format("%02d", i) %>
                </button>
            <% } %>
        </div>
    </div>
</body>
</html>