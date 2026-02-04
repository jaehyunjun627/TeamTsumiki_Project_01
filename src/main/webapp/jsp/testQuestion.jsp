<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>복습 테스트</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/test.css?v=4">
</head>
<body>
    <div class="test-container">
        <div class="test-header">
            <a href="${pageContext.request.contextPath}/testGroupSelect?level=${level}" class="back-link">← 그룹 선택</a>
            <div class="test-progress">
                문제 ${currentIndex + 1} / ${totalCount}
            </div>
        </div>
        
        <div class="test-card">
            <div class="kanji-display">
                ${currentKanji.kanji}
            </div>
            
            <div class="choices-grid">
                <c:forEach var="choice" items="${choices}">
                    <button class="choice-btn" onclick="checkAnswer('${choice}')">
                        ${choice}
                    </button>
                </c:forEach>
            </div>
        </div>
        
        <div class="answer-section">
            <button class="skip-answer-btn" onclick="skipAnswer()">
                모르겠어요
            </button>
        </div>
    </div>
    
    <script>
        let answered = false;
        const currentIndex = ${currentIndex};
        const totalCount = ${totalCount};
        const level = '${level}';
        const group = ${group};
        const contextPath = '${pageContext.request.contextPath}';
        const correctAnswer = '${correctAnswer}';
        
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
            
            fetch(contextPath + '/submitAnswer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'selected=' + encodeURIComponent(selected) + 
                      '&correct=' + encodeURIComponent(correctAnswer) +
                      '&level=' + level +
                      '&group=' + group +
                      '&index=' + currentIndex +
                      '&skipped=false'
            });
            
            setTimeout(() => {
                goToNextQuestion();
            }, 1500);
        }
        
        function skipAnswer() {
            if (answered) return;
            answered = true;
            
            const buttons = document.querySelectorAll('.choice-btn');
            buttons.forEach(btn => {
                const btnText = btn.textContent.trim();
                if (btnText === correctAnswer) {
                    btn.classList.add('correct');
                }
                btn.disabled = true;
            });
            
            document.querySelector('.skip-answer-btn').disabled = true;
            
            fetch(contextPath + '/submitAnswer', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'correct=' + encodeURIComponent(correctAnswer) +
                      '&level=' + level +
                      '&group=' + group +
                      '&index=' + currentIndex +
                      '&skipped=true'
            });
            
            setTimeout(() => {
                goToNextQuestion();
            }, 1500);
        }
        
        function goToNextQuestion() {
            if (currentIndex < totalCount - 1) {
                const nextIndex = currentIndex + 1;
                location.href = contextPath + '/testQuestion?level=' + level + '&group=' + group + '&index=' + nextIndex;
            } else {
                location.href = contextPath + '/testResult';
            }
        }
    </script>
</body>
</html>