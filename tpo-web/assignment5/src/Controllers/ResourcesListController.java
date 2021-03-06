package Controllers;

import DAL.IRepository;
import DAL.PostgresRepository;
import DTOs.Requests.ListResourcesReq;
import DTOs.Responses.ListResourcesRes;
import Exceptions.WebException;
import Models.ResourceModel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/resources")
public class ResourcesListController extends HttpServlet {

    private static final String LOGIN_VIEW = "/index.html";
    private static final String DETAILS_VIEW = "/resources/details/";

    private static final String USER_ID = "userId";
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_LAST_NAME = "userLastName";

    private static final String START =
            "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1250\">\n" +
                    "    <title>Resources</title>\n" +
                    "</head>\n" +
                    "<body>\n";
    private static final String END = "\n</body>\n</html>";

    private static final String LOGOUT_BUTTON =
            "    <form method=\"get\" action=\"/logout\">\n" +
                    "    <button type=\"submit\">LOG OUT</button>\n" +
                    "    </form><br>\n";

    @Resource(name = "jdbc/myPostgresDB")
    private DataSource dataSource;

    private IRepository repository;

    @PostConstruct
    public void init() {
        repository = new PostgresRepository(dataSource);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idUser = null;
        String firstName = null;
        String lastName = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case USER_ID:
                    idUser = Integer.parseInt(cookie.getValue());
                    break;
                case USER_FIRST_NAME:
                    firstName = cookie.getValue();
                    break;
                case USER_LAST_NAME:
                    lastName = cookie.getValue();
                    break;
            }
        }

        if (idUser == null) {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher(LOGIN_VIEW);
            dispatcher.forward(request, response);
            return;
        }

        ListResourcesRes listResourcesResponse;
        try {
            listResourcesResponse = repository.listResources(new ListResourcesReq(idUser));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new WebException("Error occurred during getting the list of user's resources.", exception);
        }

        PrintWriter out = response.getWriter();
        out.println(START);
        out.println(LOGOUT_BUTTON);
        out.println("User ID " + idUser + " : " + firstName + " " + lastName + "<hr>");

        for (ResourceModel resource : listResourcesResponse.getResources()) {
            out.println("<a href='" + DETAILS_VIEW + resource.getIdResource() + "'>" + resource.getResourceName() + "</a><br>");
        }

        out.println(END);
        out.close();
    }

}
