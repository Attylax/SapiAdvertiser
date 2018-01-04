package com.project.attylax.sapiadvertiser;

import android.content.Context;
import android.content.Intent;
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

/**
 * This is the adapter of the Main Activity's RecyclerView
 * It also uses the SapiAdvImageAdapter for showing the images inside the post,
 * as well as assigns an OnClickListener to the Post, that will show the event's details
 */
public class SapiAdvPostAdapter extends RecyclerView.Adapter<SapiAdvPostAdapter.ViewHolder> {
    private List<Post> objects;
    private final Context context;
    private Intent intent;

    SapiAdvPostAdapter(List<Post> objects, Context context) {
        this.objects = objects;
        this.context = context;
        this.intent = new Intent(context, EventDetailsActivity.class);
    }

    /**
     * A unique ViewHolder defined for this adapter; contains references to the post's elements
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvPost;
        TextView tvEvent, tvWriter, tvShortDesc, tvDate, tvLoc, tvPrice;
        List<ImageView> ivImages;
        List<String> imgPaths = new ArrayList<>();
        RecyclerView rvImages;

        ViewHolder(View view){
            super(view);
            cvPost = itemView.findViewById(R.id.cv_post);
            tvEvent = itemView.findViewById(R.id.tv_post_title);
            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvShortDesc = itemView.findViewById(R.id.tv_descShort);
            tvDate = itemView.findViewById(R.id.tv_post_date);
            tvLoc = itemView.findViewById(R.id.tv_location);
            tvPrice = itemView.findViewById(R.id.tv_post_price);
            rvImages = itemView.findViewById(R.id.rv_post_images);
            rvImages.setHasFixedSize(true);
        }
    }

    @Override
    public SapiAdvPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertiser_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * The data which will appear in the posts will be set here
     * @param holder: the ViewHolder defined by the adapter (see above)
     * @param position: the position of the to-be-modified element
     */
    @Override
    public void onBindViewHolder(SapiAdvPostAdapter.ViewHolder holder, int position) {
        holder.tvEvent.setText(objects.get(position).getEventName());
        holder.tvWriter.setText(objects.get(position).getWriterName());
        holder.tvShortDesc.setText(objects.get(position).getShortDescription());
        holder.tvDate.setText(String.format("%s, %s", objects.get(position).getEventDate(), objects.get(position).getEventTime()));
        holder.tvLoc.setText(objects.get(position).getEventLocation());
        holder.tvPrice.setText(String.format("Price: %s", String.valueOf(objects.get(position).getPrice())));
        LinearLayoutManager imgLM = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvImages.setLayoutManager(imgLM);
        holder.imgPaths = objects.get(position).getImagesPath();
        holder.ivImages = new ArrayList<>(holder.imgPaths.size());
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
