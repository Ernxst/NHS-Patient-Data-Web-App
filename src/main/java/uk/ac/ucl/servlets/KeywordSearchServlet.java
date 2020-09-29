package uk.ac.ucl.servlets;

import uk.ac.ucl.dataframe.Patient;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

// The servlet invoked to perform a keyword search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/keywordsearch.html")
public class KeywordSearchServlet extends AbstractSearchServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet(request, response, "/keywordSearch.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        Model model = ModelFactory.getModel();
        List<String> keywords = getKeywords(request, model);
        TreeMap<Character, List<Patient>> searchResult = model.searchFor(keywords);
        super.doPost(request, response, searchResult);
    }

    protected List<String> getKeywords(HttpServletRequest request, Model model) {
        //get the text from all entries with their corresponding checkbox selected
        List<String> keywords = new ArrayList<>();
        String[] columnNames = model.getColumnNames();
        for (String column : columnNames) {
            String include = request.getParameter("include_" + column);
            boolean includeColumn = include != null;
            if (includeColumn) {
                String keyword = request.getParameter(column + "_keyword");
                keywords.add(keyword);
            }
        }
        return keywords;
    }
}
