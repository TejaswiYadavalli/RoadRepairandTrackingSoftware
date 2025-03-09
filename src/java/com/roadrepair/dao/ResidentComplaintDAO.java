/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roadrepair.dao; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 


/**
 *
 * @author apiiit123
 */
public class ResidentComplaintDAO {
     private static final String URL = "jdbc:mysql://localhost:3306/ROADREPAIR"; // Change your database name
    private static final String USER = "sequel";
    private static final String PASSWORD = "PlBt@2020";

    // JDBC Driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static boolean saveResidentComplaint(String name, String phone, String email, String password, String problem) {
        Connection conn = null;
        PreparedStatement pstmtResident = null;
        PreparedStatement pstmtComplaint = null;

        try {
            Class.forName(DRIVER); // Load JDBC Driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false); // Start transaction

            // Insert into Resident table
            String residentQuery = "INSERT INTO Resident (Name, phone_number, email_id, password) VALUES (?, ?, ?, ?)";
            pstmtResident = conn.prepareStatement(residentQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtResident.setString(1, name);
            pstmtResident.setString(2, phone);
            pstmtResident.setString(3, email);
            pstmtResident.setString(4, password);

            int affectedRows = pstmtResident.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback(); // Rollback if insertion fails
                return false;
            }

            // Get the generated Resident ID
            int residentId = 0;
            var rs = pstmtResident.getGeneratedKeys();
            if (rs.next()) {
                residentId = rs.getInt(1);
            }

            // Insert into Complaint table
            String complaintQuery = "INSERT INTO Complaint (complaintid, problem) VALUES (?, ?)";
            pstmtComplaint = conn.prepareStatement(complaintQuery);
            pstmtComplaint.setInt(1, residentId);
            pstmtComplaint.setString(2, problem);
            pstmtComplaint.executeUpdate();

            conn.commit(); // Commit transaction
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // Rollback in case of failure
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmtResident != null) pstmtResident.close();
                if (pstmtComplaint != null) pstmtComplaint.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
