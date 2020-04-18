package Controllers;

import DAL.IRepository;
import DAL.PostgresRepository;
import DTOs.Requests.LoginReq;
import DTOs.Responses.LoginRes;
import Exceptions.WebException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final String PARAMETER_USER_NAME = "userName";
    private static final String PARAMETER_USER_PASSWORD = "userPassword";

    private static final String VIEW_LOGIN = "/index.html";
    private static final String VIEW_LOGIN_FAIL = "/login-failed.html";
    private static final String VIEW_RESOURCES_LIST = "/resources";

    private static final String USER_ID = "userId";
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_LAST_NAME = "userLastName";

    @Resource(name = "jdbc/myPostgresDB")
    private DataSource dataSource;

    private IRepository repository;

    @PostConstruct
    public void init() {
        repository = new PostgresRepository(dataSource);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter(PARAMETER_USER_NAME);
        String userPassword = request.getParameter(PARAMETER_USER_PASSWORD);

        if (userName == null && userPassword == null) {
            response.sendRedirect(VIEW_LOGIN);
            return;
        }

        LoginRes loginResponse;
        try {
            loginResponse = repository.login(new LoginReq(userName, userPassword));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new WebException("Error occurred during login.", exception);
        }

        if (loginResponse == null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(VIEW_LOGIN_FAIL);
            dispatcher.forward(request, response);
        } else {
            Cookie userIdCookie = new Cookie(USER_ID, Integer.toString(loginResponse.getIdUser()));
            Cookie userFirstNameCookie = new Cookie(USER_FIRST_NAME, loginResponse.getFirstName());
            Cookie userLastNameCookie = new Cookie(USER_LAST_NAME, loginResponse.getLastName());
            response.addCookie(userIdCookie);
            response.addCookie(userFirstNameCookie);
            response.addCookie(userLastNameCookie);

            response.sendRedirect(VIEW_RESOURCES_LIST);
        }
    }
}
