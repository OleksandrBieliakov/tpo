package Views;

import DTOs.Responses.LoginRes;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login-view")
public class LoginView extends HttpServlet {

    private static final String MODEL = "login-model";

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
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        //out.println(START);

        LoginRes responseModel = (LoginRes) request.getAttribute(MODEL);

        if (responseModel != null) {
            out.println(responseModel.getIdUser());
        }

        //out.println(END);
        out.close();
    }
}
