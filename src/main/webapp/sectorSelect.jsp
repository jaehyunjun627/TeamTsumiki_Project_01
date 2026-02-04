
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.model.dto.AccountDTO,model.KanjiDTO,java.util.List" %>
<%@ page import="data.KanjiRepository_KANJIDAO,java.util.List" %>
<%
AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String level = request.getParameter("level"); // N5, N4, N3...
    
    // 해당 레벨의 전체 한자 개수 가져오기 (메모리에서)
    List<KanjiDTO> allKanji = KanjiRepository_KANJIDAO.findByLevel(level);
    int totalKanji = (allKanji != null) ? allKanji.size() : 0;
    
    // 섹터당 20개씩, 총 몇 개의 섹터가 필요한지 계산
    int kanjiPerSector = 20;
    int totalSectors = (int) Math.ceil((double) totalKanji / kanjiPerSector);
    if (totalSectors == 0) totalSectors = 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=level%> 섹터 선택</title>
    <link rel="stylesheet" href="../css/sectorSelect.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%=level%></h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
            <p class="kanji-count">등록된 한자: <%=totalKanji%>개</p>
        </div>
        
        <div class="sector-grid">
            <%
            for (int i = 1; i <= 10; i++) { 
                            // 해당 섹터에 한자가 있는지 확인
                            int startIdx = (i - 1) * kanjiPerSector;
                            boolean hasKanji = (totalKanji > 0) && (startIdx < totalKanji);
            %>
                <%
                if (hasKanji) {
                %>
                    <button class="sector-btn active" 
                        onclick="location.href='kanjiStudy.jsp?level=<%=level%>&sector=<%=i%>'">
                        <%=String.format("%02d", i)%>
                    </button>
                <%
                } else {
                %>
                    <button class="sector-btn inactive" disabled>
                        <%=String.format("%02d", i)%>
                    </button>
                <%
                }
                %>
            <%
            }
            %>
        </div>
        
        <button class="back-btn" onclick="location.href='main.jsp'">돌아가기</button>
    </div>
</body>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.model.dto.AccountDTO,model.KanjiDTO,java.util.List" %>
<%@ page import="data.KanjiRepository_KANJIDAO,java.util.List" %>
<%
AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String level = request.getParameter("level"); // N5, N4, N3...
    
    // 해당 레벨의 전체 한자 개수 가져오기 (메모리에서)
    List<KanjiDTO> allKanji = KanjiRepository_KANJIDAO.findByLevel(level);
    int totalKanji = (allKanji != null) ? allKanji.size() : 0;
    
    // 섹터당 20개씩, 총 몇 개의 섹터가 필요한지 계산
    int kanjiPerSector = 20;
    int totalSectors = (int) Math.ceil((double) totalKanji / kanjiPerSector);
    if (totalSectors == 0) totalSectors = 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= level %> 섹터 선택</title>
    <link rel="stylesheet" href="../css/sectorSelect.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= level %></h1>
            <p>오늘의 작은 공부가 내일을 만들어요</p>
            <p class="kanji-count">등록된 한자: <%= totalKanji %>개</p>
        </div>
        
        <div class="sector-grid">
            <% for (int i = 1; i <= 10; i++) { 
                // 해당 섹터에 한자가 있는지 확인
                int startIdx = (i - 1) * kanjiPerSector;
                boolean hasKanji = (totalKanji > 0) && (startIdx < totalKanji);
            %>
                <% if (hasKanji) { %>
                    <button class="sector-btn active" 
                        onclick="location.href='kanjiStudy.jsp?level=<%= level %>&sector=<%= i %>'">
                        <%= String.format("%02d", i) %>
                    </button>
                <% } else { %>
                    <button class="sector-btn inactive" disabled>
                        <%= String.format("%02d", i) %>
                    </button>
                <% } %>
            <% } %>
        </div>
        
        <button class="back-btn" onclick="location.href='main.jsp'">돌아가기</button>
    </div>
</body>
>>>>>>> branch 'cheoluk' of https://github.com/jaehyunjun627/TeamTsumiki_Project_01.git
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>N1 Day 선택</title>
<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
    background: linear-gradient(135deg, #a8b5ff 0%, #c8b5ff 50%, #ffc4e1 100%);
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
}

