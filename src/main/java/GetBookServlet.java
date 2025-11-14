

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetBookServlet
 */
@WebServlet("/GetBookServlet")
public class GetBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session=request.getSession(false);        
        Integer userIdObj = (Integer) session.getAttribute("UserId");
        int userId = (userIdObj != null) ? userIdObj : -1; // -1 as default if null


        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int publishedYear =Integer.parseInt(request.getParameter("publishedYear"));
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = currentDateTime.format(formatter);

   
			out.println("<html><head><link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'></head><body>");
	        out.println("<div class='container mt-5'>");

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        ServletContext context = getServletContext();
	        try {    
	            con = MySQLDatabaseConnection.getDatabaseConnection(context);
	   
	            String query = "SELECT * FROM BOOKS WHERE Title=? AND Author=? AND PublishedYear=?";
	            ps = con.prepareStatement(query);
	            ps.setString(1, title);
	            ps.setString(2, author);
	            ps.setInt(3, publishedYear);
	            rs = ps.executeQuery(); 

	            if (rs.next()) {
	                int bookId = rs.getInt("BookID");
	                
	                out.print("<div class='card shadow-lg p-4'>");
	                out.print("<h3 class='card-title text-primary text-center'>Book Details</h3>");
	                out.print("<div class='card-body'>");
	                out.print("<p><strong>Book ID:</strong> " + bookId + "</p>");
	                out.print("<p><strong>Title:</strong> " + rs.getString("Title") + "</p>");
	                out.print("<p><strong>Author:</strong> " + rs.getString("Author") + "</p>");
	                out.print("<p><strong>Published Year:</strong> " + rs.getInt("PublishedYear") + "</p>");
	                out.print("<p><strong>Date & Time:</strong> " + formattedDateTime + "</p>");
	                
	               
	                String update = "UPDATE books SET totalcopies = CASE WHEN totalcopies > 0 THEN totalcopies - 1 ELSE totalcopies END WHERE BookID = ?";
	                ps = con.prepareStatement(update);
	                ps.setInt(1, bookId);
	                int row = ps.executeUpdate();
	                if (row > 0) {
	                    out.print("<p class='text-success'><b>Book count updated!</b></p>");
	                } else {
	                    out.print("<p class='text-danger'>Book not updated (maybe out of stock).</p>");
	                }

	               
	                String insert = "INSERT INTO getbooks (bookid, userid, buytime) VALUES (?, ?, ?)";
	                ps = con.prepareStatement(insert);
	                ps.setInt(1, bookId);
	                ps.setInt(2, userId); 
	                ps.setString(3, formattedDateTime);
	                int r = ps.executeUpdate();
	                if (r > 0) {
	                    out.print("<p class='text-success'><b>Book Issued Successfully!</b></p>");
	                } else {
	                    out.print("<p class='text-danger'>Could not issue book.</p>");
	                }
	                
	                out.print("</div></div>");
	            } else {
	                out.print("<h3 class='text-danger text-center'>Book is not available.</h3>");
	            }
	        } catch (SQLException e) {
	            out.print("<p class='text-danger'>Error: " + e.getMessage() + "</p>");
	        }

	        // Buttons with proper styling
	        out.print("<div class='mt-4 d-flex justify-content-center gap-3'>");
	        out.print("<a href='getBooks.html' class='btn btn-warning'>Get Another Book</a>");
	        out.print("<a href='logOut' class='btn btn-danger'>Log Out</a>");
	        out.print("</div>");

	        out.println("</div></body></html>");
	    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
