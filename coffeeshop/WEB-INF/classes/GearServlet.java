// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/gear")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class GearServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      HttpSession session = request.getSession(true);

      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html lang=\"en\">");

      //HEADER
      out.println("<head>");
      out.println("<meta charset=\"UTF-8\">");
      out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
      out.println("<title>Arc Coffee</title>");
      out.println("<script src=\"script.js\"></script>");
      out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
      out.println("</head>");

      //BODY
      out.println("<body>");
      out.println("<div id=\"navbar-container\"></div>");
      out.println("<div class=\"image-container\">");
      out.println("<!-- Background image -->");
      out.println("<img src=\"shot1.jpeg\" alt=\"Background Image\" style=\"width: 100vw\">");
      out.println("<div class=\"text-container\" style=\"top: 90%; left: 40%\">");
      out.println("<a href=\"#\">Carefully curated, from worldwide.</a>");
      out.println("</div>");
      out.println("</div>");

      out.println("<div class=\"content\" style=\"padding-left: 20px\">");

      out.println("<h1 style=\"padding-left:50px\">Gear</h1>");

      //FILTERS
      out.println("<div class=\"flex-container\" style=\"padding: 20px\">");
      out.println("<!-- LEFT BLOCK FOR FILTERS -->");
      out.println("<div class=\"flex-item-large\" style=\"height: 500px; overflow-y: auto\">");
      out.println("<h3 style=\"margin: 25px; color: #441f12\">Search</h3>");
      out.println("<!-- Filter Options -->");
      out.println("<form method='get' action='gearquery'>");
      out.println("<div class=\"filter-option\">");
      out.println("<label for=\"category\">Type:</label>");
      out.println("<label><input type=\"checkbox\" name=\"type\" value=\"Dripper\">Dripper</label>");
      out.println("<label><input type=\"checkbox\" name=\"type\" value=\"Kettle\">Kettle</label>");
      out.println("</div>");
      out.println("<div class=\"filter-option\">");
      out.println("<label for=\"category\">Brand:</label>");
      out.println("<label><input type=\"checkbox\" name=\"brand\" value=\"ORIGAMI\">ORIGAMI</label>");
      out.println("<label><input type=\"checkbox\" name=\"brand\" value=\"Hario\">Hario</label>");
      out.println("<label><input type=\"checkbox\" name=\"brand\" value=\"Miyako\">Miyako</label>");
      out.println("</div>");
      out.println("<p><input type='submit' value='Apply Filters' /></p>");
      out.println("</form>");
      out.println("</div>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/coffeedb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         
         String sqlStr = "SELECT * FROM gear;"; //SEARCH ALL
         //sqlStr += "ORDER BY ID ASC;";

         //out.println("<h3>Thank you for your query.</h3>");
         //out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server


         
         //FLEXBOX
         out.println("<!-- RIGHT BLOCK FOR SHOP ITEMS -->");
         out.println("<div class=\"flex-item-large\" style=\"height: 500px; width: 60vw; margin-right: 25px; overflow-y: auto\">");
         out.println("<!-- SHOP ITEMS -->");
         out.println("<div class=\"flex-container\" style=\"padding: 32px\">");


         // Step 4: Process the query result
         // Print the <form> start tag
         out.println("<form method='get' action='bag' style='display: flex; flex-wrap: wrap; gap: 20px'>");
         // For each row in ResultSet, print one checkbox inside the <form>
         while(rset.next()) {
            out.println("<div class='flex-item-small' style='height: 300px; font-family: Avenir Light; color: #441f12; font-size: 12px'>");
            out.println("<img src=\"gear.png\" alt=\"gear\" width=\"50px\" height=\"50px\" style=\"margin-top: 20px\">");
            out.println("<h3><input type='checkbox' name='ID' value='addItem'/></h3>");
            out.println("<h4>" + rset.getString("name") + "</h4>");
            out.println("<h4>" + rset.getString("type") + "</h4>");
            out.println("<h4>" + rset.getString("brand") + "</h4>");
            out.println("<h4>$" + rset.getString("price") + "</h4>");
            /*
                  + rset.getString("author") + ", "
                  + rset.getString("title") + ", $"
                  + rset.getString("price") + "</p>*/
            out.println("</div>");
         }
         // Print the submit button and </form> end-tag
         out.println("</div>");
         out.println("<p style='align-content: right'><input type='submit' value='Add to Bag' /></p>");
         out.println("</form>");
         out.println("</div>");

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}