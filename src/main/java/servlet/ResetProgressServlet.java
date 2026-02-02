// src/main/java/servlet/ResetProgressServlet.java
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.StudyProgressDTO;

@WebServlet("/resetProgress")
public class ResetProgressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        
        if (level == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // 해당 레벨의 학습 진행 상태 초기화
        HttpSession session = request.getSession();
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        
        if (progress != null) {
            progress.resetProgress();
            session.setAttribute("studyProgress_" + level, progress);
        }
        
        // 그룹 선택 페이지로 리다이렉트
        response.sendRedirect("groupSelect?level=" + level);
    }
}