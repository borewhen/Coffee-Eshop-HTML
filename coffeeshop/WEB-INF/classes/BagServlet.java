import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/bag")
public class BagServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
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

          out.println("<h1 style=\"padding-left:50px\">Shopping Bag</h1>");

    //

          try(
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/coffeedb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
          ) {
        String[] id = request.getParameterValues("ID");  // Returns an array of Strings

        //FOR BEANS

        String sqlStr = "SELECT * FROM beans WHERE ID IN ("; //SEARCH ID
         for (int i = 0; i < id.length; ++i) {
            if (i < id.length - 1) {
               sqlStr += "'" + id[i] + "', ";  // need a commas
            } else {
               sqlStr += "'" + id[i] + "'";    // no commas
            }
         }

         sqlStr += ");";
         //out.println("<h3>Thank you for your query.</h3>");
         //out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server
        
      out.println("<div class=\"flex-container\" style=\"padding: 20px\">");

      out.println("<div class=\"flex-item-large\" style=\"height: 60vh; width: 50vw; overflow-y: auto\">");
      
     while(rset.next()) {
        out.println("<div class='flex-item-small' style='height: 10vh; width: 40vh; margin: 24px; font-family: Avenir Light; color: #441f12; font-size: 12px'>");
        out.println("<h4>" + rset.getString("name") + ", " + rset.getString("origin") + ", " + rset.getString("process") + "-----" + rset.getString("price") + "</h4>");

        /*
              + rset.getString("author") + ", "
              + rset.getString("title") + ", $"
              + rset.getString("price") + "</p>*/
        out.println("</div>");
    }
//close try
    } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }

      out.println("<p style='align-content: right; padding-top: 5vh'><input type='submit' value='Checkout' /></p>");
      out.println("</div>");
      out.println("</div>");
      }
    
    
        // Redirect back to the referring page
        //response.sendRedirect(request.getHeader("referer"));
}
