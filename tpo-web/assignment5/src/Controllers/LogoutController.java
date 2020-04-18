package Controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private static final String VIEW_LOGIN = "/index.html";

    private static final String USER_ID = "userId";
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_LAST_NAME = "userLastName";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if (cookieName.equals(USER_ID) || cookieName.equals(USER_FIRST_NAME) || cookieName.equals(USER_LAST_NAME)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        response.sendRedirect(VIEW_LOGIN);
    }
}
