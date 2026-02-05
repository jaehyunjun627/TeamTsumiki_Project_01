<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 로그인 체크 -->
<c:if test="${empty sessionScope.loginUser}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${param.level} 섹터 선택</title>
    <link rel="stylesheet" href="css/sectorSelect.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>${param.level}</h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
            <p class="kanji-count">등록된 한자: ${totalKanji}개</p>
        </div>

        <div class="sector-grid">
            <c:forEach var="i" begin="1" end="10">
                <c:set var="startIdx" value="${(i - 1) * 20}"/>
                <c:choose>
                    <c:when test="${totalKanji > 0 && startIdx < totalKanji}">
                        <button class="sector-btn active"
                            onclick="location.href='kanjiStudy?level=${param.level}&sector=${i}'">
                            <fmt:formatNumber value="${i}" pattern="00"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button class="sector-btn inactive" disabled>
                            <fmt:formatNumber value="${i}" pattern="00"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <button class="back-btn" onclick="location.href='main'">돌아가기</button>
    </div>
</body>
</html>
