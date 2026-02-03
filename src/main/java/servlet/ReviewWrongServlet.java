// src/main/java/servlet/ReviewWrongServlet.java
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TestAnswerRecord;

@WebServlet("/reviewWrong")
public class ReviewWrongServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
     
     // 인코딩 설정 추가
     request.setCharacterEncoding("UTF-8");
     response.setContentType("text/html; charset=UTF-8");
     response.setCharacterEncoding("UTF-8");
     
     HttpSession session = request.getSession();
     
     // 모든 답안 기록 가져오기
     @SuppressWarnings("unchecked")
     List<TestAnswerRecord> allRecords = (List<TestAnswerRecord>) session.getAttribute("testAnswerRecords");
     
     if (allRecords == null || allRecords.isEmpty()) {
         response.sendRedirect("test");
         return;
     }
     
     // 틀린 문제만 필터링
     List<TestAnswerRecord> wrongRecords = new ArrayList<>();
     for (TestAnswerRecord record : allRecords) {
         if (!record.isCorrect()) {
             wrongRecords.add(record);
         }
     }
     
     // 틀린 문제가 없으면 결과 화면으로
     if (wrongRecords.isEmpty()) {
         response.sendRedirect("testResult");
         return;
     }
     
     // 현재 검토 중인 인덱스
     String indexParam = request.getParameter("index");
     int currentIndex = 0;
     
     if (indexParam != null) {
         try {
             currentIndex = Integer.parseInt(indexParam);
             if (currentIndex < 0) currentIndex = 0;
             if (currentIndex >= wrongRecords.size()) currentIndex = wrongRecords.size() - 1;
         } catch (NumberFormatException e) {
             currentIndex = 0;
         }
     }
     
     // 현재 검토할 답안
     TestAnswerRecord currentRecord = wrongRecords.get(currentIndex);
     
     // JSP로 데이터 전달
     request.setAttribute("currentRecord", currentRecord);
     request.setAttribute("currentIndex", currentIndex);
     request.setAttribute("totalWrong", wrongRecords.size());
     request.setAttribute("level", session.getAttribute("testLevel"));
     
     request.getRequestDispatcher("/jsp/reviewWrong.jsp").forward(request, response);
 }
}