
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
 * Servlet implementation class UserViewBooks
 */
@WebServlet("/UserViewBooks")
public class UserViewBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserViewBooks() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		ServletContext context = getServletContext();
		try {
			con = MySQLDatabaseConnection.getDatabaseConnection(context);
			String query = "SELECT * FROM BOOKS";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			out.print("<html><head><title>ITP Library Books</title>");
			out.print(
					"<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
			out.print("<style> a{ text-decoration-line: none;color:white;} </style>");
			out.print("</head><body class='container mt-4'>");
			out.print("<h2 class='text-center mb-4 text-info'>ITP Library Books</h2>");
			out.print("<div class='table-responsive'><table class='table table-striped table-hover'>");
			out.print("<thead class='table-dark'>");
			out.print("<tr>");
			out.print("<th>Book Id</th>");
			out.print("<th>Title</th>");
			out.print("<th>Author</th>");
			out.print("<th>Published Year</th>");
			out.print("<th>Total Copies</th>");
			out.print("</tr>");
			out.print("</thead>");
			out.print("<tbody>");

			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>" + rs.getInt("BookID") + "</td>");
				out.print("<td>" + rs.getString("Title") + "</td>");
				out.print("<td>" + rs.getString("Author") + "</td>");
				out.print("<td>" + rs.getInt("PublishedYear") + "</td>");
				out.print("<td>" + rs.getInt("TotalCopies") + "</td>");
				out.print("</tr>");
			}
			out.print("</tbody>");
			out.print("</table>");
			
			out.print("<div class='m-2 mt-4'>");
			out.print("<button type='submit' class='btn btn-primary w-50 '><a href='getBooks.html'>Get Books</a></button>");
			out.print("<button type='submit' class='btn btn-warning w-50 '><a href='returnBook.html'>Return Books</a></button><br>");
			out.print("</div>");
			out.print("<div class='d-flex justify-content-center mt-3'>");
			out.print("<button type='submit' class='btn btn-danger w-50'>");
			out.print("<a href='logOut' class='text-white text-decoration-none'>Log Out</a>");
			out.print("</button>");
			out.print("</div>");
			out.print("</body></html>");
		} catch (SQLException e) {
			out.print("<p>Error: " + e.getMessage() + "</p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
