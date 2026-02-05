package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dto.StudyProgressDTO;

@WebServlet("/resetProgress")
public class ResetProgressCon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String level = request.getParameter("level");

        // 파라미터 검증
        if (level == null) {
            response.sendRedirect("main.jsp");
            return;
        }

        HttpSession session = request.getSession();

        // 해당 레벨의 학습 진행 상태 초기화
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        if (progress != null) {
            progress.resetProgress();
            session.setAttribute("studyProgress_" + level, progress);
        }

        // 그룹 선택 페이지로 이동
        response.sendRedirect("groupSelect?level=" + level);
    }
}
