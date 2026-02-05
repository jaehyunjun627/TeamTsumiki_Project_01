package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.KanjiDAO;
import model.dto.KanjiDTO;
import util.StudyManager;

@WebServlet("/startGroup")
public class StartGroupCon extends HttpServlet {
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
            response.sendRedirect("main.jsp");
            return;
        }

        int groupNumber;
        try {
            groupNumber = Integer.parseInt(groupStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("main.jsp");
            return;
        }

        // 전체 한자 리스트 가져오기
        List<KanjiDTO> allKanji = KanjiDAO.findByLevel(level);

        // 해당 그룹의 한자 가져오기
        List<KanjiDTO> groupKanji = StudyManager.getKanjiByGroup(allKanji, groupNumber);

        if (groupKanji.isEmpty()) {
            response.sendRedirect("groupSelect?level=" + level);
            return;
        }

        // 세션에 현재 그룹 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("currentGroup_" + level, groupNumber);
        session.setAttribute("groupKanji_" + level, groupKanji);

        // 학습 페이지로 이동
        response.sendRedirect("study?level=" + level + "&group=" + groupNumber + "&index=0");
    }
}
