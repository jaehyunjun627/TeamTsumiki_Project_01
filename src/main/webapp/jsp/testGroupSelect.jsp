<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${level} - 테스트 그룹 선택</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/group.css?v=4">
</head>
<body>
    <div class="group-container">
        <div class="group-header">
            <h1 class="level-title">${level} 테스트</h1>
            <a href="${pageContext.request.contextPath}/test" class="back-link">← 레벨 선택</a>
        </div>
        
        <div class="progress-summary">
            <div class="progress-text">
                테스트할 그룹을 선택하세요
            </div>
        </div>
        
        <div class="group-grid">
            <c:forEach var="i" begin="1" end="${totalGroups}">
                <button class="group-button" 
                        onclick="location.href='${pageContext.request.contextPath}/startTest?level=${level}&group=${i}'">
                    ${String.format("%02d", i)}
                </button>
            </c:forEach>
        </div>
    </div>
</body>
</html>