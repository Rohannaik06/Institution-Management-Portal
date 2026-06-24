import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CourseDetailsServlet")
public class CourseDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null) {
            String email = (String) session.getAttribute("email");
            
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT course, stream FROM admissions WHERE email = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    String course = rs.getString("course");
                    
                    // डेटा localStorage मध्ये सेट करा
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><script>");
                    out.println("localStorage.setItem('selectedCourse', '" + course + "');");
                    out.println("window.location.href = 'courses.html';"); // शेवटी पेजवर पाठवा
                    out.println("</script></body></html>");
                }
                con.close();
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            response.sendRedirect("login.html");
        }
    }
}