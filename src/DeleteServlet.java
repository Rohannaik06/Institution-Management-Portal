import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM admissions WHERE id=?");
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
            con.close();
            response.sendRedirect("admin-dashboard.html");
        } catch (Exception e) { e.printStackTrace(); }
    }
}