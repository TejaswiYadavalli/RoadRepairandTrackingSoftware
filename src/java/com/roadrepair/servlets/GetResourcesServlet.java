import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GetResourcesServlet")
public class GetResourcesServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ROADREPAIR";
    private static final String DB_USER = "sequel";
    private static final String DB_PASSWORD = "PlBt@2020";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM Resources";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("complaintid") + "</td>");
                out.println("<td>" + rs.getInt("manpower_need") + "</td>");
                out.println("<td>" + rs.getInt("machines_need") + "</td>");
                out.println("<td>" + rs.getInt("manpower_allocated") + "</td>");
                out.println("<td>" + rs.getInt("machines_allocated") + "</td>");
                out.println("</tr>");
            }
            conn.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='5'>Error fetching data</td></tr>");
            e.printStackTrace();
        }
    }
}
