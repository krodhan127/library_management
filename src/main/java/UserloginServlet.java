

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
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UserloginServlet")
public class UserloginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserloginServlet() {
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
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", "Manoj@2004");
		    
		    // Continue with your query logic here

		

			
		        
		        String query="select * from user where email=? and password=?";
		        ps = con.prepareStatement(query);
		        ps.setString(1, username);
		        ps.setString(2, password);
		        rs=ps.executeQuery();
		        
		        if(rs.next()) {
		        	HttpSession session = request.getSession(true);
		            session.setMaxInactiveInterval(300);
		            session.setAttribute("UserId", rs.getInt("userid"));
		            session.setAttribute("User Name", rs.getString("Username"));	            
		            
		        	out.print("<h1 Style='color:gold;'>User Login Successfully , "+rs.getString("Username")+"</h1>");
		        	RequestDispatcher rd=request.getRequestDispatcher("UserViewBooks");
		        	rd.include(request, response);
		        	
		        }
		        else {
		        	out.print("<h1>User not Found , "+username+".....</h1>");
		        	out.print("<h2 Style='color:red;'> Please Enter Valid Username and Password </h2>");
		        	RequestDispatcher rd=request.getRequestDispatcher("UserLogin.html");
		        	rd.include(request, response);
		        }
		        
	} catch (ClassNotFoundException | SQLException e) {
	    e.printStackTrace();
	    out.println("<h2 style='color:red;'>Database connection failed: " + e.getMessage() + "</h2>");
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
