package Views;

import Exceptions.WebException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/error")
public class ErrorView extends HttpServlet {

    private static final String EXCEPTION_TYPE_ATTRIBUTE = "javax.servlet.error.exception_type";
    private static final String EXCEPTION_ATTRIBUTE = "javax.servlet.error.exception";

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        Class<?> exceptionType = (Class<?>) req.getAttribute(EXCEPTION_TYPE_ATTRIBUTE);
        WebException exception = (WebException) req.getAttribute(EXCEPTION_ATTRIBUTE);
        if (exception != null) {
            PrintWriter out = res.getWriter();
            out.println("<html><body>");
            out.print("<h2>" + exceptionType.getCanonicalName() + "</h2><br>");
            out.println("<h2>" + exception.getMessage() + "</h2><hr>");
            Throwable cause = exception.getCause();
            if (cause instanceof SQLException) {
                out.print("<h2>" + cause.getClass().getCanonicalName() + "</h2><br>");
                SQLException sqlexc = (SQLException) cause;
                out.println(sqlexc.getMessage() + "<br><br>");
                out.println("Error code: " + sqlexc.getErrorCode() + "<br>");
                out.println("SQL state : " + sqlexc.getSQLState() + "<br>");
            }
            out.print("</body></html>");
            out.close();
        }
    }
}
