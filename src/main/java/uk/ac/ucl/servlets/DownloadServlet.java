package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// The servlet invoked to download the dataframe in a given file format
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the data from the model
        // Then add the data to the request object that will be sent to the Java Server Page, so that
        // the JSP can access the data (a Java data structure).

        String filename = request.getParameter("filename");
        String fullPath = "/Downloads/" + filename;

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        PrintWriter fileWriter = response.getWriter();
        Model model = ModelFactory.getModel();
        model.saveFile(fullPath, fileWriter);
    }
}
