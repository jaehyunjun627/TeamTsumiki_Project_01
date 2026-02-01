package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AccountDAO;
import model.AccountDTO;

@WebServlet("/RegisterCon.do")
public class RegisterCon extends HttpServlet {
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

        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String userPw = request.getParameter("userPw");
        String userPw2 = request.getParameter("userPw2");

        if (nickname == null || nickname.isEmpty() ||
            email == null || email.isEmpty() ||
            userPw == null || userPw.isEmpty() ||
            userPw2 == null || userPw2.isEmpty()) {
            response.sendRedirect("register.jsp?error=empty");
            return;
        }

        AccountDAO dao = new AccountDAO();

        if (dao.idCheck(email)) {
            response.sendRedirect("register.jsp?error=id");
            return;
        }

        if (dao.nicknameCheck(nickname)) {
            response.sendRedirect("register.jsp?error=nickname");
            return;
        }

        if (!userPw.equals(userPw2)) {
            response.sendRedirect("register.jsp?error=pw");
            return;
        }

        AccountDTO dto = new AccountDTO();
        dto.setUserID(email);
        dto.setUserPW1(userPw);
        dto.setUserPW2(userPw2);
        dto.setNickname(nickname);
        dto.setEmail(email);

        dao.insertMember(dto);

        response.sendRedirect("login.jsp?msg=success");
    }
}