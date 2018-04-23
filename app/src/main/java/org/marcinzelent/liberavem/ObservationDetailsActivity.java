package org.marcinzelent.liberavem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ObservationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Observation observation = (Observation) intent.getSerializableExtra("Observation");
        String photoUrl = (String) intent.getSerializableExtra("Photo");

        SquareImageView detailsPhoto = findViewById(R.id.details_photo);
        Picasso.get().load(photoUrl).into(detailsPhoto);
        TextView detailsName = findViewById(R.id.details_name);
        detailsName.setText(observation.getNameEnglish());
        TextView detailsCreated = findViewById(R.id.details_created);
        detailsCreated.setText(observation.getCreated());
        TextView detailsUser = findViewById(R.id.details_user);
        detailsUser.setText(observation.getUserId());
        TextView detailsPopulation = findViewById(R.id.details_population);
        detailsPopulation.setText(String.valueOf(observation.getPopulation()));
        TextView detailsPlacename = findViewById(R.id.details_placename);
        detailsPlacename.setText(observation.getPlaceName());
        TextView detailsLongitude = findViewById(R.id.details_longitude);
        detailsLongitude.setText(String.valueOf(observation.getLongitude()));
        TextView detailsLatitude = findViewById(R.id.details_latitude);
        detailsLatitude.setText(String.valueOf(observation.getLatitude()));
        TextView detailsComment = findViewById(R.id.details_comment);
        if(observation.getComment() != null) detailsComment.setText(observation.getComment());
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
