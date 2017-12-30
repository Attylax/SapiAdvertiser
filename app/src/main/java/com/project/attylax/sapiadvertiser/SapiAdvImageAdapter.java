package com.project.attylax.sapiadvertiser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class SapiAdvImageAdapter extends RecyclerView.Adapter<SapiAdvImageAdapter.ViewHolder> {
    private List<ImageView> images;

    SapiAdvImageAdapter(List<ImageView> images){
        this.images = images;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;

        ViewHolder(View view){
            super(view);
            img = itemView.findViewById(R.id.iv_image);
        }
    }

    @Override
    public SapiAdvImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertiser_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageDrawable();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
