package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/*
    Both the EditPatientDataServlet and ViewPatientDataServlet have the exact same
    doGet method except for the page name used in line 31
 */
public class AbstractPatientDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response,
                                String pageName) throws IOException, ServletException {
        Model model = ModelFactory.getModel();
        String id = request.getParameter("id");
        LinkedHashMap<String, String> patientData = model.getPatientData(id);
        String fullName = model.getPatientFullName(id);

        request.setAttribute("patientData", patientData);
        request.setAttribute("fullName", fullName);
        request.setAttribute("id", id);

        // Invoke the JSP page.
        RequestDispatcher dispatch = request.getRequestDispatcher(pageName);
        dispatch.forward(request, response);
    }
}
