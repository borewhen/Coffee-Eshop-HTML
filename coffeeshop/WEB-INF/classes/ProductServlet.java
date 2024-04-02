import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        List<String> products = retrieveProductsFromDatabase(category);
        
        request.setAttribute("products", products);
        
        if ("CF".equals(category)) {
            request.getRequestDispatcher("beans.html").forward(request, response);
        } else if ("GE".equals(category)) {
            request.getRequestDispatcher("gear.html").forward(request, response);
        } else {
            // Handle invalid category
            response.getWriter().println("Invalid category");
        }
    }

    private List<String> retrieveProductsFromDatabase(String category) {
        List<String> products = new ArrayList<String>();
        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "myuser", "xxxx");
            // Prepare SQL query
            String sql = "SELECT name FROM product WHERE category = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category);
            // Execute query
            ResultSet resultSet = statement.executeQuery();
            // Process result set
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                products.add(productName);
            }
            // Close resources
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
