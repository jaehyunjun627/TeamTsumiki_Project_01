<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" type="text/css" href="../projectCss/project.css">
</head>    
<body>
    <%
    Boolean success = (Boolean) request.getAttribute("success");
    if (success != null && success) {
    %>
    <script>
    alert("회원가입을 축하드립니다 ! !");
    window.location.href = "login.jsp";
    </script>
    <%
    }
    %> 

    <center>
    <div class="signup-container">
        <h3>회원가입</h3>
        <h1>재미있게 즐기는 일본어 공부, 지금 바로 시작해 보세요 !</h1>
        
        <form id="signupForm" method="post">
            <div class="singup-form-group">
                <label for="username">닉네임</label>
                <input type="text" id="username" name="username" placeholder="닉네임" required>
            </div>

            <div class="singup-form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" placeholder="이메일" required>
        </div>

            <div class="singup-form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" placeholder="비밀번호" required>
            </div>

            <div class="singup-form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 확인" required>
            </div>

            <button type="submit" class="btn-submit">가입하기</button>
        </form><br>

        <div class="login-link">
            이미 계정이 있으신가요? <a href="login.jsp">로그인</a>
        </div>
    </div>


    <script>
        // 비밀번호 확인만 체크
        document.getElementById('signupForm').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('비밀번호가 일치하지 않습니다.');
                return false;
            }
        });
    </script>

    </center>
</body>
</html>
