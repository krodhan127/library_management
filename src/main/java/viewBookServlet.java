import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewBookServlet")
public class viewBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public viewBookServlet() {
        super();
    }

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
			String query = "SELECT * FROM BOOKS";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery(); 

            	 out.print("<html><head><title>ITP Library Books</title>");
                 out.print("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
                 out.print("</head><body class='container mt-4'>");
                 out.print("<h2 class='text-center mb-4'>ITP Library Books</h2>");
                 out.print("<div class='table-responsive'><table class='table table-striped table-hover' >");
                 out.print("<thead class='table-dark'>");
                 out.print("<tr align='center'>");
                 out.print("<th>Book Id</th>");
                 out.print("<th>Title</th>");
                 out.print("<th>Author</th>");
                 out.print("<th>Published Year</th>");
                 out.print("<th>Total Copies</th>");
                 out.print("<th>Update Book</th>");
                 out.print("<th>Delete Book</th>");
                 out.print("</tr>");
                 out.print("</thead>");
                 out.print("<tbody>");

                while (rs.next()) {
                    out.print("<tr align='center'>");
                    out.print("<td>" + rs.getInt("BookID") + "</td>");
                    out.print("<td>" + rs.getString("Title") + "</td>");
                    out.print("<td>" + rs.getString("Author") + "</td>");
                    out.print("<td>" + rs.getInt("PublishedYear") + "</td>");
                    out.print("<td>" + rs.getInt("TotalCopies") + "</td>");
//   out.print("<td><a class='btn btn-warning w-100' href='updateBook?bookId="+rs.getInt("BookID")+"& title="+rs.getString("Title")+" & author="+rs.getString("Author")+" & year="+rs.getInt("PublishedYear")+" & copy="+rs.getInt("TotalCopies")+"'>Update</a></td>");

                    out.print("<td><a class='btn btn-warning w-100' href='updateBook?bookId=" + rs.getInt("BookID") 
                    + "&title=" + rs.getString("Title") 
                    + "&author=" + rs.getString("Author") 
                    + "&year=" + rs.getInt("PublishedYear") 
                    + "&copy=" + rs.getInt("TotalCopies") 
                    + "'  onclick='return confirm(\"Are you sure you want to Update this book?\")'>Update</a></td>");
                    
//                    out.print("<td><a class='btn btn-danger w-100'  href='deleteBook?bookId="+rs.getInt("BookID")+"'>Delete</a></td>");
                    
                    out.print("<td><a class='btn btn-danger w-100' href='deleteBook?bookId=" + rs.getInt("BookID") + 
                            "' onclick='return confirm(\"Are you sure you want to delete this book?\")'>Delete</a></td>");

                    
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
