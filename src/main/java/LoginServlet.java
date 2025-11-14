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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        Connection con;
		PreparedStatement ps;
		ResultSet rs;
		ServletContext context=getServletContext();
		try
		{
			
			con=MySQLDatabaseConnection.getDatabaseConnection(context);
			
		        
		        String query="select * from admin where username=? and password=?";
		        ps = con.prepareStatement(query);
		        ps.setString(1, username);
		        ps.setString(2, password);
		        rs=ps.executeQuery();
		        
		        if(rs.next()) {
		        	out.print("<h1 Style='color:white;'>Login Successfully , "+username+"</h1>");
		        	RequestDispatcher rd=request.getRequestDispatcher("Admin-Operation.html");
		        	rd.include(request, response);
		        }
		        else {
		        	out.print("<h1>User not Found , "+username+".....</h1>");
		        	out.print("<h2 Style='color:red;'> Please Enter Valid Username and Password </h2>");
		        	RequestDispatcher rd=request.getRequestDispatcher("Login.html");
		        	rd.include(request, response);
		        }
		        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
