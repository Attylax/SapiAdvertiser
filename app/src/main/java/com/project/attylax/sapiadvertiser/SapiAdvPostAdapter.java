package com.project.attylax.sapiadvertiser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SapiAdvPostAdapter extends RecyclerView.Adapter<SapiAdvPostAdapter.ViewHolder> {
    private List<Post> objects;
    private final Context context;
    private Intent intent;

    SapiAdvPostAdapter(List<Post> objects, Context context) {
        this.objects = objects;
        this.context = context;
        this.intent = new Intent(context, EventDetailsActivity.class);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvPost;
        TextView tvEvent, tvWriter, tvShortDesc, tvDate, tvLoc;
        List<ImageView> ivImages;
        List<Uri> imgPaths = new ArrayList<>();
        RecyclerView rvImages;

        ViewHolder(View view){
            super(view);
            cvPost = itemView.findViewById(R.id.cv_post);
            tvEvent = itemView.findViewById(R.id.tv_post_title);
            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvShortDesc = itemView.findViewById(R.id.tv_descShort);
            tvDate = itemView.findViewById(R.id.tv_post_date);
            tvLoc = itemView.findViewById(R.id.tv_location);
            rvImages = itemView.findViewById(R.id.rv_post_images);
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
        holder.tvDate.setText(String.format("%s, %s", objects.get(position).getEventDate(), objects.get(position).getEventTime()));
        holder.tvLoc.setText(objects.get(position).getEventLocation());
        LinearLayoutManager imgLM = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvImages.setLayoutManager(imgLM);
        holder.imgPaths = objects.get(position).getImagesPath();
        holder.ivImages = new ArrayList<>(holder.imgPaths.size());
        /*for (int i = 0; i < holder.imgPaths.size(); ++i){
            holder.ivImages.add(new ImageView(context));
            Glide.with(context).load(holder.imgPaths.get(i)).into(holder.ivImages.get(i));
        }*/
        SapiAdvImageAdapter imgAdapter = new SapiAdvImageAdapter(context, holder.imgPaths);
        holder.rvImages.setAdapter(imgAdapter);

        final int posF = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("POST_DATA", objects.get(posF));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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
