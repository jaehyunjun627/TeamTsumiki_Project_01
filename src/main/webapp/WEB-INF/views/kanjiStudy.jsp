<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>한자 학습 - ${level} 섹터 ${sector}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/kanjiStudy.css">
</head>
<body>
    <div class="container">
        <div class="header">한자 학습</div>

        <c:choose>
            <c:when test="${currentKanji != null}">
                <div class="progress">${currentIndex + 1}/${totalInSector}</div>

                <div class="kanji-display">
                    <div class="kanji-char" onclick="location.href='${pageContext.request.contextPath}/kanjiDetail?level=${level}&sector=${sector}&index=${currentIndex}'">
                        ${currentKanji.kanji}
                    </div>
                    <div class="korean-reading">${currentKanji.korean}</div>
                </div>

                <div class="reading-section">
                    <div class="reading-row">
                        <span class="reading-label">음독:</span>
                        <span class="reading-value">
                            <c:out value="${currentKanji.unyomi_1 != null ? currentKanji.unyomi_1 : '-'}" />
                            <c:if test="${currentKanji.unyomi_2 != null}">, ${currentKanji.unyomi_2}</c:if>
                        </span>
                    </div>
                    <div class="reading-row">
                        <span class="reading-label">훈독:</span>
                        <span class="reading-value">
                            <c:out value="${currentKanji.kunyomi_1 != null ? currentKanji.kunyomi_1 : '-'}" />
                            <c:if test="${currentKanji.kunyomi_2 != null}">, ${currentKanji.kunyomi_2}</c:if>
                        </span>
                    </div>
                </div>

                <div class="example-section">
                    <h3>예시 단어</h3>
                    <c:if test="${currentKanji.example_word_1 != null && !empty currentKanji.example_word_1}">
                        <div class="example-item">${currentKanji.example_word_1}</div>
                    </c:if>
                    <c:if test="${currentKanji.example_word_2 != null && !empty currentKanji.example_word_2}">
                        <div class="example-item">${currentKanji.example_word_2}</div>
                    </c:if>
                    <c:if test="${currentKanji.example_word_3 != null && !empty currentKanji.example_word_3}">
                        <div class="example-item">${currentKanji.example_word_3}</div>
                    </c:if>
                </div>

                <div class="nav-buttons">
                    <button class="nav-btn"
                        <c:if test="${currentIndex == 0}">disabled</c:if>
                        onclick="location.href='${pageContext.request.contextPath}/kanjiStudy?level=${level}&sector=${sector}&index=${currentIndex - 1}'">
                        ◀
                    </button>
                    <button class="nav-btn"
                        <c:if test="${currentIndex >= totalInSector - 1}">disabled</c:if>
                        onclick="location.href='${pageContext.request.contextPath}/kanjiStudy?level=${level}&sector=${sector}&index=${currentIndex + 1}'">
                        ▶
                    </button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-data">
                    <p>등록된 한자가 없습니다.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <button class="back-btn" onclick="location.href='${pageContext.request.contextPath}/sectorSelect?level=${level}'">섹터 선택으로</button>
    </div>
</body>
</html>
