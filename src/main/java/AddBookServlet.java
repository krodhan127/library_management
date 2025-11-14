

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddBookServlet
 */
@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int publishedYear =Integer.parseInt(request.getParameter("publishedYear"));
        int totalCopies =Integer.parseInt(request.getParameter("totalCopies"));
        
        Connection con;
		PreparedStatement ps;
		ResultSet rs;
		ServletContext context=getServletContext();
		try
		{
			
			con=MySQLDatabaseConnection.getDatabaseConnection(context);
		    String query="INSERT INTO Books (Title, Author, publishedYear, TotalCopies) values(?,?,?,?)";
		        ps = con.prepareStatement(query);
		        ps.setString(1, title);
		        ps.setString(2, author);
		        ps.setInt(3, publishedYear);
		        ps.setInt(4, totalCopies);
		        int row=ps.executeUpdate();
		        
		        if(row > 0) {
		        	out.print("<h1 style='color:white;'>"+title+" Book Added Successfully in the Library</h1>");
		        	RequestDispatcher rd = request.getRequestDispatcher("Admin-Operation.html");
		        	rd.include(request, response);
		        }
		        else {
		        	out.print("<h1 style='color:red;'>"+title+" Book not Added Successfully in the Library</h1>");
		        }
	        }
        catch(SQLException e) {
        	out.print(e.getMessage());
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
