<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>복습 테스트 - 레벨 선택</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="main-layout">
        <div class="left-section">
            <div class="profile-card">
                <div class="profile-content">
                    <h2>복습 테스트</h2>
                    <p>레벨을 선택하세요</p>
                </div>
            </div>
        </div>
        
        <div class="right-section">
            <div class="study-section">
                <div class="section-header">
                    <h2>레벨 선택</h2>
                    <p class="section-subtitle">테스트할 레벨을 선택하세요!</p>
                </div>
                
                <div class="level-buttons">
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/testGroupSelect?level=N5'">
                        N5
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/testGroupSelect?level=N4'">
                        N4
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/testGroupSelect?level=N3'">
                        N3
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/testGroupSelect?level=N2'">
                        N2
                    </button>
                    <button class="level-btn" onclick="location.href='${pageContext.request.contextPath}/testGroupSelect?level=N1'">
                        N1
                    </button>
                </div>
                
                <div style="margin-top: 30px; text-align: center;">
                    <button class="tool-btn" onclick="location.href='${pageContext.request.contextPath}/index.jsp'" 
                            style="max-width: 300px; background: linear-gradient(135deg, #9e9e9e 0%, #bdbdbd 100%);">
                        메인으로 돌아가기
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>