package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Show", urlPatterns = "/user/show")
public class Show extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int id = Integer.parseInt(request.getParameter("id"));

        UserDAO userDAO = new UserDAO();
        User user = userDAO.Read(id);

        request.setAttribute("user",user);
        getServletContext().getRequestDispatcher("/users/show.jsp").forward(request,response);
    }
}
