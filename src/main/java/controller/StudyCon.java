package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import data.KanjiRepository;
import model.KanjiDTO;
import model.StudyProgressDTO;
import util.StudyManager;

@WebServlet("/study")
public class StudyCon extends HttpServlet {
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

        // 1) 파라미터 받기
        String level = request.getParameter("level");
        String groupParam = request.getParameter("group");
        String indexParam = request.getParameter("index"); // optional (없으면 0)

        // 2) 파라미터 검증
        if (level == null || level.isEmpty()) {
            response.sendRedirect("main.jsp");
            return;
        }

        int group;
        try {
            group = Integer.parseInt(groupParam);
        } catch (Exception e) {
            // group이 없거나 숫자가 아니면 그룹 선택으로
            response.sendRedirect("groupSelect?level=" + level);
            return;
        }

        int currentIndex = 0;
        try {
            if (indexParam != null && !indexParam.isEmpty()) {
                currentIndex = Integer.parseInt(indexParam);
            }
        } catch (Exception ignored) {
            currentIndex = 0;
        }

        // 3) 그룹 한자 목록 가져오기 (✅ 캐싱 제거: 세션에 저장하지 않음)
        List<KanjiDTO> groupKanji = KanjiRepository.findByGroup(level, group);
        if (groupKanji == null || groupKanji.isEmpty()) {
            // 존재하지 않는 그룹이면 선택 화면으로
            response.sendRedirect("groupSelect?level=" + level);
            return;
        }

        // 4) 인덱스 범위 보정
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex >= groupKanji.size()) currentIndex = groupKanji.size() - 1;

        KanjiDTO currentKanji = groupKanji.get(currentIndex);

        // 5) totalGroups (진행률/네비게이션용)
        int totalGroups = StudyManager.getTotalGroup(level); // 내부에서 KanjiRepository.getMaxGroup(level) 호출한다고 가정

        // 6) 세션 진행상태(유저 상태)만 유지
        HttpSession session = request.getSession();
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        if (progress == null) {
            progress = new StudyProgressDTO(level);
            session.setAttribute("studyProgress_" + level, progress);
        }

        // (선택) 마지막으로 본 위치 저장
        session.setAttribute("lastGroup_" + level, group);
        session.setAttribute("lastIndex_" + level, currentIndex);

        // 7) 다음/이전 판단 값
        boolean isLastKanjiInGroup = (currentIndex == groupKanji.size() - 1);
        boolean isLastGroup = (group >= totalGroups);

        // 8) JSP로 넘길 데이터 세팅
        request.setAttribute("level", level);
        request.setAttribute("group", group);
        request.setAttribute("totalGroups", totalGroups);

        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("totalCount", groupKanji.size());

        request.setAttribute("currentKanji", currentKanji);
        request.setAttribute("progress", progress);

        request.setAttribute("isLastKanjiInGroup", isLastKanjiInGroup);
        request.setAttribute("isLastGroup", isLastGroup);

        // (선택) JSP에서 “리스트 전체”가 필요하면 내려줄 수도 있음(브릿지/리스트용)
        // request.setAttribute("groupKanji", groupKanji);

        // 9) forward
        request.getRequestDispatcher("/WEB-INF/views/study.jsp").forward(request, response);
    }
}
