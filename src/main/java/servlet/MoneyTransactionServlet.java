package servlet;

import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransactionServlet extends HttpServlet {

    private BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.getInstance().getPage("moneyTransactionPage.html", new HashMap<>()));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderName = request.getParameter("senderName");
        String senderPass = request.getParameter("senderPass");
        String count = request.getParameter("count");
        String nameTo = request.getParameter("nameTo");
        Map<String, Object> pageVariables = new HashMap<>();
        Long money = Long.parseLong(count);
        BankClient client = bankClientService.getClientByName(senderName);
        String message;

        if (bankClientService.validateClient(senderName, senderPass) &&
                bankClientService.sendMoneyToClient(client, nameTo, money)) {
            message = "The transaction was successful";
        } else {
            message = "transaction rejected";
        }
        pageVariables.put("message", message);
        response.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
        response.setStatus(200);
    }
}
