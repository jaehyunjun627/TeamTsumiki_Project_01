package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dto.StudyProgressDTO;

@WebServlet("/finishGroup")
public class FinishGroupCon extends HttpServlet {
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
        String groupStr = request.getParameter("group");

        // 파라미터 검증
        if (level == null || groupStr == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int groupNumber;
        try {
            groupNumber = Integer.parseInt(groupStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        HttpSession session = request.getSession();

        // 학습 진행 상태 업데이트
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        if (progress != null) {
            progress.addCompletedGroup(groupNumber);
            session.setAttribute("studyProgress_" + level, progress);
        }

        // 그룹 한자 세션 제거
        session.removeAttribute("groupKanji_" + level);
        session.removeAttribute("currentGroup_" + level);

        // 그룹 선택 화면으로 이동
        response.sendRedirect("groupSelect?level=" + level);
    }
}
