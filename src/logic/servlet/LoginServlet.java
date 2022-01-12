package logic.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.LoginController;
import logic.bean.LoginBean;
import logic.enumeration.UserRole;
import logic.util.Session;
import logic.util.WebURLs;

import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */

// @author Danilo D'Amico

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginBean bean = new LoginBean(username, password);
        LoginController controller = new LoginController();

        UserRole type;

        try {
            type = controller.loginUser(bean);

            Session.getSession().setCurrUser(username);
            Session.getSession().setRole(type);

            response.sendRedirect(WebURLs.getEvents());

        } catch (Exception e) {
            e.printStackTrace();
            // username o password errati
        }

    }

}
