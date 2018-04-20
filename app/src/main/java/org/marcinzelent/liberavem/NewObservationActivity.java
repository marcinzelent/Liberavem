package org.marcinzelent.liberavem;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NewObservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                postNewObservation();
            }
        });
    }

    public void postNewObservation() {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("BirdId", "51");
            jsonBody.put("Comment", "Værsgo, my friend");
            jsonBody.put("Created", "/Date(1524182400000+0000)/");
            jsonBody.put("Id", "162");
            jsonBody.put("Latitude", "30.1187718");
            jsonBody.put("Longitude", "51.381484");
            jsonBody.put("Placename", "Black lodge");
            jsonBody.put("Population", "5");
            jsonBody.put("UserId", "Sminem");
            jsonBody.put("NameDanish", "gråkrage");
            jsonBody.put("NameEnglish", "hooded crow");
            final String requestBody = jsonBody.toString();

            String url = "http://birdobservationservice.azurewebsites.net/Service1.svc/observations";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getBaseContext(), "Couldn't add observation to the database!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getBaseContext(), "Successfully added activity to the database!", Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            
            Volley.newRequestQueue(getBaseContext()).add(request);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
