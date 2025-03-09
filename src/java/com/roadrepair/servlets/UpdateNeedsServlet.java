import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateNeedsServlet")
public class UpdateNeedsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ROADREPAIR";
    private static final String DB_USER = "sequel";
    private static final String DB_PASSWORD = "PlBt@2020";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        Connection conn = null;
        int rowsUpdated = 0;

        try {
            // Retrieve form parameters (instead of JSON)
            int complaintId = Integer.parseInt(request.getParameter("complaintId"));
            int manpowerNeed = Integer.parseInt(request.getParameter("manpowerNeed"));
            int machinesNeed = Integer.parseInt(request.getParameter("machinesNeed"));

            // Debugging: Print received values
            System.out.println("Complaint ID: " + complaintId);
            System.out.println("Manpower Need: " + manpowerNeed);
            System.out.println("Machines Need: " + machinesNeed);

            // Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Update Query
            String sql = "UPDATE Resources SET manpower_need = ?, machines_need = ? WHERE complaintid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, manpowerNeed);
            stmt.setInt(2, machinesNeed);
            stmt.setInt(3, complaintId);

            rowsUpdated = stmt.executeUpdate();
        } 
        catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Database driver not found");
            return;
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating needs");
            return;
        } 
        finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Send text response instead of JSON
        if (rowsUpdated > 0) {
            response.getWriter().write("Update successful");
        } else {
            response.getWriter().write("Complaint ID not found");
        }
    }
}


