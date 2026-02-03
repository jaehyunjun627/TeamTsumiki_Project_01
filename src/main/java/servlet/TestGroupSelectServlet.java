// src/main/java/servlet/TestGroupSelectServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.KanjiDTO;
import repository.KanjiRepository;
import util.StudyManager;

@WebServlet("/testGroupSelect")
public class TestGroupSelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        
        if (level == null || level.isEmpty()) {
            response.sendRedirect("test");
            return;
        }
        
        // 해당 레벨의 한자 리스트 가져오기
        List<KanjiDTO> kanjiList = KanjiRepository.findByLevel(level);
        int totalGroups = StudyManager.getTotalGroups(kanjiList);
        
        // JSP로 전달할 데이터 설정
        request.setAttribute("level", level);
        request.setAttribute("totalGroups", totalGroups);
        
        // JSP로 포워딩
        request.getRequestDispatcher("/jsp/testGroupSelect.jsp").forward(request, response);
    }
}