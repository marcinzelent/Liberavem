package org.marcinzelent.liberavem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class BirdsListAdapter extends ArrayAdapter<String> {

    private final Bird[] birds;
    private Context mContext;

    public BirdsListAdapter(Context context, Bird[] birds){
        super(context, R.layout.observations_list_item);
        this.mContext = context;
        this.birds = birds;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return birds.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.birds_list_item, parent, false);
            mViewHolder.name = convertView.findViewById(R.id.bli_name);
            mViewHolder.photo = convertView.findViewById(R.id.bli_photo);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.name.setText(birds[position].getNameEnglish());
        Picasso.get().load(birds[position].getPhotoUrl()).into(mViewHolder.photo);

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView photo;
    }

}