<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AccountDTO, model.KanjiDTO, java.util.List" %>
<%@ page import="data.KanjiRepository" %>
<%
    // ========== 로그인 체크 ==========
    AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // ========== 파라미터 받기 ==========
    String level = request.getParameter("level");
    int sector = Integer.parseInt(request.getParameter("sector"));
    
    // ========== 메모리에서 한자 가져오기 ==========
    List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
    
    // ========== 섹터에 해당하는 한자만 추출 (20개씩) ==========
    int kanjiPerSector = 20;
    int startIdx = (sector - 1) * kanjiPerSector;
    int endIdx = Math.min(startIdx + kanjiPerSector, allKanji.size());
    
    // subList 대신 새 리스트로 복사 (immutable list 문제 방지)
    List<KanjiDTO> sectorKanji = new java.util.ArrayList<>();
    for (int i = startIdx; i < endIdx; i++) {
        sectorKanji.add(allKanji.get(i));
    }
    int totalInSector = sectorKanji.size();
    
    // ========== 현재 인덱스 ==========
    int currentIndex = 0;
    String indexParam = request.getParameter("index");
    if (indexParam != null) {
        currentIndex = Integer.parseInt(indexParam);
    }
    
    // 범위 체크
    if (currentIndex >= totalInSector) currentIndex = totalInSector - 1;
    if (currentIndex < 0) currentIndex = 0;
    
    KanjiDTO currentKanji = null;
    if (totalInSector > 0) {
        currentKanji = sectorKanji.get(currentIndex);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>한자 학습 - <%= level %> 섹터 <%= sector %></title>
    <link rel="stylesheet" href="css/kanjiStudy.css">
</head>
<body>
    <div class="container">
        <div class="header">한자 학습</div>
        
        <% if (currentKanji != null) { %>
            <!-- 진행 상황 -->
            <div class="progress"><%= currentIndex + 1 %>/<%= totalInSector %></div>
            
            <!-- 한자 표시 (클릭하면 상세 페이지로) -->
            <div class="kanji-display">
                <div class="kanji-char" onclick="location.href='kanjiDetail.jsp?level=<%= level %>&sector=<%= sector %>&index=<%= currentIndex %>'">
                    <%= currentKanji.getKanji() %>
                </div>
                <div class="korean-reading"><%= currentKanji.getKorean() %></div>
            </div>
            
            <!-- 읽기 정보 -->
            <div class="reading-section">
                <div class="reading-row">
                    <span class="reading-label">음독:</span>
                    <span class="reading-value">
                        <%= currentKanji.getUnyomi_1() != null ? currentKanji.getUnyomi_1() : "-" %><%= currentKanji.getUnyomi_2() != null ? ", " + currentKanji.getUnyomi_2() : "" %>
                    </span>
                </div>
                <div class="reading-row">
                    <span class="reading-label">훈독:</span>
                    <span class="reading-value">
                        <%= currentKanji.getKunyomi_1() != null ? currentKanji.getKunyomi_1() : "-" %><%= currentKanji.getKunyomi_2() != null ? ", " + currentKanji.getKunyomi_2() : "" %>
                    </span>
                </div>
            </div>
            
            <!-- 예시 단어 -->
            <div class="example-section">
                <h3>예시 단어</h3>
                <% if (currentKanji.getExample_word_1() != null && !currentKanji.getExample_word_1().isEmpty()) { %>
                    <div class="example-item"><%= currentKanji.getExample_word_1() %></div>
                <% } %>
                <% if (currentKanji.getExample_word_2() != null && !currentKanji.getExample_word_2().isEmpty()) { %>
                    <div class="example-item"><%= currentKanji.getExample_word_2() %></div>
                <% } %>
                <% if (currentKanji.getExample_word_3() != null && !currentKanji.getExample_word_3().isEmpty()) { %>
                    <div class="example-item"><%= currentKanji.getExample_word_3() %></div>
                <% } %>
            </div>
            
            <!-- 이전/다음 버튼 -->
            <div class="nav-buttons">
                <button class="nav-btn" 
                    <%= currentIndex == 0 ? "disabled" : "" %>
                    onclick="location.href='kanjiStudy.jsp?level=<%= level %>&sector=<%= sector %>&index=<%= currentIndex - 1 %>'">
                    ◀
                </button>
                <button class="nav-btn" 
                    <%= currentIndex >= totalInSector - 1 ? "disabled" : "" %>
                    onclick="location.href='kanjiStudy.jsp?level=<%= level %>&sector=<%= sector %>&index=<%= currentIndex + 1 %>'">
                    ▶
                </button>
            </div>
        <% } else { %>
            <!-- 데이터 없음 -->
            <div class="no-data">
                <p>등록된 한자가 없습니다.</p>
            </div>
        <% } %>
        
        <!-- 돌아가기 버튼 -->
        <button class="back-btn" onclick="location.href='sectorSelect.jsp?level=<%= level %>'">섹터 선택으로</button>
    </div>
</body>
</html>