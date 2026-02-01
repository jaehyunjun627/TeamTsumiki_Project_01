<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.StudyProgressDTO" %>
<%
    String level = request.getParameter("level");
    String groupStr = request.getParameter("group");
    
    if (level == null || groupStr == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    int groupNumber = Integer.parseInt(groupStr);
    
    // 학습 진행 상태 업데이트
    StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
    if (progress != null) {
        progress.addCompletedGroup(groupNumber);
        session.setAttribute("studyProgress_" + level, progress);
    }
    
    // 그룹 한자 세션 제거
    session.removeAttribute("groupKanji_" + level);
    session.removeAttribute("currentGroup_" + level);
    
    // 그룹 선택 화면으로 이동
    response.sendRedirect("groupSelect.jsp?level=" + level);
%>