package org.marcinzelent.liberavem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllObservationsFragment extends Fragment {
    Observation[] observations;
    Bird[] birds;

    public AllObservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.observations_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ListView observationsListView = view.findViewById(R.id.observations_list_view);

        observationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent detailsIntent = new Intent(getActivity(), ObservationDetailsActivity.class);
                detailsIntent.putExtra("Observation", observations[position]);

                String photoUrl = "";
                for (Bird bird : birds)
                    if (bird.getId() == observations[position].getBirdId())
                        photoUrl = bird.getPhotoUrl();
                detailsIntent.putExtra("Photo", photoUrl);
                startActivity(detailsIntent);
            }
        });

        String birdsUrl = "http://birdobservationservice.azurewebsites.net/Service1.svc/birds";
        final StringRequest birdsRequest = new StringRequest(Request.Method.GET, birdsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                birds = gson.fromJson(response, Bird[].class);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Couldn't connect to the database!", Toast.LENGTH_LONG).show();
            }
        });

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
                final ObservationsListAdapter adapter = new ObservationsListAdapter(getActivity(), observations, birds);
                observationsListView.setAdapter(adapter);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Couldn't connect to the database!", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(birdsRequest);
        Volley.newRequestQueue(getActivity()).add(observationsRequest);
    }
}
