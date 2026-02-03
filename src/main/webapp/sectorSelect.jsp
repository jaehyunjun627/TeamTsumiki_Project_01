<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AccountDTO, model.KanjiDTO, java.util.List" %>
<%@ page import="data.KanjiRepository, java.util.List" %>
<%
    AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String level = request.getParameter("level"); // N5, N4, N3...
    
    // 해당 레벨의 전체 한자 개수 가져오기 (메모리에서)
    List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
    int totalKanji = (allKanji != null) ? allKanji.size() : 0;
    
    // 섹터당 20개씩, 총 몇 개의 섹터가 필요한지 계산
    int kanjiPerSector = 20;
    int totalSectors = (int) Math.ceil((double) totalKanji / kanjiPerSector);
    if (totalSectors == 0) totalSectors = 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= level %> 섹터 선택</title>
    <link rel="stylesheet" href="../css/sectorSelect.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= level %></h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
            <p class="kanji-count">등록된 한자: <%= totalKanji %>개</p>
        </div>
        
        <div class="sector-grid">
            <% for (int i = 1; i <= 10; i++) { 
                // 해당 섹터에 한자가 있는지 확인
                int startIdx = (i - 1) * kanjiPerSector;
                boolean hasKanji = (totalKanji > 0) && (startIdx < totalKanji);
            %>
                <% if (hasKanji) { %>
                    <button class="sector-btn active" 
                        onclick="location.href='kanjiStudy.jsp?level=<%= level %>&sector=<%= i %>'">
                        <%= String.format("%02d", i) %>
                    </button>
                <% } else { %>
                    <button class="sector-btn inactive" disabled>
                        <%= String.format("%02d", i) %>
                    </button>
                <% } %>
            <% } %>
        </div>
        
        <button class="back-btn" onclick="location.href='main.jsp'">돌아가기</button>
    </div>
</body>
</html>