package com.project.attylax.sapiadvertiser;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class SapiAdvPostAdapter extends RecyclerView.Adapter<SapiAdvPostAdapter.ViewHolder> {
    private List<Post> objects;
    private final Context context;

    SapiAdvPostAdapter(List<Post> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvPost;
        TextView tvEvent, tvWriter, tvShortDesc, tvDate, tvLoc;
        List<ImageView> ivImages;
        RecyclerView rvImages;

        ViewHolder(View view){
            super(view);
            cvPost = itemView.findViewById(R.id.cv_post);
            tvEvent = itemView.findViewById(R.id.tv_event);
            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvShortDesc = itemView.findViewById(R.id.tv_descShort);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvLoc = itemView.findViewById(R.id.tv_location);
            rvImages = itemView.findViewById(R.id.rv_images);
            rvImages.setHasFixedSize(true);
        }
    }

    @Override
    public SapiAdvPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertiser_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SapiAdvPostAdapter.ViewHolder holder, int position) {
        holder.tvEvent.setText(objects.get(position).getEventName());
        holder.tvWriter.setText(objects.get(position).getWriterName());
        holder.tvShortDesc.setText(objects.get(position).getDescShort());
        holder.tvDate.setText(objects.get(position).getEventDate());
        holder.tvLoc.setText(objects.get(position).getEventLocation());
        LinearLayoutManager imgLM = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvImages.setLayoutManager(imgLM);
        SapiAdvImageAdapter imgAdapter = new SapiAdvImageAdapter(holder.ivImages);
        holder.rvImages.setAdapter(imgAdapter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
