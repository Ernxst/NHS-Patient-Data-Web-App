package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TreeMap;

@WebServlet("/analytics.html")
public class AnalyticsServlet extends KeywordSearchServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        TreeMap<String, LinkedHashMap<String, Integer>> data = model.getAnalyticData();
        request.setAttribute("data", data);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/analytics.jsp");
        dispatch.forward(request, response);
    }
}
