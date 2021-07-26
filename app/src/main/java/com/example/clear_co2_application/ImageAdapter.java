package com.example.clear_co2_application;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{
    private Context mContext;
    private List<UploadedProduct> mUploads;
    private RecyclerViewClickListener mListener;


    public ImageAdapter (Context context,List<UploadedProduct> uploads,RecyclerViewClickListener listener)
    {
        mContext = context;
        mUploads = uploads;
        mListener = listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_item,parent,false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position)
    {
        UploadedProduct uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }




    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView)
        {
            super(itemView);
            textViewName = itemView.findViewById(R.id.single_pr_name);
            imageView = itemView.findViewById(R.id.single_image_upload);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v,int position);

    }



}
