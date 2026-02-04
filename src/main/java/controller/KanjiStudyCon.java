package controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * KanjiStudyCon.java - 한자 학습 서블릿
 *
 * [요청 URL] /kanjiStudy (GET)
 * [파라미터] level, sector, index(옵션)
 *
 * [처리 흐름]
 * 1. 로그인 체크
 * 2. 해당 레벨/섹터의 한자 목록 조회
 * 3. 현재 인덱스의 한자 데이터 설정
 * 4. kanjiStudy.jsp로 포워드
 */
@WebServlet("/kanjiStudy")
public class KanjiStudyCon extends HttpServlet {
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
        String sectorParam = request.getParameter("sector");

        if (level == null || sectorParam == null) {
            response.sendRedirect("main.do");
            return;
        }

        int sector = Integer.parseInt(sectorParam);

        // ========== 3. 메모리에서 한자 가져오기 ==========
        List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);

        // ========== 4. 섹터에 해당하는 한자만 추출 (20개씩) ==========
        int startIdx = (sector - 1) * KANJI_PER_SECTOR;
        int endIdx = Math.min(startIdx + KANJI_PER_SECTOR, allKanji.size());

        List<KanjiDTO> sectorKanji = new ArrayList<>();
        for (int i = startIdx; i < endIdx; i++) {
            sectorKanji.add(allKanji.get(i));
        }
        int totalInSector = sectorKanji.size();

        // ========== 5. 현재 인덱스 ==========
        int currentIndex = 0;
        String indexParam = request.getParameter("index");
        if (indexParam != null) {
            currentIndex = Integer.parseInt(indexParam);
        }

        // 범위 체크
        if (currentIndex >= totalInSector) currentIndex = totalInSector - 1;
        if (currentIndex < 0) currentIndex = 0;

        KanjiDTO currentKanji = null;
        if (totalInSector > 0) {
            currentKanji = sectorKanji.get(currentIndex);
        }

        // ========== 6. 요청 속성 설정 ==========
        request.setAttribute("level", level);
        request.setAttribute("sector", sector);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("totalInSector", totalInSector);
        request.setAttribute("currentKanji", currentKanji);

        // ========== 7. JSP로 포워드 ==========
        request.getRequestDispatcher("/WEB-INF/views/kanjiStudy.jsp").forward(request, response);
    }
}
