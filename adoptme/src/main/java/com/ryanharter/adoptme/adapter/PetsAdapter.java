package com.ryanharter.adoptme.adapter;

import com.ryanharter.adoptme.R;
import com.ryanharter.adoptme.model.Pet;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rharter on 2/17/14.
 */
public class PetsAdapter extends ArrayAdapter<Pet> {

    private LayoutInflater mLayoutInflater;

    public PetsAdapter(Context context, List<Pet> objects) {
        super(context, 0, objects);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.row_pet, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) v.findViewById(R.id.image);
            holder.name = (TextView) v.findViewById(R.id.name);
            holder.breed = (TextView) v.findViewById(R.id.breed);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        final Pet pet = getItem(position);

        Picasso.with(getContext()).load(pet.photos.get(0).url).into(holder.image);

        holder.name.setText(pet.name);
        holder.breed.setText(pet.breeds.get(0));

        return v;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView breed;
    }
}
