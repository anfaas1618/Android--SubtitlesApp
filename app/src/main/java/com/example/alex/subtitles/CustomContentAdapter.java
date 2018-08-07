package com.example.alex.subtitles;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomContentAdapter extends RecyclerView.Adapter<CustomContentAdapter.ViewHolder> {
    private Context mContext;
    private String[] mDataset;
    private Drawable[] mThumbnails;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView thumbnail;



        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.card_title);
            thumbnail = (ImageView) v.findViewById(R.id.card_thumbnail);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext() , ResultsActivity.class);
                    intent.putExtra("QUERY" , "hash");
                    view.getContext().startActivity(intent);
                }
            });
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomContentAdapter(Context myContext , String[] myDataset , Drawable[] myThumbnails) {
        mContext = myContext;
        mDataset = myDataset;
        mThumbnails = myThumbnails;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.layout_media_item, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset[position]);
        holder.thumbnail.setAdjustViewBounds(true);
        //Bitmap bitmapThumnail = getThumnail(mDataset[position]);
        //Drawable thumbnail = new BitmapDrawable(mContext.getResources(), bitmapThumnail );
        //Drawable thumbnail = new BitmapDrawable(mContext.getResources(), bitmapThumnail );
        holder.thumbnail.setImageDrawable(mThumbnails[position]);
    }

    public Bitmap getThumnail(String path){
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}