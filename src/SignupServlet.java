
import java.io.IOException;

import java.sql.Connection;

import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;

// import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fullname =

                request.getParameter("fullname");

        String email =

                request.getParameter("email");

        String password =

                request.getParameter("password");

        try {

            Connection con =

                    DBConnection.getConnection();

            String sql =

            "INSERT INTO users(fullname,email,password) VALUES(?,?,?)";

            PreparedStatement ps =

                    con.prepareStatement(sql);

            ps.setString(1,fullname);

            ps.setString(2,email);

            ps.setString(3,password);

            ps.executeUpdate();

            con.close();

            response.sendRedirect("login.html");

        }

        catch(Exception e) {

                response.setContentType("text/html");

                response.getWriter().println("<h2>Error occurred</h2>");

                response.getWriter().println("<pre>");

                e.printStackTrace(response.getWriter());

                response.getWriter().println("</pre>");
        }

    }
} 
