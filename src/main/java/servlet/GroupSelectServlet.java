// src/main/java/servlet/GroupSelectServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.KanjiDTO;
import model.StudyProgressDTO;
import repository.KanjiRepository;
import util.StudyManager;

@WebServlet("/groupSelect")
public class GroupSelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        
        if (level == null || level.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // 해당 레벨의 한자 리스트 가져오기
        List<KanjiDTO> kanjiList = KanjiRepository.findByLevel(level);
        int totalGroups = StudyManager.getTotalGroups(kanjiList);
        
        // 세션에서 학습 진행 상태 가져오기
        HttpSession session = request.getSession();
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        
        if (progress == null) {
            progress = new StudyProgressDTO(level);
            session.setAttribute("studyProgress_" + level, progress);
        }
        
        // JSP로 전달할 데이터 설정
        request.setAttribute("level", level);
        request.setAttribute("totalGroups", totalGroups);
        request.setAttribute("progress", progress);
        
        // JSP로 포워딩
        request.getRequestDispatcher("/jsp/groupSelect.jsp").forward(request, response);
    }
}