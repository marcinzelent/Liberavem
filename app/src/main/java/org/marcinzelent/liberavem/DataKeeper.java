package org.marcinzelent.liberavem;

import android.app.Activity;
import android.app.Fragment;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataKeeper {

    private static final DataKeeper instance = new DataKeeper();

    public static DataKeeper getInstance() {
        return instance;
    }

    private DataKeeper() {
    }

    private List<Object> fragments = new ArrayList<Object>();
    private Bird[] birds;
    private Observation[] observations;

    public Bird[] getBirds() {
        return birds;
    }

    public void setBirds(Bird[] birds) {
        this.birds = birds;
    }

    public Observation[] getObservations() {
        return observations;
    }

    public void setObservations(Observation[] observations) {
        this.observations = observations;
    }

    public void downloadData(final Activity activity) {
        if (birds == null) downloadBirds(activity);
        downloadObservations(activity);
    }

    private void downloadBirds(final Activity activity) {
        String birdsUrl = "http://birdobservationservice.azurewebsites.net/Service1.svc/birds";
        final StringRequest birdsRequest = new StringRequest(Request.Method.GET, birdsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                birds = gson.fromJson(response, Bird[].class);

                callPopulator(activity);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Couldn't connect to the database!", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(activity).add(birdsRequest);
    }

    private void downloadObservations(final Activity activity) {
        String observationsUrl = "http://birdobservationservice.azurewebsites.net/Service1.svc/observations";
        final StringRequest observationsRequest = new StringRequest(Request.Method.GET, observationsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                observations = gson.fromJson(response, Observation[].class);
                for (Observation o : observations) {
                    int p = o.getCreated().indexOf('+');
                    long epoch = Long.parseLong(o.getCreated().substring(6, p));
                    Date date = new Date(epoch);
                    String formatted = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH).format(date);
                    o.setCreated(formatted);
                }
                callPopulator(activity);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Couldn't connect to the database!", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(activity).add(observationsRequest);
    }

    public void addFragment(Object fragment) {
        fragments.add(fragment);
    }

    public void clearFragments() {
        fragments.clear();
    }

    private void callPopulator(Activity activity) {
        if (birds != null && observations != null) {
            for(Object fragment : fragments) {
                if (fragment.getClass() == AllObservationsFragment.class)
                    ((AllObservationsFragment) fragment).populateList(observations, birds);
                else {
                    List<Observation> myObservationsList = new ArrayList<>();
                    for (Observation o : observations) {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if (o.getUserId().equals(uid)) myObservationsList.add(o);
                    }
                    Observation[] myObservations = new Observation[myObservationsList.size()];
                    myObservations = myObservationsList.toArray(myObservations);
                    ((MyObservationsFragment) fragment).populateList(myObservations, birds);
                }
            }
        }
    }
}
