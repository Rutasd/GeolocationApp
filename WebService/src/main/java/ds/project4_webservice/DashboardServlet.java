/**
 * This servlet is responsible for generating
 * the dashboard data and forwarding the data
 * to the JSP page to display.
 * It retrieves the top countries, top organisations,
 * top client locales and all logs from the database
 * through the GeolocationAnalytics class.
 * It then sets the data as attributes for the JSP and
 * forwards the request and response to the dashboard JSP.
 * @author Ruta Deshpande
 * Email id - rutasurd@andrew.cmu.edu
 * andrew id - rutasurd
 */
package ds.project4_webservice;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.bson.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(req.getServletPath().contains("dashboard")) {
            // Get the top countries
            List<Document> topCountries = GeolocationAnalytics.getTopCountries(3);
            ArrayList<String> countryList = new ArrayList<>();
            for (Document doc : topCountries) {
                countryList.add(doc.getString("_id"));
            }
            //Get top Organisations
            List<Document> topOrganisations = GeolocationAnalytics.getTopOrganizations(3);
            ArrayList<String> organisationList = new ArrayList<>();
            for (Document doc : topOrganisations) {
                organisationList.add(doc.getString("_id"));
            }
            //Get top client locale
            List<Document> topClientLocales = GeolocationAnalytics.getTopClientLocales(3);
            ArrayList<String> clientLocaleList = new ArrayList<>();
            for (Document doc : topClientLocales) {
                clientLocaleList.add(doc.getString("_id"));
            }
            //get all logs
            List<Document> allLogs = GeolocationAnalytics.getAllLogs();
            // Set the attribute for the JSP
            req.setAttribute("topCountries", countryList);
            req.setAttribute("topOrganisations", organisationList);
            req.setAttribute("topClientLocales",clientLocaleList);
            req.setAttribute("allLogs",allLogs);
            // Forward to the dashboard JSP
            RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(req, res);
        }
    }

}
