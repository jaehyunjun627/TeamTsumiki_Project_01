<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>테스트 결과</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/testResult.css">
</head>
<body>
    <c:set var="score" value="${(correctCount * 100.0 / totalCount)}" />
    <div class="result-container">
        <div class="result-header">
            <h1>테스트 결과</h1>
            <p class="result-level">${level} - 그룹 ${String.format("%02d", group)}</p>
        </div>
        
        <div class="score-card">
            <div class="score-circle">
                <div class="score-number">${Math.round(score)}</div>
                <div class="score-label">점</div>
            </div>
        </div>
        
        <div class="result-summary">
            <div class="summary-item">
                <div class="summary-label">전체 문제</div>
                <div class="summary-value">${totalCount}개</div>
            </div>
            <div class="summary-item correct">
                <div class="summary-label">정답</div>
                <div class="summary-value">${correctCount}개</div>
            </div>
            <div class="summary-item wrong">
                <div class="summary-label">오답</div>
                <div class="summary-value">${wrongCount}개</div>
            </div>
        </div>
        
        <div class="result-message">
            <c:choose>
                <c:when test="${score == 100}">
                    <p class="message-text">🎉 완벽해요! 모든 문제를 맞췄습니다!</p>
                </c:when>
                <c:when test="${score >= 80}">
                    <p class="message-text">👏 잘했어요! 조금만 더 노력하면 완벽!</p>
                </c:when>
                <c:when test="${score >= 60}">
                    <p class="message-text">💪 괜찮아요! 복습하면 더 좋아질 거예요!</p>
                </c:when>
                <c:otherwise>
                    <p class="message-text">📚 다시 한번 공부해봐요!</p>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div class="result-buttons">
            <c:choose>
                <c:when test="${wrongCount > 0}">
                    <button class="result-btn review-btn" onclick="location.href='${pageContext.request.contextPath}/reviewWrong?index=0'">
                        틀린 문제 검토하기 (${wrongCount}개)
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="result-btn review-btn" disabled style="opacity: 0.5; cursor: not-allowed;">
                        모든 문제를 맞췄습니다!
                    </button>
                </c:otherwise>
            </c:choose>
            <button class="result-btn home-btn" onclick="location.href='${pageContext.request.contextPath}/index.jsp'">
                메인으로 돌아가기
            </button>
        </div>
    </div>
</body>
</html>