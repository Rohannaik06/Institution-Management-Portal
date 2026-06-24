import java.io.*;
import java.sql.*;
// import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DashboardStatsServlet")
public class DashboardStatsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            Connection con = DBConnection.getConnection();
            
            // विद्यार्थ्यांची संख्या मोजण्यासाठी क्वेरी
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM admissions");
            int totalStudents = rs.next() ? rs.getInt(1) : 0;
            
            // तुम्ही तुमच्या टेबलचे नाव बदलून (उदा. faculty, courses) हे आकडे घेऊ शकता
            String json = String.format("{\"students\": %d, \"faculty\": 35, \"courses\": 12, \"pending\": 18}", totalStudents);
            
            response.getWriter().write(json);
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}