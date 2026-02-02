<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일본어 한자 공부장 - 메인</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
    <div class="main-container">
        <h1 class="main-title">일본어 한자 공부장</h1>
        <p class="main-subtitle">JLPT 한자를 효과적으로 학습하세요</p>
        
        <div class="level-section">
            <h2>학습할 레벨을 선택하세요</h2>
            <div class="level-buttons">
                <button class="level-button" onclick="location.href='groupSelect.jsp?level=N5'">N5</button>
                <button class="level-button" onclick="location.href='groupSelect.jsp?level=N4'">N4</button>
                <button class="level-button" onclick="location.href='groupSelect.jsp?level=N3'">N3</button>
                <button class="level-button" onclick="location.href='groupSelect.jsp?level=N2'">N2</button>
                <button class="level-button" onclick="location.href='groupSelect.jsp?level=N1'">N1</button>
            </div>
        </div>
    </div>
</body>
</html>