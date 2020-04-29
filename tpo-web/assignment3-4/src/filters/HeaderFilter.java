package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "header", urlPatterns = { "*.html"})
public class HeaderFilter implements Filter {

    private static final String HEADER = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1250\">\n" +
            "    <title>Sum</title>\n" +
            "    <script\n" +
            "            src=\"https://code.jquery.com/jquery-3.5.0.min.js\"\n" +
            "            integrity=\"sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=\"\n" +
            "            crossorigin=\"anonymous\"></script>\n" +
            "</head>\n" +
            "<body>";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter writer = servletResponse.getWriter();
        writer.println(HEADER);
        writer.close();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
