import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewStudentsServlet")
public class ViewStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        StringBuilder json = new StringBuilder("[");
        
        try {
            Connection con = DBConnection.getConnection();
            // तुमच्या डेटाबेस टेबलचे नाव 'admissions' आहे याची खात्री करा
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM admissions");
            
            boolean first = true;
            while(rs.next()) {
                if(!first) json.append(",");
                json.append("{")
                    .append("\"id\":").append(rs.getInt("id")).append(",")
                    .append("\"name\":\"").append(rs.getString("full_name")).append("\",")
                    .append("\"course\":\"").append(rs.getString("course")).append("\",")
                    .append("\"status\":\"Approved\",")
                    .append("\"payment\":\"Paid\"")
                    .append("}");
                first = false;
            }
            json.append("]");
            out.print(json.toString());
            con.close();
        } catch (Exception e) { 
            e.printStackTrace();
            out.print("[]"); // एरर आल्यास रिकामी लिस्ट पाठवा
        }
    }
}