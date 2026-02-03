package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDTO;
import model.AttendanceDAO;

@WebServlet("/main2")
public class Main2Con extends HttpServlet {
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
        int todayMonth = cal.get(Calendar.MONTH) + 1; // 0부터 시작하므로 +1
        int todayDay = cal.get(Calendar.DAY_OF_MONTH);

        // ========== 이번 달 정보 ==========
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=일, 2=월, ...
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 이번 달 마지막 날

        // ========== 가입일 파싱 ==========
        int regDay = 1; // 기본값
        String regDateStr = user.getRegDate();
        if (regDateStr != null && !regDateStr.isEmpty()) {
            // regDate 형식: "2026-02-03" 또는 "2026-02-03 00:00:00"
            String[] parts = regDateStr.split(" ")[0].split("-");
            if (parts.length >= 3) {
                int regYear = Integer.parseInt(parts[0]);
                int regMonth = Integer.parseInt(parts[1]);
                regDay = Integer.parseInt(parts[2]);
                // 다른 달이면 가입일을 0으로 (이번 달 전체 표시)
                if (regYear != todayYear || regMonth != todayMonth) {
                    regDay = 0;
                }
            }
        }

        // ========== 출석 날짜 가져오기 ==========
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        List<Integer> attendedDays = attendanceDAO.getMonthAttendance(user.getUserID(), todayYear, todayMonth);

        // ========== 캘린더 데이터 생성 ==========
        List<List<CalendarDay>> calendarWeeks = new ArrayList<>();
        int totalWeeks = (int) Math.ceil((firstDayOfWeek - 1 + lastDay) / 7.0);
        int dayCount = 1;

        for (int week = 0; week < totalWeeks; week++) {
            List<CalendarDay> weekDays = new ArrayList<>();
            for (int dow = 1; dow <= 7; dow++) {
                CalendarDay calDay = new CalendarDay();

                // 첫 주에서 시작 요일 전이거나, 마지막 날 이후
                if ((week == 0 && dow < firstDayOfWeek) || dayCount > lastDay) {
                    calDay.setEmpty(true);
                    calDay.setDay(0);
                    calDay.setDayClass("empty");
                } else {
                    calDay.setEmpty(false);
                    calDay.setDay(dayCount);

                    // 색상 결정
                    String dayClass = "";
                    if (dayCount < regDay) {
                        // 가입일 이전 → 회색
                        dayClass = "gray";
                    } else if (dayCount < todayDay) {
                        // 과거 (어제 이전)
                        if (attendedDays.contains(dayCount)) {
                            dayClass = "green"; // 출석함
                        } else {
                            dayClass = "red";   // 출석 안 함
                        }
                    } else if (dayCount == todayDay) {
                        // 오늘
                        if (attendedDays.contains(dayCount)) {
                            dayClass = "green"; // 오늘 출석함
                        } else {
                            dayClass = "gray";  // 아직 학습 안 함
                        }
                    } else {
                        // 미래 → 회색
                        dayClass = "gray";
                    }
                    calDay.setDayClass(dayClass);
                    dayCount++;
                }
                weekDays.add(calDay);
            }
            calendarWeeks.add(weekDays);
        }

        // request에 속성 설정
        request.setAttribute("todayMonth", todayMonth);
        request.setAttribute("calendarWeeks", calendarWeeks);

        // JSP로 포워드
        request.getRequestDispatcher("/WEB-INF/views/main2.jsp").forward(request, response);
    }

    // 캘린더 날짜 정보를 담는 내부 클래스
    public static class CalendarDay {
        private int day;
        private String dayClass;
        private boolean empty;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDayClass() {
            return dayClass;
        }

        public void setDayClass(String dayClass) {
            this.dayClass = dayClass;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }
}
