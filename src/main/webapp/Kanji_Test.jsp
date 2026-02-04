<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kanji Test</title>

<style>
body {
    margin: 0;
    font-family: 'Arial', sans-serif;
    background: linear-gradient(135deg, #4facfe, #f093fb);
}

.wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.card {
    width: 720px;
    padding: 40px;
    border-radius: 20px;
    background: linear-gradient(135deg, #e3f2fd, #fde2e2);
    box-shadow:
        0 20px 40px rgba(0,0,0,0.25),
        0 0 0 10px rgba(255,255,255,0.4);
    text-align: center;
}

.count {
    text-align: right;
    font-size: 14px;
    color: #555;
}

.kanji {
    font-size: 100px;
    font-weight: bold;
    margin: 30px 0;
}

.reading, .meaning, .example {
    margin: 10px 0;
    font-size: 16px;
}

.buttons {
    margin-top: 30px;
}

.buttons a {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 56px;
    height: 56px;
    margin: 0 20px;
    border-radius: 50%;
    background: #ff6f91;
    color: white;
    font-size: 22px;
    text-decoration: none;
    box-shadow: 0 6px 15px rgba(0,0,0,0.3);
}
</style>
</head>

<body>

<%
    // 1️⃣ day 파라미터 받기
    String day = request.getParameter("day");
    if (day == null) day = "1";

    // 2️⃣ Day별 임시 한자 데이터 (N5 예시)
    Map<String, List<String[]>> dayMap = new HashMap<>();

    dayMap.put("1", Arrays.asList(
        new String[]{"日","ニチ","ひ","날","今日はいい天気です。"},
        new String[]{"月","ゲツ","つき","달","月がきれいです。"},
        new String[]{"人","ジン","ひと","사람","あの人は学生です。"},
        new String[]{"山","サン","やま","산","山に登ります。"},
        new String[]{"川","セン","かわ","강","川で遊びます。"}
    ));

    List<String[]> list = dayMap.get(day);

    // 안전장치
    if (list == null || list.size() == 0) {
%>
        <p>해당 Day의 한자 데이터가 없습니다.</p>
<%
        return;
    }

    // 3️⃣ 랜덤 한자 선택
    Random r = new Random();
    int idx = r.nextInt(list.size());
    String[] kanji = list.get(idx);
%>

<h1 style="text-align:center;">한자 학습 페이지</h1>
<p style="text-align:center;">선택한 Day : <strong><%= day %></strong></p>

<div class="wrapper">
    <div class="card">

        <div class="count"><%= (idx + 1) %> / <%= list.size() %></div>

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
            <a href="Kanji_Test.jsp?day=<%= day %>">◀</a>
            <a href="Kanji_Test.jsp?day=<%= day %>">▶</a>
        </div>

    </div>
</div>

<div style="text-align:center; margin-top:30px;">
    <button onclick="history.back()">◀ 뒤로 가기</button>
</div>

</body>
</html>