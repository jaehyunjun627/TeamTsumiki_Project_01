// src/main/java/servlet/TestResultServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/testResult")
public class TestResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String level = (String) session.getAttribute("testLevel");
        Integer group = (Integer) session.getAttribute("testGroup");
        Integer totalCount = (Integer) session.getAttribute("currentTestIndex");
        Integer correctCount = (Integer) session.getAttribute("testCorrectCount");
        
        @SuppressWarnings("unchecked")
        List<Boolean> testResults = (List<Boolean>) session.getAttribute("testResults");
        
        if (level == null || totalCount == null || correctCount == null) {
            response.sendRedirect("test");
            return;
        }
        
        // totalCount는 마지막 인덱스이므로 +1 해야 전체 문제 수
        int totalQuestions = totalCount + 1;
        
        // JSP로 데이터 전달
        request.setAttribute("level", level);
        request.setAttribute("group", group);
        request.setAttribute("totalCount", totalQuestions);
        request.setAttribute("correctCount", correctCount);
        request.setAttribute("wrongCount", totalQuestions - correctCount);
        request.setAttribute("testResults", testResults);
        
        request.getRequestDispatcher("/jsp/testResult.jsp").forward(request, response);
    }
}