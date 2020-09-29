package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// The servlet invoked to display patient data
@WebServlet("/editpatient.html")
public class EditPatientDataServlet extends AbstractPatientDataServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet(request, response, "/editPatientData.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        String id = performUpdate(request, model);
        request.setAttribute("id", id);
        request.setAttribute("patientData", model.getPatientData(id));
        request.setAttribute("fullName", model.getPatientFullName(id));
        RequestDispatcher dispatch = request.getRequestDispatcher("/patientData.jsp");
        dispatch.forward(request, response);
    }

    private String performUpdate(HttpServletRequest request, Model model) {
        String[] columnNames = model.getColumnNames();
        String id = request.getParameter("id");
        for (String column : columnNames) {
            String newValue = request.getParameter(column);
            model.editDataframe(id, column, newValue);
        }
        return id;
    }
}
