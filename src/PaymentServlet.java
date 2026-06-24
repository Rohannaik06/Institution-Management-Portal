import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO payments(student_name, course_name, amount, payment_mode, status, payment_date) VALUES(?,?,?,?,?, NOW())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "Rohan Naik");
            ps.setString(2, "B.Tech Information Technology");
            ps.setString(3, "120000");
            ps.setString(4, "UPI");
            ps.setString(5, "Paid");
            ps.executeUpdate();
            con.close();
            response.sendRedirect("receipt.html");
        } catch (Exception e) { e.printStackTrace(); }
    }
}