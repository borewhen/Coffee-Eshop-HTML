import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Establishing a database connection
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/coffeedb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "myuser", "xxxx");      

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // Set session attribute to indicate user is logged in
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                // Redirect to home page
                response.sendRedirect("index.html");
            } else {
                // Redirect back to login page with error message
                response.sendRedirect("login.html?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
