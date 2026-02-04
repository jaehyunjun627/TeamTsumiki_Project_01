package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccountDTO;
import model.KanjiDTO;
import data.KanjiRepository;

/**
 * SectorSelectCon.java - 섹터 선택 서블릿
 *
 * [요청 URL] /sectorSelect (GET)
 * [파라미터] level (N5, N4, N3, N2, N1)
 *
 * [처리 흐름]
 * 1. 로그인 체크
 * 2. 레벨에 해당하는 한자 개수 조회
 * 3. 총 섹터 수 계산 (20개씩)
 * 4. sectorSelect.jsp로 포워드
 */
@WebServlet("/sectorSelect")
public class SectorSelectCon extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int KANJI_PER_SECTOR = 20;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ========== 1. 로그인 체크 ==========
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ========== 2. 파라미터 받기 ==========
        String level = request.getParameter("level");
        if (level == null || level.isEmpty()) {
            response.sendRedirect("main.jsp");
            return;
        }

        // ========== 3. 해당 레벨의 한자 개수 조회 ==========
        List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
        int totalKanji = (allKanji != null) ? allKanji.size() : 0;

        // ========== 4. 총 섹터 수 계산 ==========
        int totalSectors = (int) Math.ceil((double) totalKanji / KANJI_PER_SECTOR);

        // ========== 5. 요청 속성 설정 ==========
        request.setAttribute("level", level);
        request.setAttribute("totalKanji", totalKanji);
        request.setAttribute("totalSectors", totalSectors);
        request.setAttribute("kanjiPerSector", KANJI_PER_SECTOR);

        // ========== 6. JSP로 포워드 ==========
        request.getRequestDispatcher("/WEB-INF/views/sectorSelect.jsp").forward(request, response);
    }
}
