package servlet;

import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String money = request.getParameter("money");
        Map<String, Object> pageVariables = new HashMap<>();
        Long moneyL = Long.parseLong(money);

        try {
            String message;
            if (new BankClientService().addClient(new BankClient(name, password, moneyL))) {
                message = "Add client successful";
            } else {
                message = "Client not add";
            }
            pageVariables.put("message", message);
            response.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            response.setStatus(200);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}

