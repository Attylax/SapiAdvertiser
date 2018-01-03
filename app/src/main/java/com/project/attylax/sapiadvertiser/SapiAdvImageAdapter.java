package com.project.attylax.sapiadvertiser;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


public class SapiAdvImageAdapter extends RecyclerView.Adapter<SapiAdvImageAdapter.ViewHolder> {
    private List<String> imgPaths;
    private Context context;
    private final float scale;

    SapiAdvImageAdapter(Context context, List<String> imgPaths){
        this.context = context;
        this.imgPaths = imgPaths;
        scale = context.getResources().getDisplayMetrics().density;
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
        Glide.with(context).load(imgPaths.get(position)).placeholder(R.drawable.dummy_image).override((int)(100*scale), (int)(100*scale)).centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }
}
