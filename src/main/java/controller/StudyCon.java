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
import model.dto.KanjiDTO;
import util.StudyManager;

@WebServlet("/study")
public class StudyCon extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    @SuppressWarnings("unchecked")
    protected void reqPro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String level = request.getParameter("level");
        String groupStr = request.getParameter("group");
        String indexStr = request.getParameter("index");

        // 파라미터 검증
        if (level == null || groupStr == null || indexStr == null) {
            response.sendRedirect("main.jsp");
            return;
        }

        int groupNumber;
        int currentIndex;
        try {
            groupNumber = Integer.parseInt(groupStr);
            currentIndex = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("main.jsp");
            return;
        }

        HttpSession session = request.getSession();

        // 세션에서 그룹 한자 가져오기
        List<KanjiDTO> groupKanji = (List<KanjiDTO>) session.getAttribute("groupKanji_" + level);

        // 세션에 없으면 새로 가져오기
        if (groupKanji == null || groupKanji.isEmpty()) {
            KanjiDAO kanjiDAO = new KanjiDAO();
            List<KanjiDTO> allKanji = kanjiDAO.findByLevel(level);
            groupKanji = StudyManager.getKanjiByGroup(allKanji, groupNumber);

            if (groupKanji.isEmpty()) {
                response.sendRedirect("groupSelect?level=" + level);
                return;
            }

            session.setAttribute("groupKanji_" + level, groupKanji);
            session.setAttribute("currentGroup_" + level, groupNumber);
        }

        // 인덱스 유효성 검사
        if (currentIndex < 0 || currentIndex >= groupKanji.size()) {
            currentIndex = 0;
        }

        // 현재 한자 가져오기
        KanjiDTO currentKanji = groupKanji.get(currentIndex);

        // 음독 조합
        String onyomi = combineReadings(
                currentKanji.getUnyomi_1(),
                currentKanji.getUnyomi_2(),
                currentKanji.getUnyomi_3(),
                currentKanji.getUnyomi_4()
        );

        // 훈독 조합
        String kunyomi = combineReadings(
                currentKanji.getKunyomi_1(),
                currentKanji.getKunyomi_2(),
                currentKanji.getKunyomi_3(),
                currentKanji.getKunyomi_4()
        );

        // 예시 단어 배열
        List<String> exampleWords = new ArrayList<>();
        if (currentKanji.getExample_word_1() != null && !currentKanji.getExample_word_1().isEmpty()) {
            exampleWords.add(currentKanji.getExample_word_1());
        }
        if (currentKanji.getExample_word_2() != null && !currentKanji.getExample_word_2().isEmpty()) {
            exampleWords.add(currentKanji.getExample_word_2());
        }
        if (currentKanji.getExample_word_3() != null && !currentKanji.getExample_word_3().isEmpty()) {
            exampleWords.add(currentKanji.getExample_word_3());
        }

        // 마지막 한자 여부
        boolean isLastKanji = (currentIndex == groupKanji.size() - 1);

        // JSP에 데이터 전달
        request.setAttribute("level", level);
        request.setAttribute("groupNumber", groupNumber);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("totalCount", groupKanji.size());
        request.setAttribute("currentKanji", currentKanji);
        request.setAttribute("onyomi", onyomi);
        request.setAttribute("kunyomi", kunyomi);
        request.setAttribute("isLastKanji", isLastKanji);
        request.setAttribute("exampleWords", exampleWords);

        // JSP로 포워드
        request.getRequestDispatcher("/WEB-INF/views/study.jsp").forward(request, response);
    }

    // 읽기 조합 헬퍼 메서드
    private String combineReadings(String... readings) {
        StringBuilder sb = new StringBuilder();
        for (String reading : readings) {
            if (reading != null && !reading.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(reading);
            }
        }
        return sb.toString();
    }
}
