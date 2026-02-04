<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // 서블릿으로 리다이렉트 (스크립틀릿 -> 서블릿 전환)
    response.sendRedirect(request.getContextPath() + "/main");
%>
