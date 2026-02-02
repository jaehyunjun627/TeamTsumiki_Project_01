// src/main/java/servlet/StartGroupServlet.java
package servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.KanjiDTO;
import repository.KanjiRepository;
import util.StudyManager;

@WebServlet("/startGroup")
public class StartGroupServlet extends HttpServlet {
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
        
        // 전체 한자 리스트 가져오기
        List<KanjiDTO> allKanji = KanjiRepository.findByLevel(level);
        
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
        
        // 학습 페이지로 리다이렉트
        response.sendRedirect("study?level=" + level + "&group=" + groupNumber + "&index=0");
    }
}