.container {
    background: rgba(255, 255, 255, 0.95);
    padding: 50px 60px;
    border-radius: 30px;
    box-shadow: 0 30px 80px rgba(0, 0, 0, 0.15);
    max-width: 600px;
    width: 100%;
    backdrop-filter: blur(10px);
}

.header {
    text-align: center;
    margin-bottom: 40px;
}

.header h1 {
    font-size: 2.5em;
    font-weight: 700;
    color: #333;
    margin-bottom: 15px;
    letter-spacing: -1px;
}

.day-grid {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    gap: 12px;
    margin-bottom: 30px;
}

.day-circle {
    aspect-ratio: 1;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.1em;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    border: none;
    text-decoration: none;
    position: relative;
    overflow: hidden;
    background: linear-gradient(135deg, #e8e8e8 0%, #d5d5d5 100%);
    color: #999;
    <!-- backg,color 처음 색 반영 -->
}

.day-circle:hover {
    transform: translateY(-3px) scale(1.05);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
}

.day-circle:active {
    transform: translateY(-1px) scale(1.02);
}

.day-circle.completed {
    background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
    color: white;
    box-shadow: 0 4px 15px rgba(74, 144, 226, 0.4);
}

.day-circle.completed:hover { <!-- 마우스 대면 반응 -->
    box-shadow: 0 8px 25px rgba(74, 144, 226, 0.5);
}

.day-circle::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    width: 0;
    height: 0;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.5);
    transform: translate(-50%, -50%);
    transition: width 0.6s, height 0.6s;
}

.day-circle:hover::before {
    width: 100%;
    height: 100%;
}

.back-btn {
    background: linear-gradient(135deg, #6c757d 0%, #5a6268 100%);
    color: white;
    padding: 15px 30px;
    border: none;
    border-radius: 15px;
    font-size: 1.1em;
    font-weight: 600;
    cursor: pointer;
    width: 100%;
    transition: all 0.3s;
    box-shadow: 0 4px 15px rgba(108, 117, 125, 0.3);
}

.back-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(108, 117, 125, 0.4);
}

.back-btn:active {
    transform: translateY(0);
}

@media (max-width: 768px) {
    .container {
        padding: 30px 25px;
    }
    .header h1 {
        font-size: 2em;
    }
    .day-grid {
        grid-template-columns: repeat(7, 1fr);
        gap: 8px;
    }
    .day-circle {
        font-size: 0.9em;
    }
}

