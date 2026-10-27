import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");

        out.println("<html><body>");

        out.println("<h1>Hello World!</h1>");
        out.println("<h2>Welcome, " +name + "!</h2>");
        out.println("</body></html>");
    }
}