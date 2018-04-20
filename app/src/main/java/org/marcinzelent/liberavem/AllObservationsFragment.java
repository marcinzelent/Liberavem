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
        DataKeeper.getInstance().downloadData(getActivity());
    }

    public void populateList(final Observation[] observations, final Bird[] birds) {
        final ListView observationsListView = getView().findViewById(R.id.observations_list_view);

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
        final ObservationsListAdapter adapter = new ObservationsListAdapter(getActivity(), observations, birds);
        observationsListView.setAdapter(adapter);
    }
}
