<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
</head>
<body>
    <center>
    <form id="loginForm" method="post" action="#">
    <h3>로그인</h3>
            <div class="singup-form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" placeholder="이메일" required>
        </div><br>

            <div class="singup-form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" placeholder="비밀번호" required>
            </div><br>

            <button type="submit" class="btn-submit">로그인</button>
        </form><br>
    </center>   
</body>
</html>