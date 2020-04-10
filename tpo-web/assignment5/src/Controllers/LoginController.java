package Controllers;

import DAL.IRepository;
import DAL.PostgresRepository;
import DTOs.Requests.LoginReq;
import DTOs.Responses.LoginRes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final String PARAMETER_USER_NAME = "userName";
    private static final String PARAMETER_USER_PASSWORD = "userPassword";

    private static final String MODEL = "login-model";
    private static final String VIEW = "/login-view";

    @Resource(name = "jdbc/myPostgresDB")
    private DataSource dataSource;

    private IRepository repository;

    @PostConstruct
    public void init() {
        repository = new PostgresRepository(dataSource);
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter(PARAMETER_USER_NAME);
        String userPassword = request.getParameter(PARAMETER_USER_PASSWORD);

        LoginRes responseModel = repository.login(new LoginReq(userName, userPassword));

        request.setAttribute(MODEL, responseModel);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(VIEW);
        dispatcher.forward(request, response);
    }
}
