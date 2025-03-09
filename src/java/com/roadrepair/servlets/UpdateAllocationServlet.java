import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAllocationServlet")
public class UpdateAllocationServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ROADREPAIR";
    private static final String DB_USER = "sequel";
    private static final String DB_PASSWORD = "PlBt@2020";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int complaintId = Integer.parseInt(request.getParameter("complaintid"));
        int manpowerAllocated = Integer.parseInt(request.getParameter("manpower_allocated"));
        int machinesAllocated = Integer.parseInt(request.getParameter("machines_allocated"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Update Query
            String sql = "UPDATE Resources SET manpower_allocated = ?, machines_allocated = ? WHERE complaintid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, manpowerAllocated);
            stmt.setInt(2, machinesAllocated);
            stmt.setInt(3, complaintId);

            int rowsUpdated = stmt.executeUpdate();
            conn.close();

            // Redirect back to the dashboard
            if (rowsUpdated > 0) {
                out.println("<script>alert('Update Successful!'); window.location='GetResourcesServlet';</script>");
            } else {
                out.println("<script>alert('Complaint ID not found!'); window.location='GetResourcesServlet';</script>");
            }

        } catch (Exception e) {
            out.println("<script>alert('Error updating allocation.'); window.location='GetResourcesServlet';</script>");
            e.printStackTrace();
        }
    }
}
