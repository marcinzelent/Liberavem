package org.marcinzelent.liberavem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ObservationsListAdapter extends BaseAdapter {

    private Context context;
    private final Observation[] observations;
    private final Bird[] birds;

    public ObservationsListAdapter(Context context, Observation[] observations, Bird[] birds){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.observations = observations;
        this.birds = birds;
    }

    @Override
    public int getCount() {
        return observations.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.observations_list_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.oli_name);
            viewHolder.date = convertView.findViewById(R.id.oli_date);
            viewHolder.photo = convertView.findViewById(R.id.oli_photo);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.name.setText(observations[position].getNameEnglish());
        viewHolder.date.setText(observations[position].getCreated());

        String photoUrl = "";
        //for (Bird bird : birds)
        //    if (bird.getId() == observations[position].getBirdId())
        //        photoUrl = bird.getPhotoUrl();

        //viewHolder.photo.setImageResource(photoUrl);

        return convertView;
    }

    public void add(int position, Observation observation, Bird bird) {

    }

    private static class ViewHolder {
        TextView name;
        TextView date;
        ImageView photo;
    }

}