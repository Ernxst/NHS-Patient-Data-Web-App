package uk.ac.ucl.servlets;

import uk.ac.ucl.dataframe.Patient;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/*
    All search servlets have the same doGet method except for the page name
    used in line 30 - they also have the same ending in the doPost method
 */

public class AbstractSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response,
                         String pageName) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        String[] columnNames = model.getColumnNames();
        request.setAttribute("columnNames", columnNames);
        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(pageName);
        dispatch.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response,
                          TreeMap<Character, List<Patient>> searchResult) throws IOException, ServletException
    {
        request.setAttribute("result", searchResult);
        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
        dispatch.forward(request, response);
    }
}
