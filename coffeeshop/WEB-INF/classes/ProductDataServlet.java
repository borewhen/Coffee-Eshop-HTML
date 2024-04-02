import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ProductDataServlet")
public class ProductDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type to HTML
        response.setContentType("text/html");

        // Get PrintWriter object
        PrintWriter out = response.getWriter();

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open connection with JDBC URL, username, and password
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffeedb", "myuser", "xxxx");

            // Create a statement
            Statement statement = conn.createStatement();

            // Execute SQL query to fetch product data
            String sql = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(sql);

            // Output product data as HTML
            out.println("<html><head><title>Product Data</title></head><body>");
            out.println("<h1>Product Data</h1>");
            out.println("<table border=\"1\"><tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th></tr>");
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("id") + "</td>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td>" + resultSet.getString("description") + "</td>");
                out.println("<td>" + resultSet.getDouble("price") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Close the result set and connection
            resultSet.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
