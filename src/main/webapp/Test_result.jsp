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
            font-family: 'Noto Sans KR', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .result-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            padding: 50px;
            max-width: 500px;
            width: 100%;
            text-align: center;
        }

        .result-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        .result-title {
            font-size: 32px;
            color: #333;
            margin-bottom: 30px;
            font-weight: bold;
        }

        .score-display {
            font-size: 48px;
            color: #667eea;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .score-label {
            font-size: 18px;
            color: #666;
            margin-bottom: 40px;
        }

        .result-message {
            font-size: 20px;
            color: #333;
            margin-bottom: 40px;
            line-height: 1.6;
        }

        .button-group {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn {
            padding: 15px 30px;
            font-size: 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: bold;
        }

        .btn-primary {
            background: #667eea;
            color: white;
        }

        .btn-primary:hover {
            background: #5568d3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background: #868e96;
            color: white;
        }

        .btn-secondary:hover {
            background: #495057;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(134, 142, 150, 0.4);
        }

        @media (max-width: 600px) {
            .result-container {
                padding: 30px;
            }
            
            .result-title {
                font-size: 26px;
            }
            
            .score-display {
                font-size: 40px;
            }
        }
    </style>
</head>
<body>
    <div class="result-container">
        <div class="result-icon" id="resultIcon">🎉</div>
        <h1 class="result-title">퀴즈 완료!</h1>
        
        <div class="score-display" id="scoreDisplay">
            <span id="finalScore">0</span> / <span id="finalTotal">10</span>
        </div>
        
        <div class="score-label">맞힌 문제 수</div>
        
        <div class="result-message" id="resultMessage">
            축하합니다!
        </div>
        
        <div class="button-group">
            <button class="btn btn-primary" onclick="restartQuiz()">
                🔄 다시 도전하기
            </button>
            <button class="btn btn-secondary" onclick="goToHome()">
                🏠 홈으로
            </button>
        </div>
    </div>

    <script>
        // 페이지 로드 시 결과 표시
        window.onload = function() {
            displayResult();
        };

        function displayResult() {
            // localStorage에서 점수 가져오기
            const score = parseInt(localStorage.getItem('finalScore')) || 0;
            const total = parseInt(localStorage.getItem('finalTotal')) || 10;
            
            // 점수 표시
            document.getElementById('finalScore').textContent = score;
            document.getElementById('finalTotal').textContent = total;
            
            // 점수에 따른 메시지와 아이콘 설정
            const percentage = (score / total) * 100;
            let message = '';
            let icon = '';
            
            if (percentage === 100) {
                message = '완벽합니다! 🌟<br>모든 문제를 맞히셨네요!';
                icon = '🏆';
            } else if (percentage >= 80) {
                message = '85점이 합격이면, 6개월 시간낭비 하셨네요ㅋㅋ';
                icon = '🎉';
            } else if (percentage >= 60) {
                message = '웃음이 나오죠?';
                icon = '😊';
            } else if (percentage >= 40) {
                message = '공부한거 맞아요? 멍청한건가?';
                icon = '🙂';
            } else {
                message = '다른 길을 알아보세요';
                icon = '📖';
            }
            
            document.getElementById('resultIcon').textContent = icon;
            document.getElementById('resultMessage').innerHTML = message;
        }

        function restartQuiz() {
            // localStorage 초기화
            localStorage.clear();
            // 퀴즈 페이지로 이동
            window.location.href = 'quiz.html';
        }

        function goToHome() {
            // 홈 페이지로 이동 (실제 경로에 맞게 수정)
            window.location.href = 'index.html';
        }
    </script>
</body>
</html>
