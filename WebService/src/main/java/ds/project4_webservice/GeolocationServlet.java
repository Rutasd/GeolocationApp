
/**
 * A servlet that retrieves geolocation data for an IP
 * address and logs the data to a MongoDB database.
 *  * @author Ruta Deshpande
 *  * Email id - rutasurd@andrew.cmu.edu
 *  * andrew id - rutasurd
 */
package ds.project4_webservice;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/*"})
public class GeolocationServlet extends HttpServlet {
    /**
     * Handles GET requests to retrieve geolocation data for an IP address.
     * @param req  the HTTP request object
     * @param res  the HTTP response object
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException if an error occurs while sending the response
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = DATE_FORMAT.format(new Date());
        //create a map to log data
        Map<String, String> logData = new HashMap<>();

        // Retrieve the IP address from the query parameter
        String ipAddress = req.getParameter("ip");
        if (ipAddress == null || ipAddress.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = res.getWriter()) {
                out.print("{ \"error\": \"IP address is required\" }");
                return;
            }
        }
        String apiKey = "af577a73259241b081d8fc04ec8e9f85";
        String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + apiKey + "&ip=" + ipAddress;
        URL url = new URL(apiUrl);
        //build http connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        StringBuilder jsonResponse = new StringBuilder();
        //get response code
        int responseCode = connection.getResponseCode();
        //if 423, append to jsonResponse
        if (responseCode == 423) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String output;
            while ((output = br.readLine()) != null) {
                jsonResponse.append(output);
            }
        }
        //if 500, print appropriate response
        else if (responseCode == 500) {
            jsonResponse.append("{ \"error\": \"Cannot reach the API service, error code: ");
            jsonResponse.append(responseCode);
            jsonResponse.append(" \"}");
        }
        //if any other code than 200, print appropriate response
        else if (responseCode != 200) {
            jsonResponse.append("{ \"error\": \"Failed to fetch data, Invalid input, error code: ");
            jsonResponse.append(responseCode);
            jsonResponse.append(" \"}");
        }
        //sucessful cases
        else {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                jsonResponse.append(output);
            }
            jsonResponse = new StringBuilder(filterJson(jsonResponse.toString()));
        }
        connection.disconnect();
        //create json object with response code and json response as fields
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("responseCode", responseCode);
        jsonObject.add("jsonResponse", new Gson().fromJson(jsonResponse.toString(), JsonObject.class));
       //send response
        try (PrintWriter out = res.getWriter()) {
            out.print(new Gson().toJson(jsonObject));
        }
        //Convert to LocationInfor Json
        Gson gson = new Gson();
        LocationInfo locationInfo = gson.fromJson(jsonResponse.toString(), LocationInfo.class);
        //put fields data into logdata
        logData.put("searchIP",ipAddress);
        logData.put("timeOfRequest", timestamp);
        logData.put("userAgent",req.getHeader("User-Agent"));
        logData.put("Response", jsonResponse.toString());
        logData.put("Country",locationInfo.getCountry_name());
        logData.put("Organisation",locationInfo.getOrganization());
        logData.put("ResponseStatus", String.valueOf(responseCode));
        logData.put("ClientLocale",req.getLocale().toString());
        //log data using logger class method
        Logger.log(logData);

            // Print the JSON response to the jsp
            try (PrintWriter out = res.getWriter()) {
                out.print(jsonResponse.toString());
            }
    }

    /**
     * Filter appropriate fields from the json response from the api
     * Use POJO fields for filtering data
     * @param jsonString
     * @return String representation of POJO
     */
    private String filterJson(String jsonString) {
        Gson gson = new Gson();
        LocationInfo locationInfo = gson.fromJson(jsonString, LocationInfo.class);
        return locationInfo.toString();
    }

}
