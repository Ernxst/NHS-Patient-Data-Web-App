package uk.ac.ucl.servlets;

import uk.ac.ucl.dataframe.Patient;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

// The servlet invoked to perform a range search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/rangesearch.html")
public class RangeSearchServlet extends AbstractSearchServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet(request, response, "/rangeSearch.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        String columnName = request.getParameter("columnName");
        String min = request.getParameter("minvalue");
        String max = request.getParameter("maxvalue");

        Model model = ModelFactory.getModel();
        TreeMap<Character, List<Patient>> searchResult = model.rangeSearch(columnName, min, max);
        super.doPost(request, response, searchResult);
    }
}
