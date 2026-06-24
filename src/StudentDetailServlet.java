import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/StudentDetailServlet")
public class StudentDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admissions WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                out.println("<html><head><title>Student Profile</title>");
                out.println("<style>");
                out.println("body { font-family: 'Poppins', sans-serif; background: #f5f7fb; padding: 50px; }");
                out.println(".profile-card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 400px; margin: auto; }");
                out.println("h2 { color: #08142f; margin-bottom: 20px; }");
                out.println("p { font-size: 16px; color: #475569; margin: 10px 0; }");
                out.println("b { color: #0f172a; }");
                out.println(".back-btn { display: block; margin-top: 20px; text-decoration: none; color: #2563eb; font-weight: 600; }");
                out.println("</style></head><body>");
                
                out.println("<div class='profile-card'>");
                out.println("<h2>Student Details</h2>");
                out.println("<p><b>Full Name:</b> " + rs.getString("full_name") + "</p>");
                out.println("<p><b>Email:</b> " + rs.getString("email") + "</p>");
                out.println("<p><b>Course:</b> " + rs.getString("course") + "</p>");
                out.println("<p><b>Status:</b> " + rs.getString("status") + "</p>");
                out.println("<a href='students.html' class='back-btn'>← Back to Students List</a>");
                out.println("</div>");
                
                out.println("</body></html>");
            } else {
                out.println("<h3>Student not found!</h3>");
            }
            con.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
            out.println("Error loading details!");
        }
    }
}