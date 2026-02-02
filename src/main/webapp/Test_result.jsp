<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>퀴즈 결과</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Noto Sans KR', 'Malgun Gothic', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .result-container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 24px;
            width: 100%;
            max-width: 480px;
            padding: 50px 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        .result-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        .result-title {
            font-size: 32px;
            font-weight: 700;
            color: #2d3436;
            margin-bottom: 30px;
        }

        .score-display {
            margin-bottom: 30px;
        }

        .score-number {
            font-size: 72px;
            font-weight: 900;
            color: #5e72e4;
            line-height: 1;
            margin-bottom: 10px;
        }

        .score-divider {
            font-size: 48px;
            color: #666;
        }

        .score-label {
            font-size: 16px;
            color: #666;
            font-weight: 600;
        }

        .result-message {
            font-size: 16px;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .button-group {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .btn {
            width: 100%;
            padding: 16px;
            font-size: 16px;
            font-weight: 600;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .btn-primary {
            background: #5e72e4;
            color: white;
        }

        .btn-primary:hover {
            background: #4c63d2;
            transform: translateY(-2px);
        }

        .btn-secondary {
            background: white;
            color: #5e72e4;
            border: 2px solid #5e72e4;
        }

        .btn-secondary:hover {
            background: #f0f4ff;
            transform: translateY(-2px);
        }

        .accuracy-bar {
            width: 100%;
            height: 12px;
            background: #e3f2fd;
            border-radius: 12px;
            overflow: hidden;
            margin-bottom: 30px;
        }

        .accuracy-fill {
            height: 100%;
            background: linear-gradient(90deg, #10b981, #5e72e4);
            border-radius: 12px;
            transition: width 1s ease;
        }

        /* 정오표 스타일 */
        .answer-table-container {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 2px solid #e3f2fd;
        }

        .table-title {
            font-size: 20px;
            font-weight: 700;
            color: #2d3436;
            margin-bottom: 20px;
            text-align: left;
        }

        .answer-table {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .answer-row {
            display: grid;
            grid-template-columns: 50px 80px 1fr 1fr;
            gap: 12px;
            padding: 12px;
            background: white;
            border-radius: 8px;
            align-items: center;
            font-size: 14px;
        }

        .answer-row.correct {
            background: #f0fdf4;
            border-left: 4px solid #10b981;
        }

        .answer-row.wrong {
            background: #fef2f2;
            border-left: 4px solid #ef4444;
        }

        .row-number {
            font-weight: 700;
            color: #666;
            text-align: center;
        }

        .row-kanji {
            font-size: 24px;
            font-weight: 900;
            color: #2d3436;
            text-align: center;
        }

        .row-answer {
            display: flex;
            flex-direction: column;
            gap: 4px;
        }

        .answer-label {
            font-size: 11px;
            color: #888;
            font-weight: 600;
        }

        .answer-value {
            font-size: 14px;
            font-weight: 600;
            color: #2d3436;
        }

        .answer-value.correct-answer {
            color: #10b981;
        }

        .answer-value.wrong-answer {
            color: #ef4444;
        }

        .answer-value.skipped {
            color: #f59e0b;
        }
    </style>
</head>
<body>
    <div class="result-container">
        <div class="result-icon" id="resultIcon">🎉</div>
        <h1 class="result-title">퀴즈 완료!</h1>
        
        <div class="score-display">
            <div class="score-number">
                <span id="userScore">0</span>
                <span class="score-divider">/</span>
                <span id="totalScore">10</span>
            </div>
            <p class="score-label">정답</p>
        </div>

        <div class="accuracy-bar">
            <div id="accuracyFill" class="accuracy-fill" style="width: 0%"></div>
        </div>

        <p id="resultMessage" class="result-message">
            훌륭해요! 계속 연습하세요!
        </p>

        <div class="button-group">
            <button class="btn btn-primary" onclick="restartQuiz()">다시 도전하기</button>
            <button class="btn btn-secondary" onclick="goHome()">홈으로</button>
        </div>

        <!-- 정오표 -->
        <div class="answer-table-container">
            <h2 class="table-title">정오표</h2>
            <div class="answer-table" id="answerTable">
                <!-- 동적 생성 -->
            </div>
        </div>
    </div>

    <script>
        window.onload = function() {
            displayResults();
            displayAnswerTable();
        };

        function displayResults() {
            const finalScore = parseInt(localStorage.getItem('finalScore')) || 0;
            const finalTotal = parseInt(localStorage.getItem('finalTotal')) || 10;
            const accuracy = Math.round((finalScore / finalTotal) * 100);
            
            document.getElementById('userScore').textContent = finalScore;
            document.getElementById('totalScore').textContent = finalTotal;
            
            setTimeout(() => {
                document.getElementById('accuracyFill').style.width = accuracy + '%';
            }, 200);
            
            updateResultDisplay(accuracy);
        }

        function displayAnswerTable() {
            const resultDataStr = localStorage.getItem('resultData');
            if (!resultDataStr) return;
            
            const resultData = JSON.parse(resultDataStr);
            const tableContainer = document.getElementById('answerTable');
            
            resultData.forEach((item, index) => {
                const isCorrect = item.userAnswer === item.correctAnswer;
                const isSkipped = item.userAnswer === '건너뜀' || item.userAnswer === '시간초과';
                
                const row = document.createElement('div');
                row.className = 'answer-row ' + (isCorrect ? 'correct' : 'wrong');
                
                row.innerHTML = `
                    <div class="row-number">${index + 1}</div>
                    <div class="row-kanji">${item.kanji}</div>
                    <div class="row-answer">
                        <span class="answer-label">내 답변</span>
                        <span class="answer-value ${isSkipped ? 'skipped' : (isCorrect ? 'correct-answer' : 'wrong-answer')}">
                            ${item.userAnswer}
                        </span>
                    </div>
                    <div class="row-answer">
                        <span class="answer-label">정답</span>
                        <span class="answer-value correct-answer">${item.correctAnswer}</span>
                    </div>
                `;
                
                tableContainer.appendChild(row);
            });
        }

        function updateResultDisplay(accuracy) {
            const iconElement = document.getElementById('resultIcon');
            const messageElement = document.getElementById('resultMessage');
            
            let icon, message;
            
            if (accuracy === 100) {
                icon = '🏆';
                message = '완벽해요! 모든 문제를 맞추셨네요!';
            } else if (accuracy >= 80) {
                icon = '🎉';
                message = '85점이 합격기준이면 탈락하셨겠네요.';
            } else if (accuracy >= 60) {
                icon = '👍';
                message = '당신은 60점 짜리 인생인가요?';
            } else if (accuracy >= 40) {
                icon = '💪';
                message = '그걸 공부라고 하신건가요?';
            } else {
                icon = '📚';
                message = '웃음이 나오세요?';
            }
            
            iconElement.textContent = icon;
            messageElement.textContent = message;
        }

        function restartQuiz() {
            localStorage.clear();
            window.location.href = 'quiz';
        }

        function goHome() {
            localStorage.clear();
            window.location.href = 'quiz';
        }
    </script>
</body>
</html>