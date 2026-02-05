package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccountDAO;
import model.dto.AccountDTO;

/**
 * LoginCon.java - 로그인 처리 서블릿
 * 
 * [요청 URL] LoginCon.do (POST)
 * [파라미터] userId, userPw
 * 
 * [처리 흐름]
 * 1. 빈 입력 체크 → 실패 시 login.jsp?error=empty
 * 2. 관리자 계정 체크 (DB 연결 없이 로그인 가능)
 * 3. 일반 회원 DB 체크
 *    - 성공 → 세션에 회원정보 저장 → main.jsp 이동
 *    - 비밀번호 틀림 → login.jsp?error=pw
 *    - 아이디 없음 → login.jsp?error=id
 */
@WebServlet("/LoginCon.do")
public class LoginCon extends HttpServlet {
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

        String userID = request.getParameter("userId");
        String userPW = request.getParameter("userPw");

        // ========== 1. 빈 입력 체크 ==========
        if (userID == null || userID.isEmpty() || userPW == null || userPW.isEmpty()) {
            response.sendRedirect("login.jsp?error=empty");
            return;
        }

        // ========== 2. 관리자 계정 체크 (DB 연결 없이) ==========
        // 관리자 아이디: admin@admin.com / 비밀번호: 123
        if ("admin@admin.com".equals(userID) && "123".equals(userPW)) {
            AccountDTO dto = new AccountDTO();
            dto.setUserID("admin@admin.com");
            dto.setNickname("관리자");
            dto.setEmail("admin@admin.com");

            HttpSession session = request.getSession();
            session.setAttribute("loginUser", dto);
            response.sendRedirect("main");
            return;
        }

        // ========== 3. 일반 회원 DB 체크 ==========
        AccountDAO dao = new AccountDAO();
        int result = dao.loginCheck(userID, userPW);

        if (result == 1) {
            // 로그인 성공 → 세션에 회원정보 저장
            AccountDTO dto = dao.getMember(userID);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", dto);
            response.sendRedirect(request.getContextPath() + "/main.do");
        } else if (result == 0) {
            // 비밀번호 틀림
            response.sendRedirect("login.jsp?error=pw");
        } else {
            // 아이디 없음
            response.sendRedirect("login.jsp?error=id");
        }
    }
}