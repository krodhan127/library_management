import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

@WebServlet("/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateBookServlet() {
        super();
    }

    // 🟢 Display Update Form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve Parameters (Handle Nulls)
        String bookIdParam = request.getParameter("bookId");
        String bname = request.getParameter("title");
        String aname = request.getParameter("author");
        String yearParam = request.getParameter("year");
        String copyParam = request.getParameter("copy");

        int bookId = (bookIdParam != null && !bookIdParam.isEmpty()) ? Integer.parseInt(bookIdParam) : 0;
        int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : 0;
        int copy = (copyParam != null && !copyParam.isEmpty()) ? Integer.parseInt(copyParam) : 0;

        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Update Book Record</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>body { background: url('lib.jpg'); background-size: cover; } a { text-decoration: none; }</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container d-flex justify-content-center align-items-start mt-5 vh-100'>");
        out.println("<div class='card p-4 shadow-lg' style='width: 400px; background: rgba(255, 255, 255, 0.9); border-radius: 10px;'>");
        out.println("<h3 class='text-center mb-4 text-warning'>Update Book Record</h3>");
        out.println("<form action='UpdateBookServlet' method='post'>"); // Corrected Form Action

        // Book ID (Disabled to prevent changes)
        out.println("<div class='mb-3'><label class='form-label'><b>Book Id :</b></label>");
        out.println("<input type='text' class='form-control' name='bookId' value='" + bookId + "' readonly></div>");

        // Other Fields
        out.println("<div class='mb-3'><label class='form-label'><b>Book Title :</b></label>");
        out.println("<input type='text' class='form-control' name='title' value='" + (bname != null ? bname : "") + "' required></div>");

        out.println("<div class='mb-3'><label class='form-label'><b>Author :</b></label>");
        out.println("<input type='text' class='form-control' name='author' value='" + (aname != null ? aname : "") + "' required></div>");

        out.println("<div class='mb-3'><label class='form-label'><b>Published Year :</b></label>");
        out.println("<input type='text' class='form-control' name='year' value='" + (year > 0 ? year : "") + "' required></div>");

        out.println("<div class='mb-3'><label class='form-label'><b>Total Book Copies :</b></label>");
        out.println("<input type='text' class='form-control' name='bookcopies' value='" + (copy > 0 ? copy : "") + "' required></div>");

        // Submit Button
        out.println("<button type='submit' class='btn btn-warning w-100'>Submit</button>");
        out.println("</form>");
        out.println("<p class='text-center mt-3'>Go Back? <a href='Admin-Operation.html'>Previous Page</a></p>");
        out.println("</div></div>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body></html>");
    }

    // 🔵 Process Update Form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Handle Null Inputs and Convert
        String bookIdParam = request.getParameter("bookId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String yearParam = request.getParameter("year");
        String bookcopiesParam = request.getParameter("bookcopies");

        // Convert Safely
        int bookId = (bookIdParam != null && !bookIdParam.isEmpty()) ? Integer.parseInt(bookIdParam) : 0;
        int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : 0;
        int bookcopies = (bookcopiesParam != null && !bookcopiesParam.isEmpty()) ? Integer.parseInt(bookcopiesParam) : 0;

        // Check if Book ID is Valid
        if (bookId == 0) {
            out.print("<h2 style='color:red;'>Invalid Book ID</h2>");
            return;
        }

        // Database Connection
        Connection con;
        PreparedStatement ps;
        ServletContext context = getServletContext();

        try {
            con = MySQLDatabaseConnection.getDatabaseConnection(context);
            if (con == null) {
                out.print("<h2 style='color:red;'>Database Connection Failed</h2>");
                return;
            }

            // Update Query
            String query = "UPDATE Books SET Title=?, Author=?, PublishedYear=?, TotalCopies=? WHERE BookId=?";
            ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, year);
            ps.setInt(4, bookcopies);
            ps.setInt(5, bookId);

            int row = ps.executeUpdate();

            if (row > 0) {
                out.print("<h2 style='color:green;'>" + title + " Book Updated Successfully!</h2>");
                RequestDispatcher rd = request.getRequestDispatcher("viewBookServlet");
                rd.include(request, response);
            } else {
                out.print("<h2 style='color:red;'>Update Failed!</h2>");
            }
        } catch (SQLException e) {
            out.print("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }
    }
}
