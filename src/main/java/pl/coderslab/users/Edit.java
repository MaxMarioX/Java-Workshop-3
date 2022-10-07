package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Edit", urlPatterns = "/user/edit")
public class Edit extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.Read(Integer.parseInt(id));

        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/users/edit.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setUserName(request.getParameter("UserName"));
        user.setEmail(request.getParameter("UserEmail"));
        user.setPassword(request.getParameter("UserPassword"));

        UserDAO userDAO = new UserDAO();
        userDAO.Update(user);

        response.sendRedirect(request.getContextPath() + "/user/list");
    }
}
