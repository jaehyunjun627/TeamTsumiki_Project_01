package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.KanjiRepository_KANJIDAO;
import model.KanjiDTO;

@WebServlet("/review.do")
public class ReviewCon extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ 파라미터 받기
        String level = request.getParameter("level"); // N5
        String pageParam = request.getParameter("page"); // 1

        if (level == null) level = "N5";
        int page = (pageParam == null) ? 1 : Integer.parseInt(pageParam);

        // 2️⃣ 레벨 전체 한자 가져오기
        List<KanjiDTO> allList = KanjiRepository_KANJIDAO.findByLevel(level);

        // 3️⃣ 페이지당 4개
        int pageSize = 4;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allList.size());

        List<KanjiDTO> pageList = allList.subList(start, end);

        // 4️⃣ JSP로 전달
        request.setAttribute("pageList", pageList);
        request.setAttribute("page", page);

        // 5️⃣ forward
        request.getRequestDispatcher("/WEB-INF/review.jsp")
        .forward(request, response);
    }
}