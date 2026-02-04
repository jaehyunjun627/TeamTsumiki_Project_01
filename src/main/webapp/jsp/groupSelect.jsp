<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${level} - 그룹 선택</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css?v=4">
</head>
<body>
    <div class="group-container">
        <div class="group-header">
            <h1 class="level-title">${level}</h1>
            <a href="${pageContext.request.contextPath}/index.jsp" class="back-link">← 레벨 선택</a>
        </div>
        
        <div class="progress-summary">
            <div class="progress-text">
                학습 진행도: ${progress.completedCount} / ${totalGroups} 그룹
            </div>
            <div class="progress-text">
                학습한 한자: ${progress.learnedKanjiCount}개
            </div>
            <c:if test="${progress.completedCount == totalGroups && totalGroups > 0}">
                <button class="reset-button" onclick="if(confirm('학습 진행도를 초기화하시겠습니까?')) location.href='${pageContext.request.contextPath}/resetProgress?level=${level}'">
                    처음부터 다시 시작
                </button>
            </c:if>
        </div>
        
        <div class="group-grid">
            <c:forEach var="i" begin="1" end="${totalGroups}">
                <c:set var="isCompleted" value="${progress.isGroupCompleted(i)}" />
                <button class="group-button ${isCompleted ? 'completed' : ''}" 
                        onclick="location.href='${pageContext.request.contextPath}/startGroup?level=${level}&group=${i}'">
                    <c:out value="${String.format('%02d', i)}" />
                </button>
            </c:forEach>
        </div>
    </div>
</body>
</html>