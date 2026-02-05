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
import model.dto.AccountDTO;
import model.dto.KanjiDTO;

/**
 * SectorSelectCon.java - 섹터 선택 페이지 서블릿
 *
 * [요청 URL] /sectorSelect?level=N5
 * [처리] 해당 레벨의 한자 개수를 조회하여 JSP로 전달
 */
@WebServlet("/sectorSelect")
public class SectorSelectCon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 체크
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 레벨 파라미터
        String level = request.getParameter("level");
        if (level == null) {
            response.sendRedirect("main");
            return;
        }

        // 해당 레벨의 전체 한자 개수 조회 (인메모리 캐시)
        List<KanjiDTO> allKanji = KanjiDAO.findByLevel(level);
        int totalKanji = (allKanji != null) ? allKanji.size() : 0;

        // request에 데이터 저장
        request.setAttribute("totalKanji", totalKanji);

        // JSP로 forward
        request.getRequestDispatcher("/sectorSelect.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
