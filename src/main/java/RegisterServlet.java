

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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Connection con;
		PreparedStatement ps;
		ResultSet s;
		ServletContext context=getServletContext();
		try
		{
				con=MySQLDatabaseConnection.getDatabaseConnection(context);
			   
		        String query="insert into admin(username,email,password) values(?,?,?)";
		        ps = con.prepareStatement(query);
		        ps.setString(1, username);
		        ps.setString(2, email);
		        ps.setString(3, password);
		        int row=ps.executeUpdate();
		        
		        if(row > 0) {
		        	out.print("<h1 style='color:green;'>Admin Register Successfully , "+username+"</h1>");
//		        	out.print("<a href='Login.html'><button >Sign in</button></a>");
 
		        	RequestDispatcher rd=request.getRequestDispatcher("/Login.html");
		        	rd.include(request, response);
		        }
		        else {
		        	out.print("<h1 style='color:red;'>Admin not Found , "+username+".....</h1>");
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
