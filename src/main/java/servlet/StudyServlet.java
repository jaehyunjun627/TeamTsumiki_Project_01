// src/main/java/servlet/StudyServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KanjiDTO;

@WebServlet("/study")
public class StudyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String level = request.getParameter("level");
        String groupStr = request.getParameter("group");
        
        if (level == null || groupStr == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        int groupNumber = Integer.parseInt(groupStr);
        
        // 세션에서 그룹 한자 리스트 가져오기
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<KanjiDTO> kanjiList = (List<KanjiDTO>) session.getAttribute("groupKanji_" + level);
        
        if (kanjiList == null || kanjiList.isEmpty()) {
            response.sendRedirect("groupSelect?level=" + level);
            return;
        }
        
        // 현재 인덱스 가져오기
        String indexParam = request.getParameter("index");
        int currentIndex = 0;
        
        if (indexParam != null) {
            try {
                currentIndex = Integer.parseInt(indexParam);
                if (currentIndex < 0) currentIndex = 0;
                if (currentIndex >= kanjiList.size()) currentIndex = kanjiList.size() - 1;
            } catch (NumberFormatException e) {
                currentIndex = 0;
            }
        }
        
        // 현재 한자 가져오기
        KanjiDTO currentKanji = kanjiList.get(currentIndex);
        
        // 음독/훈독 합치기
        String onyomi = currentKanji.getOnyomiCombined();
        String kunyomi = currentKanji.getKunyomiCombined();
        
        // 마지막 한자 여부 확인
        boolean isLastKanji = (currentIndex == kanjiList.size() - 1);
        
        // 예시 단어를 줄바꿈으로 분리
        String[] exampleWords = currentKanji.getExample_word() != null ? 
                                currentKanji.getExample_word().split("、") : new String[0];
        
        // JSP로 전달할 데이터 설정
        request.setAttribute("level", level);
        request.setAttribute("groupNumber", groupNumber);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("totalCount", kanjiList.size());
        request.setAttribute("currentKanji", currentKanji);
        request.setAttribute("onyomi", onyomi);
        request.setAttribute("kunyomi", kunyomi);
        request.setAttribute("isLastKanji", isLastKanji);
        request.setAttribute("exampleWords", exampleWords);
        
        // JSP로 포워딩
        request.getRequestDispatcher("/jsp/study.jsp").forward(request, response);
    }
}