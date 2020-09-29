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

// The servlet invoked to perform a bounds search (min or max).
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/boundssearch.html")
public class BoundsSearchServlet extends AbstractSearchServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response, "/boundsSearch.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String columnName = request.getParameter("columnName");
        String bound = getBound(request);
        boolean isMin = bound.compareTo("min") == 0;
        Model model = ModelFactory.getModel();
        TreeMap<Character, List<Patient>> searchResult = model.boundsSearch(columnName, isMin);
        super.doPost(request, response, searchResult);
    }

    private String getBound(HttpServletRequest request) {
        if (request.getParameter("min") != null) {
            return "min";
        }
        return "max"; //find max by default
    }
}
