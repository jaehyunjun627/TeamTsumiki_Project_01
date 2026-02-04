<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 서블릿으로 리다이렉트 (스크립틀릿 -> 서블릿 전환)
    String level = request.getParameter("level");
    String sector = request.getParameter("sector");
    String index = request.getParameter("index");

    StringBuilder url = new StringBuilder(request.getContextPath() + "/kanjiStudy?");
    if (level != null) url.append("level=").append(level);
    if (sector != null) url.append("&sector=").append(sector);
    if (index != null) url.append("&index=").append(index);

    response.sendRedirect(url.toString());
%>
