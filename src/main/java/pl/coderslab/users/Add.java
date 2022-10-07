package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Add", urlPatterns = "/user/add")
public class Add extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User new_user = new User();
        new_user.setUserName(request.getParameter("UserName"));
        new_user.setEmail(request.getParameter("UserEmail"));
        new_user.setPassword(request.getParameter("UserEmail"));

        UserDAO user = new UserDAO();
        user.Create(new_user);

        response.sendRedirect("/user/list");
    }
}
