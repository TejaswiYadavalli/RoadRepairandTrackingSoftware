/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roadrepair.servlets;
import com.roadrepair.dao.ResidentComplaintDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author apiiit123
 */
@WebServlet("/SubmitComplaintServlet")

public class SubmitComplaintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("residentName");
        String phone = request.getParameter("residentPhone");
        String email = request.getParameter("residentEmail");
        String password = request.getParameter("residentPassword");
        String problem = request.getParameter("problem");
        ResidentComplaintDAO dao = new ResidentComplaintDAO();
        boolean isSaved = dao.saveResidentComplaint(name, phone, email, password, problem);

        if (isSaved) {
            response.getWriter().write("Complaint Submitted Successfully!");
        } else {
            response.getWriter().write("Error Submitting Complaint. Try Again!");
        }
    }

}
