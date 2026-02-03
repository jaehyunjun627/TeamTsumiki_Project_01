<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.KanjiDTO" %>
<%@ page import="java.util.List" %>
<%
    KanjiDTO currentKanji = (KanjiDTO) request.getAttribute("currentKanji");
    @SuppressWarnings("unchecked")
    List<String> choices = (List<String>) request.getAttribute("choices");
    String correctAnswer = (String) request.getAttribute("correctAnswer");
    Integer currentIndex = (Integer) request.getAttribute("currentIndex");
    Integer totalCount = (Integer) request.getAttribute("totalCount");
    String level = (String) request.getAttribute("level");
    Integer group = (Integer) request.getAttribute("group");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>복습 테스트</title>
    <link rel="stylesheet" href="<%= contextPath %>/css/test.css?v=2">
</head>
<body>
    <div class="test-container">
        <div class="test-header">
            <a href="<%= contextPath %>/testGroupSelect?level=<%= level %>" class="back-link">← 그룹 선택</a>
            <div class="test-progress">
                문제 <%= currentIndex + 1 %> / <%= totalCount %>
            </div>
        </div>
        
        <div class="test-card">
            <div class="kanji-display">
                <%= currentKanji.getKanji() %>
            </div>
            
            <div class="choices-grid">
                <% for (int i = 0; i < choices.size(); i++) { %>
                    <button class="choice-btn" onclick="checkAnswer('<%= choices.get(i) %>')">
                        <%= choices.get(i) %>
                    </button>
                <% } %>
            </div>
        </div>
        
        <div class="answer-section">
            <button class="show-answer-btn" onclick="showAnswer()">
                정답 확인
            </button>
        </div>
    </div>
    
    <script>
        let answered = false;
        const currentIndex = <%= currentIndex %>;
        const totalCount = <%= totalCount %>;
        const level = '<%= level %>';
        const group = <%= group %>;
        const contextPath = '<%= contextPath %>';
        const correctAnswer = '<%= correctAnswer %>';
        
        function checkAnswer(selected) {
            if (answered) return;
            answered = true;
            
            const isCorrect = (selected === correctAnswer);
            
            const buttons = document.querySelectorAll('.choice-btn');
            buttons.forEach(btn => {
                const btnText = btn.textContent.trim();
                if (btnText === correctAnswer) {
                    btn.classList.add('correct');
                }
                if (btnText === selected && !isCorrect) {
                    btn.classList.add('wrong');
                }
                btn.disabled = true;
            });
            
            // 서버에 답안 제출
            fetch(contextPath + '/submitAnswer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'selected=' + encodeURIComponent(selected) + 
                      '&correct=' + encodeURIComponent(correctAnswer) +
                      '&level=' + level +
                      '&group=' + group +
                      '&index=' + currentIndex
            });
            
            setTimeout(() => {
                if (currentIndex < totalCount - 1) {
                    // 다음 문제로
                    const nextIndex = currentIndex + 1;
                    location.href = contextPath + '/testQuestion?level=' + level + '&group=' + group + '&index=' + nextIndex;
                } else {
                    // 테스트 완료 - 결과 화면으로
                    location.href = contextPath + '/testResult';
                }
            }, 1500);
        }
        
        function showAnswer() {
            const buttons = document.querySelectorAll('.choice-btn');
            buttons.forEach(btn => {
                if (btn.textContent.trim() === correctAnswer) {
                    btn.classList.add('correct');
                }
            });
        }
    </script>
</body>
</html>