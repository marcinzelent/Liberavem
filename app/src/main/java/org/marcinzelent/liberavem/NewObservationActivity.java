package org.marcinzelent.liberavem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class NewObservationActivity extends AppCompatActivity {

    private Bird selectedBird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button cancelButton = findViewById(R.id.new_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button button = findViewById(R.id.new_add);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                postNewObservation();
            }
        });

        Spinner newBirdSpinner = findViewById(R.id.new_birds);
        BirdsListAdapter adapter = new BirdsListAdapter(this, DataKeeper.getInstance().getBirds());
        newBirdSpinner.setAdapter(adapter);
        newBirdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBird = DataKeeper.getInstance().getBirds()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                ((EditText)findViewById(R.id.new_longitude)).setText(String.valueOf(location.getLongitude()));
                ((EditText)findViewById(R.id.new_latitude)).setText(String.valueOf(location.getLatitude()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void postNewObservation() {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("BirdId", selectedBird.getId());
            jsonBody.put("Comment", ((EditText)findViewById(R.id.new_comment)).getText());
            jsonBody.put("Created", "/Date(" + System.currentTimeMillis() + "+0000)/");
            jsonBody.put("Id", "0");
            jsonBody.put("Latitude", ((EditText)findViewById(R.id.new_latitude)).getText());
            jsonBody.put("Longitude", ((EditText)findViewById(R.id.new_longitude)).getText());
            jsonBody.put("Placename", ((EditText)findViewById(R.id.new_place)).getText());
            jsonBody.put("Population", ((EditText)findViewById(R.id.new_population)).getText());
            jsonBody.put("UserId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            jsonBody.put("NameDanish", selectedBird.getNameDanish());
            jsonBody.put("NameEnglish", selectedBird.getNameEnglish());
            final String requestBody = jsonBody.toString();

            String url = "http://birdobservationservice.azurewebsites.net/Service1.svc/observations";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getBaseContext(), "Successfully added activity to the database!", Toast.LENGTH_LONG).show();
                            NewObservationActivity.this.finish();
                            DataKeeper.getInstance().downloadData(NewObservationActivity.this);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getBaseContext(), "Couldn't add observation to the database!", Toast.LENGTH_LONG).show();
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
