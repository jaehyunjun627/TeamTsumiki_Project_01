package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccountDAO;
import model.AccountDTO;

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

        if (userID == null || userID.isEmpty() || userPW == null || userPW.isEmpty()) {
            response.sendRedirect("login.jsp?error=empty");
            return;
        }

        AccountDAO dao = new AccountDAO();
        int result = dao.loginCheck(userID, userPW);

        if (result == 1) {
            AccountDTO dto = dao.getMember(userID);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", dto);
            response.sendRedirect("main.jsp");
        } else if (result == 0) {
            response.sendRedirect("login.jsp?error=pw");
        } else {
            response.sendRedirect("login.jsp?error=id");
        }
    }
}