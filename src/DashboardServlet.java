import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null) {
            String email = (String) session.getAttribute("email");
            String name = (String) session.getAttribute("studentName");
            
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT course, stream, status FROM admissions WHERE email = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                
                String course = "--", stream = "--", status = "Pending";
                if (rs.next()) {
                    course = rs.getString("course");
                    stream = rs.getString("stream");
                    status = rs.getString("status");
                }
                
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body><script>");
                out.println("localStorage.setItem('studentName', '" + name + "');");
                out.println("localStorage.setItem('course', '" + course + "');");
                out.println("localStorage.setItem('status', '" + status + "');");
                out.println("localStorage.setItem('stream', '" + stream + "');");
                out.println("window.location.href = 'dashboard.html';");
                out.println("</script></body></html>");
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("login.html");
        }
    }
}