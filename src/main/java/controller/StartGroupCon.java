package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.KanjiRepository;
import model.KanjiDTO;

@WebServlet("/startGroup")
public class StartGroupCon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String level = request.getParameter("level");
        String groupStr = request.getParameter("group");

        // 1) 파라미터 검증
        if (level == null || level.isEmpty() || groupStr == null || groupStr.isEmpty()) {
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

        // 2) 그룹 존재  검증 
        List<KanjiDTO> groupKanji = KanjiRepository.findByGroup(level, groupNumber);
        if (groupKanji == null || groupKanji.isEmpty()) {
            response.sendRedirect("groupSelect?level=" + level);
            return;
        }

        // 3) 학습 페이지로 이동 (index=0부터 시작)
        response.sendRedirect("study?level=" + level + "&group=" + groupNumber + "&index=0");
    }
}
