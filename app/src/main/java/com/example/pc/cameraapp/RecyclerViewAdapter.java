package com.example.pc.cameraapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.cameraapp.models.Event;

import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Bitmap bitmap;
    private Context mContext ;
    private List<Event> mData ;


    public RecyclerViewAdapter(Context mContext, List<Event> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.eventName.setText(mData.get(position).getName());
        holder.eventLocation.setText(mData.get(position).getLocation());
        holder.eventImg.setImageBitmap(stringToBitMap(mData.get(position).getImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext,EventActivity.class);
                intent.putExtra("Event Name",mData.get(position).getName());
                mContext.startActivity(intent);

            }
        });

    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView eventLocation;
        ImageView eventImg;


        public MyViewHolder(View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name) ;
            eventLocation = itemView.findViewById(R.id.event_location);
            eventImg = itemView.findViewById(R.id.event_img);


        }
    }


}
