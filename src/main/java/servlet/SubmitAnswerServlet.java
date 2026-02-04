//src/main/java/servlet/SubmitAnswerServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KanjiDTO;
import model.TestAnswerRecord;

@WebServlet("/submitAnswer")
public class SubmitAnswerServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 protected void doPost(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
     
     // 인코딩 설정 추가
     request.setCharacterEncoding("UTF-8");
     response.setContentType("application/json; charset=UTF-8");
     response.setCharacterEncoding("UTF-8");
     
     HttpSession session = request.getSession();
     
     String selectedAnswer = request.getParameter("selected");
     String correctAnswer = request.getParameter("correct");
     String skippedParam = request.getParameter("skipped");  // 건너뛰기 여부
     String indexStr = request.getParameter("index");
     
     if (correctAnswer == null) {
         response.sendRedirect("test");
         return;
     }
     
     int currentIndex = Integer.parseInt(indexStr);
     boolean isSkipped = "true".equals(skippedParam);
     
     // 정답 여부 확인
     boolean isCorrect = !isSkipped && selectedAnswer != null && selectedAnswer.equals(correctAnswer);
     
     // 현재 한자 정보 가져오기
     @SuppressWarnings("unchecked")
     List<KanjiDTO> testKanji = (List<KanjiDTO>) session.getAttribute("testKanji");
     KanjiDTO currentKanji = testKanji.get(currentIndex);
     
     // 답안 기록 생성 (건너뛰기면 userAnswer는 null)
     String userAnswer = isSkipped ? null : selectedAnswer;
     TestAnswerRecord record = new TestAnswerRecord(currentKanji, correctAnswer, userAnswer, isCorrect, isSkipped);
     
     // 답안 기록 저장
     @SuppressWarnings("unchecked")
     List<TestAnswerRecord> answerRecords = (List<TestAnswerRecord>) session.getAttribute("testAnswerRecords");
     if (answerRecords != null) {
         answerRecords.add(record);
         session.setAttribute("testAnswerRecords", answerRecords);
         
         if (isCorrect) {
             Integer correctCount = (Integer) session.getAttribute("testCorrectCount");
             if (correctCount == null) correctCount = 0;
             session.setAttribute("testCorrectCount", correctCount + 1);
         }
     }
     
     // JSON 응답
     response.getWriter().write("{\"correct\": " + isCorrect + "}");
 }
}