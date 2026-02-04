package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.KanjiRepository;
import model.KanjiDTO;
import model.StudyProgressDTO;
import util.StudyManager;

@WebServlet("/groupSelect")
public class GroupSelectCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String level = request.getParameter("level");

		// 파라미터 검증
		if (level == null || level.isEmpty()) {
			response.sendRedirect("main.jsp");
			return;
		}

		HttpSession session = request.getSession();

		// 해당 레벨의 전체 한자 가져오기
		int totalGroups = StudyManager.getTotalGroup(level);

		// 학습 진행 상태 가져오기 (없으면 생성)
		StudyProgressDTO progress = (StudyProgressDTO) session.getAttribute("studyProgress_" + level);
		if (progress == null) {
			progress = new StudyProgressDTO(level);
			session.setAttribute("studyProgress_" + level, progress);
		}

		// JSP에 데이터 전달
		request.setAttribute("level", level);
		request.setAttribute("totalGroups", totalGroups);
		request.setAttribute("progress", progress);

		// JSP로 포워드
		request.getRequestDispatcher("/WEB-INF/views/groupSelect.jsp").forward(request, response);
	}
}
