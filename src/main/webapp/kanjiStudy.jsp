<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 로그인 체크 -->
<c:if test="${empty sessionScope.loginUser}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>한자 학습 - ${level} 섹터 ${sector}</title>
    <link rel="stylesheet" href="css/kanjiStudy.css">
</head>
<body>
    <div class="container">
        <div class="header">한자 학습</div>

        <c:choose>
            <c:when test="${not empty currentKanji}">
                <!-- 진행 상황 -->
                <div class="progress">${currentIndex + 1}/${totalInSector}</div>

                <!-- 한자 표시 -->
                <div class="kanji-display">
                    <div class="kanji-char">
                        ${currentKanji.kanji}
                    </div>
                    <div class="korean-reading">${currentKanji.koreanMeaning}</div>
                </div>

                <!-- 읽기 정보 -->
                <div class="reading-section">
                    <div class="reading-row">
                        <span class="reading-label">음독:</span>
                        <span class="reading-value">
                            <c:out value="${currentKanji.onyomi1}" default="-"/>
                            <c:if test="${not empty currentKanji.onyomi2}">, ${currentKanji.onyomi2}</c:if>
                        </span>
                    </div>
                    <div class="reading-row">
                        <span class="reading-label">훈독:</span>
                        <span class="reading-value">
                            <c:out value="${currentKanji.kunyomi1}" default="-"/>
                            <c:if test="${not empty currentKanji.kunyomi2}">, ${currentKanji.kunyomi2}</c:if>
                        </span>
                    </div>
                </div>

                <!-- 예시 단어 -->
                <div class="example-section">
                    <h3>예시 단어</h3>
                    <c:if test="${not empty currentKanji.example1}">
                        <div class="example-item">${currentKanji.example1}</div>
                    </c:if>
                    <c:if test="${not empty currentKanji.example2}">
                        <div class="example-item">${currentKanji.example2}</div>
                    </c:if>
                    <c:if test="${not empty currentKanji.example3}">
                        <div class="example-item">${currentKanji.example3}</div>
                    </c:if>
                </div>

                <!-- 이전/다음 버튼 -->
                <div class="nav-buttons">
                    <c:choose>
                        <c:when test="${currentIndex == 0}">
                            <button class="nav-btn" disabled>◀</button>
                        </c:when>
                        <c:otherwise>
                            <button class="nav-btn"
                                onclick="location.href='kanjiStudy?level=${level}&sector=${sector}&index=${currentIndex - 1}'">
                                ◀
                            </button>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${currentIndex >= totalInSector - 1}">
                            <button class="nav-btn" disabled>▶</button>
                        </c:when>
                        <c:otherwise>
                            <button class="nav-btn"
                                onclick="location.href='kanjiStudy?level=${level}&sector=${sector}&index=${currentIndex + 1}'">
                                ▶
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <!-- 데이터 없음 -->
                <div class="no-data">
                    <p>등록된 한자가 없습니다.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- 돌아가기 버튼 -->
        <button class="back-btn" onclick="location.href='sectorSelect?level=${level}'">섹터 선택으로</button>
    </div>
</body>
</html>
