package com.project.attylax.sapiadvertiser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * The adapter which shows the images inside each individual post
 * The images are visualized with the help of the "Glide" library
 */
public class SapiAdvImageAdapter extends RecyclerView.Adapter<SapiAdvImageAdapter.ViewHolder> {
    private List<String> imgPaths;
    private Context context;
    private final float scale;

    SapiAdvImageAdapter(Context context, List<String> imgPaths){
        this.context = context;
        this.imgPaths = imgPaths;
        scale = context.getResources().getDisplayMetrics().density;
    }

    /**
     * A simple ViewHolder which contains a reference to the image
     */
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

    /**
     * Loads the image from an URI
     * @param holder: the ViewHolder containing the image
     * @param position: the position of the image in the inner list; also the position of the source URI
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(imgPaths.get(position)).placeholder(R.drawable.dummy_image).override((int)(100*scale), (int)(100*scale)).centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }
}
