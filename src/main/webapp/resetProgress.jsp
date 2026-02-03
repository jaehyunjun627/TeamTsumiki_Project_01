<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.StudyProgressDTO" %>
<%
    String level = request.getParameter("level");
    if (level == null) {
        response.sendRedirect("main.jsp");
        return;
    }
    
    // 해당 레벨의 학습 진행 상태 초기화
    StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
    if (progress != null) {
        progress.resetProgress();
        session.setAttribute("studyProgress_" + level, progress);
    }
    
    // 그룹 선택 페이지로 이동 (dailyCount.jsp가 아님!)
    response.sendRedirect("groupSelect.jsp?level=" + level);
%>