@media (max-width: 480px) {
    .day-grid {
        grid-template-columns: repeat(5, 1fr);
    }
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.container {
    animation: fadeIn 0.5s ease-out;
}

.day-circle {
    animation: fadeIn 0.5s ease-out backwards;
}

.day-circle:nth-child(1) { animation-delay: 0.05s; }
.day-circle:nth-child(2) { animation-delay: 0.1s; }
.day-circle:nth-child(3) { animation-delay: 0.15s; }
.day-circle:nth-child(4) { animation-delay: 0.2s; }
.day-circle:nth-child(5) { animation-delay: 0.25s; }
.day-circle:nth-child(6) { animation-delay: 0.3s; }
.day-circle:nth-child(7) { animation-delay: 0.35s; }
.day-circle:nth-child(8) { animation-delay: 0.4s; }
.day-circle:nth-child(9) { animation-delay: 0.45s; }
.day-circle:nth-child(10) { animation-delay: 0.5s; }
.day-circle:nth-child(11) { animation-delay: 0.55s; }
.day-circle:nth-child(12) { animation-delay: 0.6s; }
.day-circle:nth-child(13) { animation-delay: 0.65s; }
.day-circle:nth-child(14) { animation-delay: 0.7s; }
.day-circle:nth-child(15) { animation-delay: 0.75s; }
.day-circle:nth-child(16) { animation-delay: 0.8s; }
</style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>N1</h1>
    </div>
    
    <div class="day-grid">
        <a href="startGroup?day=1" class="day-circle" data-day="1">1</a>
        <a href="Kanji_Test.jsp?day=2" class="day-circle" data-day="2">2</a>
        <a href="Kanji_Test.jsp?day=3" class="day-circle" data-day="3">3</a>
        <a href="Kanji_Test.jsp?day=4" class="day-circle" data-day="4">4</a>
        <a href="Kanji_Test.jsp?day=5" class="day-circle" data-day="5">5</a>
        <a href="Kanji_Test.jsp?day=6" class="day-circle" data-day="6">6</a>
        <a href="Kanji_Test.jsp?day=7" class="day-circle" data-day="7">7</a>
        
        <a href="Kanji_Test.jsp?day=8" class="day-circle" data-day="8">8</a>
        <a href="Kanji_Test.jsp?day=9" class="day-circle" data-day="9">9</a>
        <a href="Kanji_Test.jsp?day=10" class="day-circle" data-day="10">10</a>
        <a href="Kanji_Test.jsp?day=11" class="day-circle" data-day="11">11</a>
        <a href="Kanji_Test.jsp?day=12" class="day-circle" data-day="12">12</a>
        <a href="Kanji_Test.jsp?day=13" class="day-circle" data-day="13">13</a>
        <a href="Kanji_Test.jsp?day=14" class="day-circle" data-day="14">14</a>
        
        <a href="Kanji_Test.jsp?day=15" class="day-circle" data-day="15">15</a>
        <a href="Kanji_Test.jsp?day=16" class="day-circle" data-day="16">16</a>
        <a href="Kanji_Test.jsp?day=17" class="day-circle" data-day="17">17</a>
        <a href="Kanji_Test.jsp?day=18" class="day-circle" data-day="18">18</a>
        <a href="Kanji_Test.jsp?day=19" class="day-circle" data-day="19">19</a>
        <a href="Kanji_Test.jsp?day=20" class="day-circle" data-day="20">20</a>
        <a href="Kanji_Test.jsp?day=21" class="day-circle" data-day="21">21</a>
        
        <a href="Kanji_Test.jsp?day=22" class="day-circle" data-day="22">22</a>
        <a href="Kanji_Test.jsp?day=23" class="day-circle" data-day="23">23</a>
        <a href="Kanji_Test.jsp?day=24" class="day-circle" data-day="24">24</a>
        <a href="Kanji_Test.jsp?day=25" class="day-circle" data-day="25">25</a>
        <a href="Kanji_Test.jsp?day=26" class="day-circle" data-day="26">26</a>
        <a href="Kanji_Test.jsp?day=27" class="day-circle" data-day="27">27</a>
        <a href="Kanji_Test.jsp?day=28" class="day-circle" data-day="28">28</a>
        <a href="Kanji_Test.jsp?day=28" class="day-circle" data-day="29">29</a>
        <a href="Kanji_Test.jsp?day=28" class="day-circle" data-day="30">30</a>
        <!-- a href="", 버튼을 누르면 ""안의 파일로 넘어감 -->
    </div>
    
    <button class="back-btn" onclick="history.back()">◀️ 뒤로 가기</button>
</div>

<script>
// 페이지 로드 시 완료한 Day 불러오기
window.onload = function() {
    const completedDays = JSON.parse(localStorage.getItem('completedDays') || '[]');
    completedDays.forEach(day => {
        const circle = document.querySelector(`[data-day="${day}"]`);
        if(circle) {
            circle.classList.add('completed');
        }
    });
};

// Day 클릭 시 완료 처리
document.querySelectorAll('.day-circle').forEach(circle => { //모든 day-circle 버튼 찾아서
    circle.addEventListener('click', function(e) { //각 버튼에 클릭 이벤트 리스너 추가 -> ~가 일어나면 ~해라
        const day = this.getAttribute('data-day');
        
        // localStorage에 저장
        let completedDays = JSON.parse(localStorage.getItem('completedDays') || '[]');
        if(!completedDays.includes(day)) {
            completedDays.push(day);
            localStorage.setItem('completedDays', JSON.stringify(completedDays));
        }
        
        // 파란색으로 변경
        this.classList.add('completed');
    });
});
</script>
</body>
>>>>>>> branch 'main' of https://github.com/jaehyunjun627/TeamTsumiki_Project_01.git
</html>