//src/main/java/servlet/TestQuestionServlet.java
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
import model.TestAnswerRecord;
import repository.KanjiRepository;

@WebServlet("/testQuestion")
public class TestQuestionServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
     
     HttpSession session = request.getSession();
     
     // URL에서 index 파라미터 가져오기
     String indexParam = request.getParameter("index");
     int currentIndex = 0;
     
     if (indexParam != null) {
         try {
             currentIndex = Integer.parseInt(indexParam);
         } catch (NumberFormatException e) {
             currentIndex = 0;
         }
     }
     
     @SuppressWarnings("unchecked")
     List<KanjiDTO> testKanji = (List<KanjiDTO>) session.getAttribute("testKanji");
     String level = (String) session.getAttribute("testLevel");
     Integer group = (Integer) session.getAttribute("testGroup");
     
     if (testKanji == null || level == null) {
         response.sendRedirect("test");
         return;
     }
     
     // 인덱스 범위 체크
     if (currentIndex < 0 || currentIndex >= testKanji.size()) {
         response.sendRedirect("testGroupSelect?level=" + level);
         return;
     }
     
     // 세션에 현재 인덱스 저장
     session.setAttribute("currentTestIndex", currentIndex);
     
     // 테스트 결과 리스트 초기화 (첫 문제일 때)
     if (currentIndex == 0) {
         session.setAttribute("testAnswerRecords", new ArrayList<TestAnswerRecord>());
         session.setAttribute("testCorrectCount", 0);
     }
     
     // 현재 문제 한자
     KanjiDTO currentKanji = testKanji.get(currentIndex);
     
     // 전체 한자 리스트 (오답 보기 생성용)
     List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
     
     // 보기 4개 생성
     List<String> choices = generateChoices(currentKanji, allKanji);
     
     // 정답
     String correctAnswer = currentKanji.getOnyomiCombined();
     if (correctAnswer.isEmpty()) {
         correctAnswer = currentKanji.getKunyomiCombined();
     }
     
     // JSP로 데이터 전달
     request.setAttribute("currentKanji", currentKanji);
     request.setAttribute("choices", choices);
     request.setAttribute("correctAnswer", correctAnswer);
     request.setAttribute("currentIndex", currentIndex);
     request.setAttribute("totalCount", testKanji.size());
     request.setAttribute("level", level);
     request.setAttribute("group", group);
     
     request.getRequestDispatcher("/jsp/testQuestion.jsp").forward(request, response);
 }
 
 private List<String> generateChoices(KanjiDTO correctKanji, List<KanjiDTO> allKanji) {
     List<String> choices = new ArrayList<>();
     
     String correctReading = correctKanji.getOnyomiCombined();
     if (correctReading.isEmpty()) {
         correctReading = correctKanji.getKunyomiCombined();
     }
     choices.add(correctReading);
     
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
     
     while (choices.size() < 4) {
         choices.add("ダミー" + choices.size());
     }
     
     Collections.shuffle(choices);
     return choices;
 }
}