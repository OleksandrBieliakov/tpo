package addition;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addition")
public class Controller extends HttpServlet {

    private static final String PARAMETER1 = "p1";
    private static final String PARAMETER2 = "p2";

    private static final String MODEL = "Model";

    private final Logic logic = new Logic();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }

    private void serviceRequest(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String parameter1 = req.getParameter(PARAMETER1);
        String parameter2 = req.getParameter(PARAMETER2);

        Model model = logic.process(parameter1, parameter2);
        req.setAttribute(MODEL, model);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/addition-view");
        dispatcher.forward(req, res);
    }
}
