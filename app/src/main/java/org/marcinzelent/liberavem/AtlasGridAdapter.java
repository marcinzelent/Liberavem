package org.marcinzelent.liberavem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AtlasGridAdapter extends BaseAdapter {

    private Context mContext;
    private Bird[] birds;

    public AtlasGridAdapter(Context c, Bird[] b) {
        mContext = c;
        birds = b;
    }

    public int getCount() {
        return birds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.atlas_grid_item, parent, false);
            viewHolder.photo = convertView.findViewById(R.id.atlas_photo);
            viewHolder.name = convertView.findViewById(R.id.atlas_name);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Picasso.get().load(birds[position].getPhotoUrl()).into(viewHolder.photo);
        viewHolder.name.setText(birds[position].getNameEnglish());

        return convertView;
    }

    private static class ViewHolder {
        ImageView photo;
        TextView name;
    }
}
