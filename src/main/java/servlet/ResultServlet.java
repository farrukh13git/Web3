package servlet;

import util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", new HashMap<>()));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
    }
}
