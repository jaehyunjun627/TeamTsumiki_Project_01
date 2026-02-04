<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.model.dto.AccountDTO" %>
<%@ page import="model.AttendanceDAO_LOGDAO" %>
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
    String scoreParam = request.getParameter("score");
    String totalParam = request.getParameter("total");
    
    int sector = 1;
    int score = 0;
    int total = 10;
    
    try {
        if (sectorParam != null) sector = Integer.parseInt(sectorParam);
        if (scoreParam != null) score = Integer.parseInt(scoreParam);
        if (totalParam != null) total = Integer.parseInt(totalParam);
    } catch (NumberFormatException e) {
        // 기본값 사용
    }
    
    // ========== 출석 체크 (테스트 완료 시 출석 기록) ==========
    AttendanceDAO_LOGDAO attendanceDAO = new AttendanceDAO_LOGDAO();
    boolean attendanceResult = attendanceDAO.checkAttendance(user.getUserID());
    
    // ========== 결과 메시지 및 아이콘 설정 ==========
    double percentage = (total > 0) ? ((double) score / total * 100) : 0;
    String message = "";
    String icon = "";
    
    if (percentage == 100) {
        message = "완벽합니다! 🌟<br>모든 문제를 맞히셨네요!";
        icon = "🏆";
    } else if (percentage >= 80) {
        message = "훌륭해요!<br>조금만 더 복습하면 완벽해요!";
        icon = "🎉";
    } else if (percentage >= 60) {
        message = "좋아요!<br>꾸준히 노력하고 있네요!";
        icon = "😊";
    } else if (percentage >= 40) {
        message = "괜찮아요!<br>복습이 좀 더 필요해요!";
        icon = "📚";
    } else {
        message = "힘내세요!<br>다시 학습하고 도전해보세요!";
        icon = "💪";
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>퀴즈 결과</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        
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
        
        .result-icon { font-size: 80px; margin-bottom: 20px; }
        
        .result-title {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
            font-weight: bold;
        }
        
        .level-info {
            font-size: 16px;
            color: #666;
            margin-bottom: 30px;
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
            margin-bottom: 20px;
        }
        
        .attendance-badge {
            display: inline-block;
            background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            font-size: 14px;
            font-weight: 600;
            margin-bottom: 30px;
        }
        
        .attendance-badge.already {
            background: linear-gradient(135deg, #9e9e9e 0%, #757575 100%);
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
            text-decoration: none;
            display: block;
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
            .result-container { padding: 30px; }
            .result-title { font-size: 26px; }
            .score-display { font-size: 40px; }
        }
    </style>
</head>
<body>
    <div class="result-container">
        <div class="result-icon"><%= icon %></div>
        <h1 class="result-title">퀴즈 완료!</h1>
        <p class="level-info"><%= level %> - 섹터 <%= sector %></p>
        
        <div class="score-display">
            <%= score %> / <%= total %>
        </div>
        
        <div class="score-label">맞힌 문제 수</div>
        
        <% if (attendanceResult) { %>
            <div class="attendance-badge">✅ 오늘 출석 완료!</div>
        <% } else { %>
            <div class="attendance-badge already">📌 이미 출석 처리됨</div>
        <% } %>
        
        <div class="result-message">
            <%= message %>
        </div>
        
        <div class="button-group">
            <a href="Test_main.jsp?level=<%= level %>&sector=<%= sector %>" class="btn btn-primary">
                🔄 다시 도전하기
            </a>
            <a href="kanjiStudy.jsp?level=<%= level %>&sector=<%= sector %>" class="btn btn-secondary">
                📖 다시 학습하기
            </a>
            <a href="main.jsp" class="btn btn-secondary">
                🏠 홈으로
            </a>
        </div>
    </div>
</body>
</html>