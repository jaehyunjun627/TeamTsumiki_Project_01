<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="data.KanjiRepository_KANJIDAO" %>
<%@ page import="model.KanjiDTO" %>
<%@ page import="model.AccountDTO" %>
<%
// ========== 로그인 체크 ==========
    AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // ========== 파라미터 받기 ==========
    String level = request.getParameter("level");
    String sectorParam = request.getParameter("sector");
    int sector = 1;
    
    if (sectorParam != null && !sectorParam.isEmpty()) {
        try {
            sector = Integer.parseInt(sectorParam);
        } catch (NumberFormatException e) {
            sector = 1;
        }
    }
    
    // ========== 해당 섹터의 한자 가져오기 ==========
    List<KanjiDTO> kanjiList = KanjiRepository_KANJIDAO.findBySector(level, sector);
    
    if (kanjiList == null || kanjiList.isEmpty()) {
        out.println("<script>alert('테스트 데이터가 없습니다.'); history.back();</script>");
        return;
    }
    
    // ========== 퀴즈 데이터 준비 ==========
    // 모든 읽기(음독) 수집 (오답 보기용)
    List<String> allReadings = new ArrayList<>();
    for (KanjiDTO k : kanjiList) {
        if (k.getUnyomi_1() != null && !k.getUnyomi_1().isEmpty()) {
            allReadings.add(k.getUnyomi_1());
        }
        if (k.getUnyomi_2() != null && !k.getUnyomi_2().isEmpty()) {
            allReadings.add(k.getUnyomi_2());
        }
        if (k.getKunyomi_1() != null && !k.getKunyomi_1().isEmpty()) {
            allReadings.add(k.getKunyomi_1());
        }
    }
    
    // 퀴즈 질문 클래스
    class QuizQuestion {
        public String kanji;
        public List<String> options;
        public int correctIndex;
        
        public QuizQuestion(String kanji, List<String> options, int correctIndex) {
            this.kanji = kanji;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }
    
    List<QuizQuestion> quizQuestions = new ArrayList<>();
    
    for (int i = 0; i < kanjiList.size(); i++) {
        KanjiDTO kanji = kanjiList.get(i);
        String question = kanji.getKanji();
        
        // 정답: 첫번째 음독
        String correctAnswer = kanji.getUnyomi_1();
        if (correctAnswer == null || correctAnswer.isEmpty()) {
            correctAnswer = kanji.getKunyomi_1(); // 훈독으로 대체
        }
        
        if (correctAnswer == null || correctAnswer.isEmpty()) {
            continue; // 읽기 정보 없으면 스킵
        }
        
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        
        // 오답 보기 생성
        List<String> wrongOptions = new ArrayList<>();
        for (String reading : allReadings) {
            if (!reading.equals(correctAnswer) && !wrongOptions.contains(reading)) {
                wrongOptions.add(reading);
            }
        }
        Collections.shuffle(wrongOptions);
        
        // 3개의 오답 추가
        for (int j = 0; j < 3 && j < wrongOptions.size(); j++) {
            options.add(wrongOptions.get(j));
        }
        
        // 보기 섞기
        Collections.shuffle(options);
        int correctIndex = options.indexOf(correctAnswer);
        
        quizQuestions.add(new QuizQuestion(question, options, correctIndex));
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>한자 테스트 - <%= level %> 섹터 <%= sector %></title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        
        body {
            font-family: 'Noto Sans KR', -apple-system, BlinkMacSystemFont, sans-serif;
            background: linear-gradient(135deg, #C9A9E9 0%, #E6C9F5 50%, #F5D4FF 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .quiz-container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 30px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
            padding: 40px;
            max-width: 450px;
            width: 100%;
        }
        
        .quiz-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .level-badge {
            display: inline-block;
            background: linear-gradient(135deg, #E6C9F5 0%, #C9A9E9 100%);
            color: #5a3a7a;
            padding: 8px 20px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
            margin-bottom: 15px;
        }
        
        .progress-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 16px;
            color: #333;
            font-weight: 600;
        }
        
        .timer {
            color: #7B68EE;
            font-size: 18px;
        }
        
        .question-section {
            margin: 30px 0;
            text-align: center;
        }
        
        .question-kanji {
            font-size: 120px;
            font-weight: 700;
            color: #333;
            margin-bottom: 20px;
            line-height: 1;
        }
        
        .options-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 15px;
            margin-bottom: 20px;
        }
        
        .option-btn {
            padding: 20px;
            font-size: 16px;
            font-weight: 600;
            border: 2px solid #e0e0e0;
            background: white;
            border-radius: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
            color: #333;
            min-height: 70px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .option-btn:hover:not(:disabled) {
            background: #f8f9fa;
            border-color: #7B68EE;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(123, 104, 238, 0.2);
        }
        
        .option-btn:disabled { cursor: not-allowed; }
        .option-btn.selected { background: #f0f0f0; border-color: #999; }
        
        .option-btn.correct {
            background: #6B9BD1 !important;
            border-color: #5A8AC0 !important;
            color: white !important;
            animation: correctPulse 0.5s ease;
        }
        
        .option-btn.wrong {
            background: #E88B8B !important;
            border-color: #D77A7A !important;
            color: white !important;
            animation: wrongShake 0.5s ease;
        }
        
        .option-btn.answer {
            background: #90C695 !important;
            border-color: #7FB584 !important;
            color: white !important;
            animation: answerHighlight 0.6s ease;
        }
        
        @keyframes correctPulse { 0%, 100% { transform: scale(1); } 50% { transform: scale(1.05); } }
        @keyframes wrongShake { 0%, 100% { transform: translateX(0); } 25% { transform: translateX(-5px); } 75% { transform: translateX(5px); } }
        @keyframes answerHighlight { 0% { transform: scale(1); } 50% { transform: scale(1.08); } 100% { transform: scale(1); } }
        
        .pass-button {
            width: 100%;
            padding: 18px;
            font-size: 16px;
            font-weight: 700;
            background: linear-gradient(135deg, #6B9BD1 0%, #5A8AC0 100%);
            color: white;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(107, 155, 209, 0.3);
        }
        
        .pass-button:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(107, 155, 209, 0.4); }
        .pass-button:disabled { opacity: 0.6; cursor: not-allowed; }
        
        @media (max-width: 600px) {
            .quiz-container { padding: 30px 25px; }
            .question-kanji { font-size: 80px; }
            .option-btn { padding: 15px; font-size: 14px; min-height: 60px; }
        }
    </style>
</head>
<body>
    <div class="quiz-container">
        <div class="quiz-header">
            <div class="level-badge"><%= level %> - 섹터 <%= sector %></div>
            <div class="progress-info">
                <span><span id="currentQuestion">1</span>/<span id="totalQuestions"><%= quizQuestions.size() %></span></span>
                <span class="timer" id="timer">5</span>
            </div>
        </div>
        
        <div class="question-section">
            <div class="question-kanji" id="questionKanji"></div>
        </div>
        
        <div class="options-grid" id="optionsContainer"></div>
        
        <button class="pass-button" id="passButton">모르겠어요</button>
    </div>
    
    <script type="text/javascript">
        // ========== 퀴즈 데이터 ==========
        var quizData = [];
        var testLevel = "<%= level %>";
        var testSector = <%= sector %>;
        var userID = "<%= user.getUserID() %>";
        
        <% for (int i = 0; i < quizQuestions.size(); i++) {
            QuizQuestion q = quizQuestions.get(i);
            String kanjiEscaped = q.kanji.replace("\\", "\\\\").replace("\"", "\\\"");
        %>
        quizData.push({
            question: "<%= kanjiEscaped %>",
            options: [
                <% for (int j = 0; j < q.options.size(); j++) {
                    String optionEscaped = q.options.get(j).replace("\\", "\\\\").replace("\"", "\\\"");
                %>
                "<%= optionEscaped %>"<%= j < q.options.size() - 1 ? "," : "" %>
                <% } %>
            ],
            correctIndex: <%= q.correctIndex %>
        });
        <% } %>
        
        // ========== 변수 ==========
        var currentQuestionIndex = 0;
        var score = 0;
        var questionStartTime = 0;
        var totalTime = 5;
        var timerInterval = null;
        var isAnswered = false;
        var resultData = [];
        
        // ========== 초기화 ==========
        function init() {
            if (quizData.length === 0) {
                alert("퀴즈 데이터가 없습니다.");
                history.back();
                return;
            }
            loadQuestion();
            updateDisplay();
        }
        
        // ========== 타이머 ==========
        function startQuestionTimer() {
            questionStartTime = Date.now();
            updateTimer();
            if (timerInterval) clearInterval(timerInterval);
            timerInterval = setInterval(updateTimer, 100);
        }
        
        function updateTimer() {
            var elapsed = Math.floor((Date.now() - questionStartTime) / 1000);
            var timeLeft = totalTime - elapsed;
            document.getElementById("timer").textContent = Math.max(0, timeLeft);
            if (timeLeft <= 0) {
                clearInterval(timerInterval);
                handleTimeout();
            }
        }
        
        // ========== 문제 로드 ==========
        function loadQuestion() {
            var question = quizData[currentQuestionIndex];
            document.getElementById("questionKanji").textContent = question.question;
            
            var container = document.getElementById("optionsContainer");
            container.innerHTML = "";
            
            for (var i = 0; i < question.options.length; i++) {
                var btn = document.createElement("button");
                btn.className = "option-btn";
                btn.textContent = question.options[i];
                btn.onclick = (function(idx) {
                    return function() { selectOption(this, idx); };
                })(i);
                container.appendChild(btn);
            }
            
            document.getElementById("passButton").disabled = false;
            isAnswered = false;
            startQuestionTimer();
        }
        
        // ========== 선택 ==========
        function selectOption(button, index) {
            if (isAnswered) return;
            
            var buttons = document.querySelectorAll(".option-btn");
            for (var i = 0; i < buttons.length; i++) {
                buttons[i].classList.remove("selected");
            }
            button.classList.add("selected");
            
            setTimeout(function() { checkAnswer(index); }, 300);
        }
        
        // ========== 정답 확인 ==========
        function checkAnswer(selectedIndex) {
            if (isAnswered) return;
            isAnswered = true;
            clearInterval(timerInterval);
            
            var question = quizData[currentQuestionIndex];
            var buttons = document.querySelectorAll(".option-btn");
            
            for (var i = 0; i < buttons.length; i++) {
                buttons[i].disabled = true;
            }
            document.getElementById("passButton").disabled = true;
            
            resultData.push({
                kanji: question.question,
                userAnswer: question.options[selectedIndex],
                correctAnswer: question.options[question.correctIndex]
            });
            
            if (selectedIndex === question.correctIndex) {
                buttons[selectedIndex].classList.remove("selected");
                buttons[selectedIndex].classList.add("correct");
                score++;
            } else {
                buttons[selectedIndex].classList.remove("selected");
                buttons[selectedIndex].classList.add("wrong");
                buttons[question.correctIndex].classList.add("answer");
            }
            
            setTimeout(nextQuestion, 1500);
        }
        
        // ========== 패스 ==========
        function passQuestion() {
            if (isAnswered) return;
            isAnswered = true;
            clearInterval(timerInterval);
            
            var question = quizData[currentQuestionIndex];
            var buttons = document.querySelectorAll(".option-btn");
            
            for (var i = 0; i < buttons.length; i++) {
                buttons[i].disabled = true;
            }
            document.getElementById("passButton").disabled = true;
            
            resultData.push({
                kanji: question.question,
                userAnswer: "건너뜀",
                correctAnswer: question.options[question.correctIndex]
            });
            
            buttons[question.correctIndex].classList.add("answer");
            setTimeout(nextQuestion, 1500);
        }
        
        // ========== 시간 초과 ==========
        function handleTimeout() {
            if (isAnswered) return;
            isAnswered = true;
            
            var question = quizData[currentQuestionIndex];
            var buttons = document.querySelectorAll(".option-btn");
            
            for (var i = 0; i < buttons.length; i++) {
                buttons[i].disabled = true;
            }
            document.getElementById("passButton").disabled = true;
            
            resultData.push({
                kanji: question.question,
                userAnswer: "시간초과",
                correctAnswer: question.options[question.correctIndex]
            });
            
            buttons[question.correctIndex].classList.add("answer");
            setTimeout(nextQuestion, 1500);
        }
        
        // ========== 다음 문제 ==========
        function nextQuestion() {
            currentQuestionIndex++;
            
            if (currentQuestionIndex >= quizData.length) {
                endQuiz();
                return;
            }
            
            loadQuestion();
            updateDisplay();
        }
        
        // ========== 표시 업데이트 ==========
        function updateDisplay() {
            document.getElementById("currentQuestion").textContent = currentQuestionIndex + 1;
            document.getElementById("totalQuestions").textContent = quizData.length;
        }
        
        // ========== 퀴즈 종료 ==========
        function endQuiz() {
            if (timerInterval) clearInterval(timerInterval);
            
            // 결과 페이지로 이동 (파라미터로 전달)
            var params = "?level=" + testLevel + 
                         "&sector=" + testSector + 
                         "&score=" + score + 
                         "&total=" + quizData.length;
            
            window.location.href = "Test_result.jsp" + params;
        }
        
        // ========== 이벤트 ==========
        document.getElementById("passButton").onclick = passQuestion;
        window.onload = init;
    </script>
</body>
</html>