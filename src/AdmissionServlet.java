import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AdmissionServlet")
public class AdmissionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        String fullName = request.getParameter("fullName");
        String mobile = request.getParameter("mobile");
        String gender = request.getParameter("gender");
        String stream = request.getParameter("stream");
        String course = request.getParameter("course");
        String address = request.getParameter("address");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO admissions(full_name, mobile, email, gender, stream, course, address) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, mobile);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setString(5, stream);
            ps.setString(6, course);
            ps.setString(7, address);

            int i = ps.executeUpdate();
            if (i > 0) {
                response.sendRedirect("DashboardServlet"); // Redirect to servlet to refresh data
            } else {
                response.getWriter().println("Admission Failed!");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}