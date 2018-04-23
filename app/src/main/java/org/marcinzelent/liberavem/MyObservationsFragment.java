package org.marcinzelent.liberavem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyObservationsFragment extends Fragment {

    public MyObservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.observations_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final SwipeRefreshLayout sfl = view.findViewById(R.id.swiperefresh);
        sfl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        DataKeeper.getInstance().downloadData(getActivity());
                        sfl.setRefreshing(false);
                    }
                }
        );
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
