<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>섹터 한자 학습</title>

<style>
/* 카드 UI 스타일 */
.card {
    width: 600px;
    margin: 50px auto;
    padding: 30px;
    background: #fff;
    border-radius: 15px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    text-align: center;
}
.kanji { font-size: 60px; }
.buttons a {
    margin: 0 20px;
    font-size: 20px;
}
</style>
</head>

<body>

<%
    // ===== 1️⃣ 서버(JSP) 영역 =====
    String sector = request.getParameter("sector");
    if (sector == null) sector = "1";

    Map<String, List<String[]>> sectorMap = new HashMap<>();
    sectorMap.put("1", Arrays.asList(
        new String[]{"場", "ジョウ", "ば", "장소", "ここは工場です。"},
        new String[]{"所", "ショ", "ところ", "곳", "静かな所です。"},
        new String[]{"地", "チ", "ち", "땅", "地図を見ます。"},
        new String[]{"国", "コク", "くに", "나라", "日本の国です。"},
        new String[]{"町", "チョウ", "まち", "마을", "小さい町です。"}
    ));

    List<String[]> list = sectorMap.get(sector);
    Random r = new Random();
    String[] kanji = list.get(r.nextInt(list.size()));
%>

<!-- ===== 2️⃣ 여기부터 화면(UI) ===== -->

<div class="card">
    <div class="count">5 / 20</div>

    <div class="kanji"><%= kanji[0] %></div>

    <div class="reading">
        음독 : <%= kanji[1] %> / 훈독 : <%= kanji[2] %>
    </div>

    <div class="meaning">
        의미 : <%= kanji[3] %>
    </div>

    <div class="example">
        예문 : <%= kanji[4] %>
    </div>

    <div class="buttons">
        <a href="Sector_Kanji.jsp?sector=<%= sector %>">◀</a>
        <a href="Sector_Kanji.jsp?sector=<%= sector %>">▶</a>
    </div>
</div>

</body>
</html>