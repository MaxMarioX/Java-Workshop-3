package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="test", urlPatterns = "/users/test")
public class Test extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO users = new UserDAO();
        User[] new_users = users.finalAll();

        for(int a = 0; a < new_users.length; a++)
        {
            User user = new_users[a];
            response.getWriter().append(user.getId() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword());
        }
    }

}
