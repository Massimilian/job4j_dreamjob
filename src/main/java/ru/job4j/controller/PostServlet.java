package ru.job4j.controller;

import ru.job4j.model.Post;
import ru.job4j.model.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", MemStore.instOf().findAllPosts());
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        MemStore.instOf().savePost(new Post(Integer.valueOf(req.getParameter("id")), req.getParameter("vacancy")));
        resp.sendRedirect(req.getContextPath() + "/post/save.do");
    }
}
