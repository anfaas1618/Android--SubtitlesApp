package com.example.alex.subtitles;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;



public class CustomContentActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_media);
        mRecyclerView = (RecyclerView) findViewById(R.id.customContentRecyclerView);
        //used to improved performace since layout size remains constant
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] mDataset = new String[getAllMedia().size()];
        mDataset = getAllMedia().toArray(mDataset);

        Drawable[] thumbnails = new Drawable[getAllMedia().size()];
        //ContentResolver crThumb = getContentResolver();
        // TODO create caching for the thumbnails
        for(int i=0; i< mDataset.length ; i++){
            Bitmap bitmapThumnail = ThumbnailUtils.createVideoThumbnail(mDataset[i], MediaStore.Video.Thumbnails.MINI_KIND);
            //Bitmap bitmapThumnail = MediaStore.Video.Thumbnails.getThumbnail(crThumb , )
            Drawable thumbnail = new BitmapDrawable(this.getResources(), bitmapThumnail );
            thumbnails[i] = thumbnail;
        }

        mAdapter = new CustomContentAdapter(this , mDataset , thumbnails);
        mRecyclerView.setAdapter(mAdapter);

    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);

        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        return downloadedList;
    }
}