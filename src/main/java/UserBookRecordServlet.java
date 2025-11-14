

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserBookRecordServlet
 */
@WebServlet("/UserBookRecordServlet")
public class UserBookRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBookRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection con;
		PreparedStatement ps;
		ResultSet rs ;
		ServletContext context=getServletContext();
		try
		{	
			con = MySQLDatabaseConnection.getDatabaseConnection(context);
			String query = "SELECT * FROM getbooks";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery(); 

            	 out.print("<html><head><title>ITP Library User Books Record</title>");
                 out.print("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
                 out.print("</head><body class='container mt-4'>");
                 out.print("<h2 class='text-center mb-4'>ITP Library User Books Record</h2>");
                 out.print("<div class='table-responsive'><table class='table table-striped table-hover'>");
                 out.print("<thead class='table-dark'>");
                 out.print("<tr>");
                 out.print("<th>Id</th>");
                 out.print("<th>Book ID</th>");
                 out.print("<th>User Id</th>");
                 out.print("<th>Buy Time</th>");
                 out.print("<th>Return Time</th>");
                 out.print("</tr>");
                 out.print("</thead>");
                 out.print("<tbody>");

                while (rs.next()) {
                    out.print("<tr>");
                    out.print("<td>" + rs.getInt("id") + "</td>");
                    out.print("<td>" + rs.getInt("BookID") + "</td>");
                    out.print("<td>" + rs.getInt("userid") + "</td>");
                    out.print("<td>" + rs.getString("buytime") + "</td>");
                    out.print("<td>" + rs.getString("returntime") + "</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("<p class='text-center mt-3'>Go Back? <a href='Admin-Operation.html' style='text-decoration-line:none;'>Previous Page</a></p>");
                out.print("</body></html>");
        } catch (SQLException e) {
            out.print("<p>Error: " + e.getMessage() + "</p>");
        }
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
