package filters;

import Listeners.StringResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "footer", urlPatterns = {"*.html"})
public class FooterFilter implements Filter {

    private static final String FOOTER = "<div id=\"date\"></div>" +
            "<script src=\"/scripts/date.js\"></script>" +
            "</body></html>";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StringResponseWrapper wrapperResponse = new StringResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest, wrapperResponse);

        appendServletGeneratedResponse(servletResponse, wrapperResponse);

        appendFooter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void appendServletGeneratedResponse(ServletResponse originalResponse, StringResponseWrapper wrapperResponse)
            throws IOException {
        String wrapperContent = wrapperResponse.content();
        PrintWriter originalResponseWriter = originalResponse.getWriter();
        originalResponseWriter.print(wrapperContent);
    }

    private void appendFooter(ServletRequest request, ServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        // Locale locale = request.getLocale();
        // DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, locale);
        // writer.println("\n Footer 1 - " + df.format(new Date()));
        writer.println(FOOTER);
        writer.close();
    }
}
