

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
 * Servlet implementation class DeleteBookServlet
 */
@WebServlet("/DeleteBookServlet")
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int bookId =Integer.parseInt(request.getParameter("bookId"));
        
        Connection con;
		PreparedStatement ps;
		ResultSet rs;
		ServletContext context=getServletContext();
		try
		{
			
			con=MySQLDatabaseConnection.getDatabaseConnection(context);
			
		        String query="delete from books where BookID=?";
		        ps = con.prepareStatement(query);
		        ps.setInt(1, bookId);
		      
		        int row=ps.executeUpdate();
		        
		        if(row > 0) {
		        	out.print("<h1 style='color:Red;'>BookId = "+bookId+" Deleted Successfully from the Library</h1>");
		        	RequestDispatcher rd = request.getRequestDispatcher("viewBookServlet");
		        	rd.include(request, response);
		        }
		        else {
		        	out.print("<h1 style='color:red;'>BookId = "+bookId+" not Deleted Successfully from the Library</h1>");
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
