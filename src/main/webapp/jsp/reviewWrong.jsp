<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.TestAnswerRecord" %>
<%@ page import="model.KanjiDTO" %>
<%
    TestAnswerRecord currentRecord = (TestAnswerRecord) request.getAttribute("currentRecord");
    Integer currentIndex = (Integer) request.getAttribute("currentIndex");
    Integer totalWrong = (Integer) request.getAttribute("totalWrong");
    String level = (String) request.getAttribute("level");
    String contextPath = request.getContextPath();
    
    KanjiDTO kanji = currentRecord.getKanji();
    String[] exampleWords = kanji.getExample_word() != null ? 
                            kanji.getExample_word().split("、") : new String[0];
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>틀린 문제 검토</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/review.css">
</head>
<body>
    <div class="review-container">
        <div class="review-header">
            <a href="<%= contextPath %>/testResult" class="back-link">← 결과로 돌아가기</a>
            <div class="review-progress">
                틀린 문제 <%= currentIndex + 1 %> / <%= totalWrong %>
            </div>
        </div>
        
        <div class="review-card">
            <div class="wrong-indicator">✗ 오답</div>
            
            <div class="kanji-display">
                <%= kanji.getKanji() %>
            </div>
            
            <div class="kanji-korean">
                <%= kanji.getKorean() != null ? kanji.getKorean() : "" %>
            </div>
            
            <div class="answer-comparison">
                <div class="answer-box correct-box">
                    <div class="answer-label">정답</div>
                    <div class="answer-value"><%= currentRecord.getCorrectAnswer() %></div>
                </div>
                
                <div class="answer-box wrong-box">
                    <div class="answer-label">내 답</div>
                    <div class="answer-value"><%= currentRecord.getUserAnswer() %></div>
                </div>
            </div>
            
            <div class="kanji-details">
                <div class="detail-section">
                    <div class="detail-label">음독</div>
                    <div class="detail-value">
                        <%= kanji.getOnyomiCombined().isEmpty() ? "-" : kanji.getOnyomiCombined() %>
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">훈독</div>
                    <div class="detail-value">
                        <%= kanji.getKunyomiCombined().isEmpty() ? "-" : kanji.getKunyomiCombined() %>
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">뜻</div>
                    <div class="detail-value">
                        <%= kanji.getExplantion() != null ? kanji.getExplantion() : "" %>
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">예시</div>
                    <div class="detail-value examples">
                        <% for (String example : exampleWords) { %>
                            <span class="example-item"><%= example.trim() %></span>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="review-navigation">
            <% if (currentIndex > 0) { %>
                <button class="nav-btn prev-btn" 
                        onclick="location.href='<%= contextPath %>/reviewWrong?index=<%= currentIndex - 1 %>'">
                    ← 이전 오답
                </button>
            <% } else { %>
                <button class="nav-btn prev-btn" disabled>
                    ← 이전 오답
                </button>
            <% } %>
            
            <% if (currentIndex < totalWrong - 1) { %>
                <button class="nav-btn next-btn" 
                        onclick="location.href='<%= contextPath %>/reviewWrong?index=<%= currentIndex + 1 %>'">
                    다음 오답 →
                </button>
            <% } else { %>
                <button class="nav-btn finish-btn" 
                        onclick="location.href='<%= contextPath %>/testResult'">
                    검토 완료
                </button>
            <% } %>
        </div>
    </div>
</body>
</html>