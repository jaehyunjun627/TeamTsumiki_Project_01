// src/main/java/servlet/FinishGroupServlet.java
package servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KanjiDTO;
import model.StudyProgressDTO;

@WebServlet("/finishGroup")
public class FinishGroupServlet extends HttpServlet {
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
        
        HttpSession session = request.getSession();
        
        // 학습한 한자 리스트 가져오기
        @SuppressWarnings("unchecked")
        List<KanjiDTO> groupKanji = (List<KanjiDTO>) session.getAttribute("groupKanji_" + level);
        
        // 학습 진행 상태 업데이트
        StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
        
        if (progress != null) {
            // 그룹 완료 처리
            progress.addCompletedGroup(groupNumber);
            
            // 학습한 한자들 추가 (NEW!)
            if (groupKanji != null) {
                Set<String> kanjis = new HashSet<>();
                for (KanjiDTO kanji : groupKanji) {
                    kanjis.add(kanji.getKanji());
                }
                progress.addAllLearnedKanji(kanjis);
            }
            
            session.setAttribute("studyProgress_" + level, progress);
        }
        
        // 그룹 한자 세션 제거
        session.removeAttribute("groupKanji_" + level);
        session.removeAttribute("currentGroup_" + level);
        
        // 그룹 선택 화면으로 리다이렉트
        response.sendRedirect("groupSelect?level=" + level);
    }
}