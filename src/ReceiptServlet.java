import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ReceiptServlet")
public class ReceiptServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String studentId = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM admissions WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(studentId));
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                out.println("<html><head><title>Receipt - " + rs.getString("full_name") + "</title>");
                out.println("<style>");
                out.println("body { font-family: 'Poppins', sans-serif; background: #f1f5f9; padding: 40px; display: flex; justify-content: center; }");
                out.println(".receipt { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 500px; border-top: 8px solid #2563eb; }");
                out.println("h1 { color: #0f172a; text-align: center; }");
                out.println(".info { margin: 20px 0; border-top: 1px solid #e2e8f0; padding-top: 20px; }");
                out.println("p { font-size: 16px; color: #334155; margin: 10px 0; }");
                out.println(".actions { text-align: center; margin-top: 30px; }");
                out.println("button, a { padding: 10px 20px; border-radius: 8px; text-decoration: none; font-weight: 500; cursor: pointer; transition: 0.3s; }");
                out.println("button { background: #2563eb; color: white; border: none; }");
                out.println("a { background: #e2e8f0; color: #475569; margin-left: 10px; }");
                out.println("@media print { .actions { display: none; } }");
                out.println("</style></head><body>");
                
                out.println("<div class='receipt'>");
                out.println("<h1>Payment Receipt</h1>");
                out.println("<div class='info'>");
                out.println("<p><b>Receipt ID:</b> RCP-" + rs.getInt("id") + "</p>");
                out.println("<p><b>Student Name:</b> " + rs.getString("full_name") + "</p>");
                out.println("<p><b>Email:</b> " + rs.getString("email") + "</p>");
                out.println("<p><b>Course:</b> " + rs.getString("course") + "</p>");
                out.println("<p><b>Status:</b> <span style='color:green;'>SUCCESS</span></p>");
                out.println("</div>");
                
                out.println("<div class='actions'>");
                out.println("<button onclick='window.print()'>🖨 Print Receipt</button>");
                out.println("<a href='admin-dashboard.html'>← Back</a>");
                out.println("</div></div>");
                
                out.println("</body></html>");
            } else {
                out.println("<h3>Student record not found!</h3>");
            }
            con.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
            out.println("Error: " + e.getMessage());
        }
    }
}