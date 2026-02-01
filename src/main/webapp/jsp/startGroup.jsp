<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.KanjiDTO" %>
<%@ page import="repository.KanjiRepository" %>
<%@ page import="util.StudyManager" %>
<%
    String level = request.getParameter("level");
    String groupStr = request.getParameter("group");
    
    if (level == null || groupStr == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    int groupNumber = Integer.parseInt(groupStr);
    
    // 전체 한자 리스트 가져오기
    List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
    
    // 해당 그룹의 한자 가져오기
    List<KanjiDTO> groupKanji = StudyManager.getKanjiByGroup(allKanji, groupNumber);
    
    if (groupKanji.isEmpty()) {
        response.sendRedirect("groupSelect.jsp?level=" + level);
        return;
    }
    
    // 세션에 현재 그룹 정보 저장
    session.setAttribute("currentGroup_" + level, groupNumber);
    session.setAttribute("groupKanji_" + level, groupKanji);
    
    // 학습 페이지로 이동
    response.sendRedirect("study.jsp?level=" + level + "&group=" + groupNumber + "&index=0");
%>