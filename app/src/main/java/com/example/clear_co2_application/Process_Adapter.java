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

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Process_Adapter extends RecyclerView.Adapter<Process_Adapter.ImageViewHolder> {
    private Context pContext;
    private List<UploadedProduct> pUploads;
    private RecyclerViewProcessListener pListener;
    private ProcessViewDeleteListener dListener;

    public Process_Adapter(Context context, List<UploadedProduct> uploads, RecyclerViewProcessListener listener) {
        pContext = context;
        pUploads = uploads;
        pListener = listener;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(pContext).inflate(R.layout.process_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UploadedProduct uploadCurrent = pUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.verifyImage.setVisibility(View.GONE);

        if (uploadCurrent.getVerified() == 1) {
            holder.verifyImage.setVisibility(View.VISIBLE);
        }

        if (uploadCurrent.getCarbonValue() != "no value") {
            holder.carbonValue.setText(uploadCurrent.getCarbonValue());
        }
    }

    @Override
    public int getItemCount() {
        return pUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            ProcessViewDeleteListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName;
        public ImageView imageView;
        public ImageView verifyImage;
        public TextView carbonValue;
        public TextView info;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.process_name);
            imageView = itemView.findViewById(R.id.process_image_upload);
            info = itemView.findViewById(R.id.info);
            verifyImage = itemView.findViewById(R.id.process_verifyImage);
            carbonValue = itemView.findViewById(R.id.process_carbonValue);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            pListener.onClick(view, getAdapterPosition());
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
                    dListener.onDeleteClick(imageView, getAdapterPosition());
            }
            return false;
        }

        @Override
        public void onDeleteClick(View view, int position) {
            dListener.onDeleteClick(view, position);
        }
    }

    public interface RecyclerViewProcessListener {
        void onClick(View v, int position);
    }

    public interface ProcessViewDeleteListener {
        void onDeleteClick(View view, int position);
    }

    public void setProcessViewDeleteListener(ProcessViewDeleteListener listener) {
        dListener = listener;
    }

}
