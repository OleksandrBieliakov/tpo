package Controllers;

import DTOs.Requests.LoginReq;
import DTOs.Responses.LoginRes;
import Logic.Logic;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final String PARAMETER_USER_NAME = "userName";
    private static final String PARAMETER_USER_PASSWORD = "userPassword";

    private static final String MODEL = "login-model";
    private static final String VIEW = "/login-view";

    private final Logic _logic = new Logic();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter(PARAMETER_USER_NAME);
        String userPassword = request.getParameter(PARAMETER_USER_PASSWORD);

        LoginRes responseModel = _logic.login(new LoginReq(userName, userPassword));

        request.setAttribute(MODEL, responseModel);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(VIEW);
        dispatcher.forward(request, response);
    }
}
