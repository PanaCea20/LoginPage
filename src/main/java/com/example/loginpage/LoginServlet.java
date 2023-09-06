package com.example.loginpage;

import java.io.*;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/hello-servlet")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try {
            PrintWriter out= response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/LoginPage","root","root");
            String n= request.getParameter("txtName");
            String p= request.getParameter("txtPwd");

            PreparedStatement ps = con.prepareStatement("select uname from login where uname=? and password=?");
            ps.setString(1, n);
            ps.setString(2, p);
            ResultSet rs =  ps.executeQuery();
            if(rs.next()){
                RequestDispatcher rd = request.getRequestDispatcher("/LoginPage/Welcome.jsp");
                rd.forward(request, response);
            } else {
                out.println("<font color=red size=18>Login Failed!<br>");
                out.println("<a href=login.jsp>Try again! </a>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}