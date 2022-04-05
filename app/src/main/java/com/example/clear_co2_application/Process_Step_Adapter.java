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

public class Process_Step_Adapter extends RecyclerView.Adapter<Process_Step_Adapter.ImageViewHolder> {
    private Context mContext;
    private List<UploadedProduct> mUploads;
    private OnStepClickListener sListener;

    public Process_Step_Adapter(Context context, List<UploadedProduct> uploads) {
        mContext = context;
        mUploads = uploads;

    }

    @Override
    public Process_Step_Adapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.step_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Process_Step_Adapter.ImageViewHolder holder, int position) {
        UploadedProduct uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.verifyImage.setVisibility(View.GONE);

        if (uploadCurrent.getVerified() == 1)
        {
            holder.verifyImage.setVisibility(View.VISIBLE);
        }

        if (uploadCurrent.getCarbonValue() != "no value")
        {
            holder.carbonValue.setText(uploadCurrent.getCarbonValue());
        }
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;
        public ImageView verifyImage;
        public TextView carbonValue;
        public TextView pro_info;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.singleStep_pr_name);
            imageView = itemView.findViewById(R.id.singleStep_image_upload);
            pro_info = itemView.findViewById(R.id.info_process);
            verifyImage = itemView.findViewById(R.id.verifyImage);
            carbonValue = itemView.findViewById(R.id.carbonValue);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
                    sListener.onDeleteClick(imageView, getAdapterPosition());
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            sListener.onItemClick(itemView, getAdapterPosition());
        }
    }

    public interface OnStepClickListener {
        void onItemClick (View view, int position);
        void onDeleteClick(View view, int position);
    }

    public void setOnStepClickListener(OnStepClickListener listener) {
        sListener = listener;
    }
}


