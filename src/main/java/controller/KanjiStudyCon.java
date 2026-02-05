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

import model.dao.KanjiDAO;
import model.dto.AccountDTO;
import model.dto.KanjiDTO;

/**
 * KanjiStudyCon.java - 한자 학습 페이지 서블릿
 *
 * [요청 URL] /kanjiStudy?level=N5&sector=1&index=0
 * [처리] 해당 레벨/섹터의 한자를 조회하여 JSP로 전달
 */
@WebServlet("/kanjiStudy")
public class KanjiStudyCon extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int KANJI_PER_SECTOR = 20;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 체크
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 파라미터 받기
        String level = request.getParameter("level");
        String sectorStr = request.getParameter("sector");
        String indexStr = request.getParameter("index");

        if (level == null || sectorStr == null) {
            response.sendRedirect("main");
            return;
        }

        int sector;
        int currentIndex = 0;
        try {
            sector = Integer.parseInt(sectorStr);
            if (indexStr != null) {
                currentIndex = Integer.parseInt(indexStr);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("main");
            return;
        }

        // 해당 레벨의 전체 한자 조회 (인메모리 캐시)
        List<KanjiDTO> allKanji = KanjiDAO.findByLevel(level);

        // 섹터에 해당하는 한자만 추출 (20개씩)
        int startIdx = (sector - 1) * KANJI_PER_SECTOR;
        int endIdx = Math.min(startIdx + KANJI_PER_SECTOR, allKanji.size());

        List<KanjiDTO> sectorKanji = new ArrayList<>();
        for (int i = startIdx; i < endIdx; i++) {
            sectorKanji.add(allKanji.get(i));
        }
        int totalInSector = sectorKanji.size();

        // 범위 체크
        if (currentIndex >= totalInSector) currentIndex = totalInSector - 1;
        if (currentIndex < 0) currentIndex = 0;

        KanjiDTO currentKanji = null;
        if (totalInSector > 0) {
            currentKanji = sectorKanji.get(currentIndex);
        }

        // request에 데이터 저장
        request.setAttribute("level", level);
        request.setAttribute("sector", sector);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("totalInSector", totalInSector);
        request.setAttribute("currentKanji", currentKanji);

        // JSP로 forward
        request.getRequestDispatcher("/kanjiStudy.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
