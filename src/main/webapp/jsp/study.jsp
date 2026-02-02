<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.KanjiDTO" %>
<%
    String level = (String) request.getAttribute("level");
    Integer groupNumber = (Integer) request.getAttribute("groupNumber");
    Integer currentIndex = (Integer) request.getAttribute("currentIndex");
    Integer totalCount = (Integer) request.getAttribute("totalCount");
    KanjiDTO currentKanji = (KanjiDTO) request.getAttribute("currentKanji");
    String onyomi = (String) request.getAttribute("onyomi");
    String kunyomi = (String) request.getAttribute("kunyomi");
    Boolean isLastKanji = (Boolean) request.getAttribute("isLastKanji");
    String[] exampleWords = (String[]) request.getAttribute("exampleWords");
    
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일본어 한자 공부장</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css?v=2">
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="<%= contextPath %>/groupSelect?level=<%= level %>" class="back-link">그룹 선택</a>
            <div class="group-info">그룹 <%= String.format("%02d", groupNumber) %></div>
            <div class="progress">
                <%= currentIndex + 1 %>/<%= totalCount %>
            </div>
        </div>
        
        <div class="kanji-card">
            <div class="kanji-korean">
                <%= currentKanji.getExplantion() != null ? currentKanji.getExplantion() : "" %>
            </div>
            
            <div class="kanji-character">
                <%= currentKanji.getKanji() %>
            </div>
            
            <div class="kanji-readings">
                <div class="reading-item">
                    <span class="reading-label">음독 : </span>
                    <span class="reading-value"><%= onyomi.isEmpty() ? "-" : onyomi %></span>
                </div>
                
                <div class="reading-item">
                    <span class="reading-label">훈독 : </span>
                    <span class="reading-value"><%= kunyomi.isEmpty() ? "-" : kunyomi %></span>
                </div>
            </div>
            
            <div class="kanji-meaning">
                <div class="meaning-label">의미 :</div>
                <div class="meaning-value">
                    <%= currentKanji.getKorean() != null ? currentKanji.getKorean() : "" %>
                </div>
            </div>
            
            <div class="kanji-examples">
                <% for (String example : exampleWords) { %>
                    <div class="example-item"><%= example.trim() %></div>
                <% } %>
            </div>
        </div>
        
        <div class="navigation">
            <button class="nav-button prev-button" 
                    onclick="location.href='<%= contextPath %>/study?level=<%= level %>&group=<%= groupNumber %>&index=<%= currentIndex - 1 %>'"
                    <%= currentIndex == 0 ? "disabled" : "" %>>
                <span class="nav-arrow">◀</span>
            </button>
            
            <% if (isLastKanji) { %>
                <button class="nav-button next-button" 
                        onclick="location.href='<%= contextPath %>/study?level=<%= level %>&group=<%= groupNumber %>&index=0'">
                    <span class="nav-arrow">▶</span>
                </button>
            <% } else { %>
                <button class="nav-button next-button" 
                        onclick="location.href='<%= contextPath %>/study?level=<%= level %>&group=<%= groupNumber %>&index=<%= currentIndex + 1 %>'">
                    <span class="nav-arrow">▶</span>
                </button>
            <% } %>
        </div>
        
        <div class="button-section">
            <button class="action-button finish-button" onclick="location.href='<%= contextPath %>/finishGroup?level=<%= level %>&group=<%= groupNumber %>'">
                학습 완료
            </button>
            <button class="action-button home-button" onclick="location.href='<%= contextPath %>/groupSelect?level=<%= level %>'">
                그룹 선택으로
            </button>
        </div>
    </div>
</body>
</html>