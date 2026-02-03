// src/main/java/servlet/StartTestServlet.java
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KanjiDTO;
import repository.KanjiRepository;
import util.StudyManager;

@WebServlet("/startTest")
public class StartTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        String groupStr = request.getParameter("group");
        
        if (level == null || groupStr == null) {
            response.sendRedirect("test");
            return;
        }
        
        int groupNumber = Integer.parseInt(groupStr);
        
        // 전체 한자 리스트 가져오기
        List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
        
        // 해당 그룹의 한자 가져오기 (2개)
        List<KanjiDTO> groupKanji = StudyManager.getKanjiByGroup(allKanji, groupNumber);
        
        if (groupKanji.isEmpty()) {
            response.sendRedirect("testGroupSelect?level=" + level);
            return;
        }
        
        // 정답 한자 (첫 번째 한자)
        KanjiDTO correctKanji = groupKanji.get(0);
        
        // 보기 4개 생성 (정답 1개 + 오답 3개)
        List<String> choices = generateChoices(correctKanji, allKanji);
        
        // 세션에 테스트 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("testLevel", level);
        session.setAttribute("testGroup", groupNumber);
        session.setAttribute("testKanji", groupKanji);
        session.setAttribute("currentTestIndex", 0);
        session.setAttribute("correctAnswer", correctKanji.getOnyomiCombined());
        
        // 테스트 페이지로 리다이렉트
        response.sendRedirect("testQuestion?level=" + level + "&group=" + groupNumber + "&index=0");
    }
    
    // 보기 4개 생성 (정답 포함)
    private List<String> generateChoices(KanjiDTO correctKanji, List<KanjiDTO> allKanji) {
        List<String> choices = new ArrayList<>();
        
        // 정답 추가
        String correctReading = correctKanji.getOnyomiCombined();
        if (correctReading.isEmpty()) {
            correctReading = correctKanji.getKunyomiCombined();
        }
        choices.add(correctReading);
        
        // 오답 3개 추가
        for (KanjiDTO kanji : allKanji) {
            if (choices.size() >= 4) break;
            
            String reading = kanji.getOnyomiCombined();
            if (reading.isEmpty()) {
                reading = kanji.getKunyomiCombined();
            }
            
            if (!reading.equals(correctReading) && !choices.contains(reading)) {
                choices.add(reading);
            }
        }
        
        // 부족하면 더미 데이터 추가
        while (choices.size() < 4) {
            choices.add("ダミー" + choices.size());
        }
        
        // 섞기
        Collections.shuffle(choices);
        
        return choices;
    }
}