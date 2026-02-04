<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.KanjiDTO" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JLPT 복습</title>
</head>
<body>

<h2>복습 페이지</h2>

<%
List<KanjiDTO> pageList =
    (List<KanjiDTO>) request.getAttribute("pageList");

if (pageList != null) {
    for (KanjiDTO k : pageList) {
%>
    <div style="margin-bottom:20px;">
        <div>한자: <%= k.getKanji() %></div>
        <div>음독: <%= k.getUnyomi_1() %></div>
        <div>훈독: <%= k.getKunyomi_1() %></div>
        <div>뜻: <%= k.getKorean() %></div>
    </div>
    <hr>
<%
    }
}
%>


<%
int currentPage = (Integer) request.getAttribute("page");
String level = request.getParameter("level");
if (level == null) level = "N5";
%>

<div style="margin-top:30px; text-align:center;">

    <%-- 이전 페이지 --%>
    <% if (currentPage > 1) { %>
        <a href="review.do?level=<%= level %>&page=<%= currentPage - 1 %>">
            ◀ 이전
        </a>
    <% } %>

    <span style="margin:0 20px;">
        페이지 <%= currentPage %>
    </span>

    <%-- 다음 페이지 --%>
    <% if (pageList != null && pageList.size() == 4) { %>
        <a href="review.do?level=<%= level %>&page=<%= currentPage + 1 %>">
            다음 ▶
        </a>
    <% } %>

</div>





</body>
</html>