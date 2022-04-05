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

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ImageViewHolder> {
    private Context mContext;
    private List<UploadedProduct> mUploads;
    private RecyclerViewClickListener mListener;
    private RecyclerViewDeleteListener dListener;


    public Product_Adapter(Context context, List<UploadedProduct> uploads, RecyclerViewClickListener listener) {
        mContext = context;
        mUploads = uploads;
        mListener = listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
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


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            RecyclerViewDeleteListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.single_pr_name);
            imageView = itemView.findViewById(R.id.single_image_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onClick(itemView, getAdapterPosition());
        }

        @Override
        public void onDelete(View view, int position) {
            dListener.onDelete(view, position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Action Menu");
            MenuItem delete = contextMenu.add(Menu.NONE, 1, 1, "Delete item.");

            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case 1:
                    dListener.onDelete(imageView, getAdapterPosition());
            }
            return false;
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    public interface RecyclerViewDeleteListener {
        void onDelete(View view, int position);
    }

    public void setRecyclerViewDeleteListener(RecyclerViewDeleteListener listener) {
        dListener = listener;
    }

}
