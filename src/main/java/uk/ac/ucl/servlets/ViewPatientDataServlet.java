package uk.ac.ucl.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// The servlet invoked to display patient data
@WebServlet("/patient.html")
public class ViewPatientDataServlet extends AbstractPatientDataServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        super.doGet(request, response, "/patientData.jsp");
    }
}
