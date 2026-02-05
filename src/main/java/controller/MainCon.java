package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LogDAO;
import model.dto.AccountDTO;

/**
 * MainCon.java - 메인 페이지 서블릿
 *
 * [요청 URL] /main
 * [처리] 로그인 체크 + 출석 데이터 조회 후 JSP로 전달
 */
@WebServlet("/main")
public class MainCon extends HttpServlet {
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

        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("loginUser");

        // 로그인 체크
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ========== 오늘 날짜 정보 ==========
        Calendar cal = Calendar.getInstance();
        int todayYear = cal.get(Calendar.YEAR);
        int todayMonth = cal.get(Calendar.MONTH) + 1;
        int todayDay = cal.get(Calendar.DAY_OF_MONTH);

        // ========== 이번 달 정보 ==========
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // ========== 가입일 파싱 ==========
        int regDay = 0;
        String regDateStr = user.getRegDate();
        if (regDateStr != null && !regDateStr.isEmpty()) {
            String[] parts = regDateStr.split(" ")[0].split("-");
            if (parts.length >= 3) {
                int regYear = Integer.parseInt(parts[0]);
                int regMonth = Integer.parseInt(parts[1]);
                regDay = Integer.parseInt(parts[2]);
                if (regYear != todayYear || regMonth != todayMonth) {
                    regDay = 0;
                }
            }
        }

        // ========== 출석 날짜 가져오기 ==========
        LogDAO logDAO = new LogDAO();
        List<Integer> attendedDays = logDAO.getMonthAttendance(user.getUserID(), todayYear, todayMonth);

        // request에 데이터 저장
        request.setAttribute("todayYear", todayYear);
        request.setAttribute("todayMonth", todayMonth);
        request.setAttribute("todayDay", todayDay);
        request.setAttribute("firstDayOfWeek", firstDayOfWeek);
        request.setAttribute("lastDay", lastDay);
        request.setAttribute("regDay", regDay);
        request.setAttribute("attendedDays", attendedDays);

        // JSP로 포워드
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }
}
