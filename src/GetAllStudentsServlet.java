import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/GetAllStudentsServlet")
public class GetAllStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM admissions");

            System.out.println("Query executed successfully"); // हे कन्सोलमध्ये दिसते का पहा
            while(rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("course") + "</td>");
                out.println("<td><a href='StudentDetailServlet?id="+rs.getInt("id")+"' class='btn view'>Details</a></td>");
                out.println("</tr>");
            }
            con.close();
        } catch (Exception e) { 
            out.println("<tr><td colspan='5'>Error loading data!</td></tr>");
        }
    }
}