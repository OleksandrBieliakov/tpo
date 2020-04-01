package addition;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/addition-view")
public class View extends HttpServlet {

    private static final String MODEL = "Model";

    private static final String START = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1250\">\n" +
            "    <title>Test</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<form method=\"get\" action=\"/addition\">\n" +
            "    <label>Number1 <input type=\"number\" name=\"p1\" required></label><br>\n" +
            "    <label>Number2 <input type=\"number\" name=\"p2\" required></label><br>\n" +
            "    <button type=\"submit\" formmethod=\"post\">POST</button>\n" +
            "    <button type=\"submit\" formmethod=\"get\">GET</button>\n" +
            "</form>\n";
    private static final String END = "\n</body>\n" +
            "</html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }

    private void serviceRequest(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println(START);

        Model model = (Model) request.getAttribute(MODEL);
        if (model != null) {
            out.println(model.getResult());
        }

        out.println(END);
        out.close();
    }

}
