package cmu.ds.geolocationapp;

import android.app.Activity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class provides a method to retrieve location information for a given IP address
 * by making a GET request to a web service API and parsing the JSON response.
 *  * @author Ruta Deshpande
 *  * Email id - rutasurd@andrew.cmu.edu
 *  * andrew id - rutasurd
 *  Code Reference - AndroidInterestingPicture Lab
 */
public class GetLocation {
    IPGeolocation ipGeolocation = null;
    String inputIP = null;
    JsonObject locationResponse = null;

    /**
     * Retrieves the location information for the given IP address using the web service API
     * and displays the information on the activity using an instance of IPGeolocation.
     *
     * @param input    The IP address to retrieve location information for.
     * @param activity The activity to display the location information on.
     * @param ipg      An instance of IPGeolocation to display the location information.
     */
    public void getLocation(String input, Activity activity, IPGeolocation ipg) {
        this.ipGeolocation = ipg;
        this.inputIP = input;
        new BackgroundTask(activity).execute();
    }
    /**
     * Inner class to run the location retrieval process in the background and display the
     * retrieved information on the activity.
     */
    private class BackgroundTask {
        private Activity activity;
        int responseCode;
        /**
         * Constructor to initialize the background task with the given activity.
         * @param activity The activity to display the location information on.
         */
        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }

        /**
         * Starts the background thread to execute the location retrieval process.
         */
        private void startBackground() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        doInBackground();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                onPostExecute();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }).start();
        }

        /**
         * Executes the background task to retrieve location information for the input IP address.
         */
        public void execute() {
            startBackground();
        }

        /**
         * Displays the retrieved location information on the activity.
         * @throws JSONException If an error occurs while parsing the JSON response.
         */
        private void onPostExecute() throws JSONException {
            ipGeolocation.showLocation(locationResponse);
        }

        /**
         * Calls getLocationFromWebService() with Ip address
         * @throws IOException
         */
        private void doInBackground() throws IOException {
            locationResponse = getLocationFromWebService(inputIP);
        }

        /**
         * Retrieves the location information for the input IP address from the web service API.
         *
         * @param input The IP address to retrieve location information for.
         * @return The JSON response containing the location information.
         * @throws IOException If an error occurs during the retrieval process.
         */
        private JsonObject getLocationFromWebService (String input) throws IOException {

            String apiUrl = "https://rutasd-vigilant-fishstick-rprqp54jxqghpg99-8080.preview.app.github.dev/api?ip="+input;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            StringBuilder jsonResponse = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                jsonResponse.append(output);
            }
            JsonObject jsonObject = new Gson().fromJson(jsonResponse.toString(), JsonObject.class);
            return jsonObject;
        }
    }
}
