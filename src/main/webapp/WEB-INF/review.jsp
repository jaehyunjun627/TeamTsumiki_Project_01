<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JLPT 복습</title>
</head>
<body>

<h2>복습 페이지</h2>

<c:forEach var="kanji" items="${pageList}">
    <div style="margin-bottom:20px;">
        <div>한자: ${kanji.kanji}</div>
        <div>음독: ${kanji.unyomi_1}</div>
        <div>훈독: ${kanji.kunyomi_1}</div>
        <div>뜻: ${kanji.korean}</div>
    </div>
    <hr>
</c:forEach>


<div style="margin-top:30px; text-align:center;">

    <%-- 이전 페이지 --%>
    <c:if test="${currentPage > 1}">
        <c:url var="prevUrl" value="/review.do">
            <c:param name="level" value="${level}" />
            <c:param name="page" value="${currentPage - 1}" />
        </c:url>
        <a href="${prevUrl}">
            ◀ 이전
        </a>
    </c:if>

    <span style="margin:0 20px;">
        페이지 ${currentPage}
    </span>

    <%-- 다음 페이지 --%>
    <c:if test="${hasNext}">
        <c:url var="nextUrl" value="/review.do">
            <c:param name="level" value="${level}" />
            <c:param name="page" value="${currentPage + 1}" />
        </c:url>
        <a href="${nextUrl}">
            다음 ▶
        </a>
    </c:if>

</div>





</body>
</html>
