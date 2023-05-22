/**
 * The IPGeolocation class is an activity that handles
 * the user interface and interacts with GetLocation to obtain
 * location informationfor a specified IP address. It extends AppCompatActivity.
 *  * @author Ruta Deshpande
 *  * Email id - rutasurd@andrew.cmu.edu
 *  * andrew id - rutasurd
 *  Code Reference - AndroidInterestingPicture Lab
 */

package cmu.ds.geolocationapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;


public class IPGeolocation extends AppCompatActivity {

    IPGeolocation ipgActivity = this;
    EditText inputText;
    TextView outputText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final IPGeolocation ipg = this;
        inputText = (EditText) findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        submitButton = (Button) findViewById(R.id.submitButton);

        Button submitButton = (Button) findViewById(R.id.submitButton);

        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                String input = inputText.getText().toString();
                System.out.println("searchTerm = " + input);
                GetLocation getLocation = new GetLocation();
                getLocation.getLocation(input, ipgActivity, ipg);
            }
        });
    }

    public void showLocation(JsonObject locationResponse) throws JSONException {
        TextView displayText = (TextView) findViewById(R.id.outputText);
        int responseCode = locationResponse.get("responseCode").getAsInt();
        JsonElement jsonResponseElement = locationResponse.get("jsonResponse");

        if (responseCode == 200) {
            Gson gson = new Gson();
            LocationInfo locationInfo = gson.fromJson(jsonResponseElement, LocationInfo.class);
            displayText.setText(formatResponse(locationInfo));
        }
        else if(responseCode == 423){
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponseElement, JsonObject.class);
            displayText.setText(jsonObject.get("message").getAsString());
        }
        else{
            displayText.setText("Not Found");
        }
    }


    public String formatResponse(LocationInfo locationInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Country: ").append(locationInfo.getCountry_name()).append("\n\n");
        sb.append("State: ").append(locationInfo.getState_prov()).append("\n\n");
        sb.append("City: ").append(locationInfo.getCity()).append("\n\n");
        sb.append("Zipcode: ").append(locationInfo.getZipcode()).append("\n\n");
        sb.append("Latitude: ").append(locationInfo.getLatitude()).append("\n\n");
        sb.append("Longitude: ").append(locationInfo.getLongitude()).append("\n\n");
        sb.append("Organisation: ").append(locationInfo.getOrganization()).append("\n\n");
        return sb.toString();
    }
}
