<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.KanjiDTO" %>
<%
    String level = request.getParameter("level");
    String groupStr = request.getParameter("group");
    
    if (level == null || groupStr == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    int groupNumber = Integer.parseInt(groupStr);
    
    // 세션에서 그룹 한자 리스트 가져오기
    @SuppressWarnings("unchecked")
    List<KanjiDTO> kanjiList = (List<KanjiDTO>) session.getAttribute("groupKanji_" + level);
    
    if (kanjiList == null || kanjiList.isEmpty()) {
        response.sendRedirect("groupSelect.jsp?level=" + level);
        return;
    }
    
    // 현재 인덱스 가져오기
    String indexParam = request.getParameter("index");
    int currentIndex = 0;
    
    if (indexParam != null) {
        try {
            currentIndex = Integer.parseInt(indexParam);
            if (currentIndex < 0) currentIndex = 0;
            if (currentIndex >= kanjiList.size()) currentIndex = kanjiList.size() - 1;
        } catch (NumberFormatException e) {
            currentIndex = 0;
        }
    }
    
    // 현재 한자 가져오기
    KanjiDTO currentKanji = kanjiList.get(currentIndex);
    
    // 음독/훈독 합치기
    String onyomi = currentKanji.getOnyomiCombined();
    String kunyomi = currentKanji.getKunyomiCombined();
    
    // 마지막 한자 여부 확인
    boolean isLastKanji = (currentIndex == kanjiList.size() - 1);
    
    // 예시 단어를 줄바꿈으로 분리
    String[] exampleWords = currentKanji.getExample_word() != null ? 
                            currentKanji.getExample_word().split("、") : new String[0];
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일본어 한자 공부장</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="groupSelect.jsp?level=<%= level %>" class="back-link">그룹 선택</a>
            <div class="group-info">그룹 <%= String.format("%02d", groupNumber) %></div>
            <div class="progress">
                <%= currentIndex + 1 %>/<%= kanjiList.size() %>
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
                    onclick="location.href='study.jsp?level=<%= level %>&group=<%= groupNumber %>&index=<%= currentIndex - 1 %>'"
                    <%= currentIndex == 0 ? "disabled" : "" %>>
                <span class="nav-arrow">◀</span>
            </button>
            
            <% if (isLastKanji) { %>
                <button class="nav-button next-button" 
                        onclick="location.href='study.jsp?level=<%= level %>&group=<%= groupNumber %>&index=0'">
                    <span class="nav-arrow">▶</span>
                </button>
            <% } else { %>
                <button class="nav-button next-button" 
                        onclick="location.href='study.jsp?level=<%= level %>&group=<%= groupNumber %>&index=<%= currentIndex + 1 %>'">
                    <span class="nav-arrow">▶</span>
                </button>
            <% } %>
        </div>
        
        <div class="button-section">
            <button class="action-button finish-button" onclick="location.href='finishGroup.jsp?level=<%= level %>&group=<%= groupNumber %>'">
                학습 완료
            </button>
            <button class="action-button home-button" onclick="location.href='groupSelect.jsp?level=<%= level %>'">
                그룹 선택으로
            </button>
        </div>
    </div>
</body>
</html>