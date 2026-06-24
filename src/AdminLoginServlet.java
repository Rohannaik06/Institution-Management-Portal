import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admins WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                // लॉगिन यशस्वी: सेशन सेट करा
                HttpSession session = request.getSession();
                session.setAttribute("adminEmail", email);
                session.setAttribute("adminName", rs.getString("username"));
                
                response.sendRedirect("admin-dashboard.html"); // तुमचा ऍडमिन डॅशबोर्ड
            } else {
                response.getWriter().println("Invalid Email or Password!");
            }
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}