<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>register</title>
<link rel="stylesheet" type="text/css" href="../css/addrlist.css">

<style>
.error-message {
    color: red;
    font-size: 12px;
    margin-top: 5px;
    display: none;
}
.success-message {
    color: green;
    font-size: 12px;
    margin-top: 5px;
    display: none;
}
.input-group {
    margin-bottom: 15px;
}
</style>
</head>
<body>
<div class="container">
		<form action="register_process.jsp" method="post" onsubmit="return validateForm();">
			<div class="input-group">
				<label>이름</label>
				<input type="text" name="username" placeholder="이름 입력" required /><br />
			</div>
			
			<div class="input-group">
				<label>비밀번호</label>
				<input type="password" id="password" name="password" placeholder="비밀번호 입력" required><br />
			</div>
			
			<div class="input-group">
				<label>비밀번호 확인</label>
				<input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호 확인" required><br />
				<span id="passwordError" class="error-message">비밀번호가 일치하지 않습니다.</span>
				<span id="passwordSuccess" class="success-message">비밀번호가 일치합니다.</span>
			</div>
			
			<div class="input-group">
				<label>닉네임</label>
				<input type="text" name="nickname" placeholder="닉네임" required /><br />
			</div>
			
			<p></p>
			<div>
				<input type="submit" id="submitBtn" value="회원가입"/>
				<p></p>
				<input type="reset" value="취소"/>
			</div>
		</form>
</div>

<script>
// 비밀번호 확인 필드에 이벤트 리스너 추가
document.getElementById('passwordConfirm').addEventListener('keyup', checkPasswordMatch);
document.getElementById('password').addEventListener('keyup', checkPasswordMatch);

function checkPasswordMatch() {
    var password = document.getElementById('password').value;
    var passwordConfirm = document.getElementById('passwordConfirm').value;
    var errorMsg = document.getElementById('passwordError');
    var successMsg = document.getElementById('passwordSuccess');
    var submitBtn = document.getElementById('submitBtn');
    
    // 비밀번호 확인란이 비어있으면 메시지 숨김
    if (passwordConfirm === '') {
        errorMsg.style.display = 'none';
        successMsg.style.display = 'none';
        return;
    }
    
    // 비밀번호 일치 여부 확인
    if (password !== passwordConfirm) {
        errorMsg.style.display = 'block'; // 에러 메시지 보이기
        successMsg.style.display = 'none'; // 성공 메시지 숨기기
        submitBtn.disabled = true;
        submitBtn.style.opacity = '0.5';
        submitBtn.style.cursor = 'not-allowed';
    } else {
        errorMsg.style.display = 'none';
        successMsg.style.display = 'block';
        submitBtn.disabled = false;
        submitBtn.style.opacity = '1';
        submitBtn.style.cursor = 'pointer';
    }
}

// 이중 안전장치, 제출 직전에 최종 확인 한 번 더
function validateForm() {
    var password = document.getElementById('password').value;
    var passwordConfirm = document.getElementById('passwordConfirm').value;
    
    if (password !== passwordConfirm) {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    }
    
    return true;
}
</script>

</body>
</html>