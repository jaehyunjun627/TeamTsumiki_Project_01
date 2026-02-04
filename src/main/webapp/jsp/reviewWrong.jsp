<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>틀린 문제 검토</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/review.css?v=3">
</head>
<body>
    <c:set var="kanji" value="${currentRecord.kanji}" />
    <c:set var="exampleWords" value="${fn:split(kanji.example_word, '、')}" />
    
    <div class="review-container">
        <div class="review-header">
            <a href="${pageContext.request.contextPath}/testResult" class="back-link">← 결과로 돌아가기</a>
            <div class="review-progress">
                틀린 문제 ${currentIndex + 1} / ${totalWrong}
            </div>
        </div>
        
        <div class="review-card">
            <div class="wrong-indicator">
                <c:choose>
                    <c:when test="${currentRecord.skipped}">
                        ⊘ 건너뜀
                    </c:when>
                    <c:otherwise>
                        ✗ 오답
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="kanji-display">
                ${kanji.kanji}
            </div>
            
            <div class="kanji-korean">
                ${kanji.korean}
            </div>
            
            <div class="answer-comparison">
                <div class="answer-box correct-box">
                    <div class="answer-label">정답</div>
                    <div class="answer-value">${currentRecord.correctAnswer}</div>
                </div>
                
                <c:choose>
                    <c:when test="${currentRecord.skipped}">
                        <div class="answer-box skip-box">
                            <div class="answer-label">내 답</div>
                            <div class="answer-value">-</div>
                            <div class="skip-text">모르겠어요 선택</div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="answer-box wrong-box">
                            <div class="answer-label">내 답</div>
                            <div class="answer-value">${currentRecord.userAnswer}</div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="kanji-details">
                <div class="detail-section">
                    <div class="detail-label">음독</div>
                    <div class="detail-value">
                        ${empty kanji.onyomiCombined ? '-' : kanji.onyomiCombined}
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">훈독</div>
                    <div class="detail-value">
                        ${empty kanji.kunyomiCombined ? '-' : kanji.kunyomiCombined}
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">뜻</div>
                    <div class="detail-value">
                        ${kanji.explantion}
                    </div>
                </div>
                
                <div class="detail-section">
                    <div class="detail-label">예시</div>
                    <div class="detail-value examples">
                        <c:forEach var="example" items="${exampleWords}">
                            <span class="example-item">${fn:trim(example)}</span>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="review-navigation">
            <c:choose>
                <c:when test="${currentIndex > 0}">
                    <button class="nav-btn prev-btn" 
                            onclick="location.href='${pageContext.request.contextPath}/reviewWrong?index=${currentIndex - 1}'">
                        ← 이전 오답
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="nav-btn prev-btn" disabled>
                        ← 이전 오답
                    </button>
                </c:otherwise>
            </c:choose>
            
            <c:choose>
                <c:when test="${currentIndex < totalWrong - 1}">
                    <button class="nav-btn next-btn" 
                            onclick="location.href='${pageContext.request.contextPath}/reviewWrong?index=${currentIndex + 1}'">
                        다음 오답 →
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="nav-btn finish-btn" 
                            onclick="location.href='${pageContext.request.contextPath}/testResult'">
                        검토 완료
                    </button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>