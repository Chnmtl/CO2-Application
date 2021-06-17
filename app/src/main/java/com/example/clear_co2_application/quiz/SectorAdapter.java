package com.example.clear_co2_application.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.clear_co2_application.R;

import java.util.ArrayList;

public class SectorAdapter extends ArrayAdapter<Sector>
{
    private Context mContext;
    private int mResource;

    public SectorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Sector> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView txtName = convertView.findViewById(R.id.txtName);

        imageView.setImageResource(getItem(position).getImage());
        txtName.setText(getItem(position).getTitle());

        return convertView;
    }
}
