package ru.job4j.controller;

import ru.job4j.model.Post;
import ru.job4j.model.TestPsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", TestPsqlStore.instOf().findAllPosts());
        //req.setAttribute("posts", PsqlStore.instOf().findAllPosts());
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        if (id.equals("-1")) {
            TestPsqlStore.instOf().createPost(new Post(0, req.getParameter("vacancy")));
        } else {
            TestPsqlStore.instOf().update(new Post(Integer.valueOf(id), req.getParameter("vacancy")));
        }
        //PsqlStore.instOf().savePost(new Post(Integer.valueOf(req.getParameter("id")), req.getParameter("vacancy")));
        resp.sendRedirect(req.getContextPath() + "/post/save.do");
    }
}
