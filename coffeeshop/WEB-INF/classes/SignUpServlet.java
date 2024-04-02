import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieving sign-up data from the HTML form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String shippingAddress = request.getParameter("shippingAddress");
        String cardNo = request.getParameter("cardNo");
        String dateOfExpiry = request.getParameter("dateOfExpiry");
        String CVV = request.getParameter("CVV");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing a database connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/coffeedb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "xxxx");

            // Inserting sign-up data into the users table using prepared statement
            String sqlInsert = "INSERT INTO users (username, email, password, shippingAddress, cardNo, dateOfExpiry, CVV, firstName, lastName) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlInsert);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, shippingAddress);
            statement.setString(5, cardNo);
            statement.setString(6, dateOfExpiry);
            statement.setString(7, CVV);
            statement.setString(8, firstName);
            statement.setString(9, lastName);

            int countInserted = statement.executeUpdate();

            response.sendRedirect("wait.html");
            
            // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
        } catch(SQLException | ClassNotFoundException ex) {
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h3>Error occurred during sign up:</h3>");
            out.println("<p>" + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            out.println("</body></html>");

            ex.printStackTrace();
        } finally {
            out.close();
        }
    }
}
