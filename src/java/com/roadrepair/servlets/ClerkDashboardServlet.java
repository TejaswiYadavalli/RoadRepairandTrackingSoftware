package com.roadrepair.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ClerkDashboardServlet")
public class ClerkDashboardServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ROADREPAIR";
    private static final String DB_USER = "sequel";
    private static final String DB_PASSWORD = "PlBt@2020";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println("Servlet Called: ClerkDashboardServlet");

        StringBuilder json = new StringBuilder();
        json.append("["); // Start JSON array

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT c.complaintid, c.problem, c.status, r.manpower_need, r.machines_need, " +
                         "r.manpower_allocated, r.machines_allocated " +
                         "FROM Complaint c LEFT JOIN Resources r ON c.complaintid = r.complaintid";

            System.out.println("Query Executed: " + sql);

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                first = false;

                System.out.println("Fetching Complaint: " + rs.getInt("complaintid") + ", " + rs.getString("problem"));

                json.append("{");
                json.append("\"complaintid\":").append(rs.getInt("complaintid")).append(",");
                json.append("\"problem\":\"").append(rs.getString("problem")).append("\",");
                json.append("\"status\":\"").append(rs.getString("status")).append("\",");
                json.append("\"manpower_need\":").append(rs.getInt("manpower_need")).append(",");
                json.append("\"machines_need\":").append(rs.getInt("machines_need")).append(",");
                json.append("\"manpower_allocated\":").append(rs.getInt("manpower_allocated")).append(",");
                json.append("\"machines_allocated\":").append(rs.getInt("machines_allocated"));
                json.append("}");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]"); // End JSON array

        System.out.println("Response Sent: " + json.toString());

        response.getWriter().write(json.toString());
    }
}
