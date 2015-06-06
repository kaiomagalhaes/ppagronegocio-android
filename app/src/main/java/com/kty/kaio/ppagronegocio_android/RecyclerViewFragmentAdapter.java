package com.kty.kaio.ppagronegocio_android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewFragmentAdapter extends RecyclerView.Adapter<RecyclerViewFragmentAdapter.ViewHolder> {

    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public RecyclerViewFragmentAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    public RecyclerViewFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.mTextView.setBackgroundColor(0xFFFFFF00);
        }
        holder.mTextView.setText(mDataset[position]);
    }

    public int getItemCount() {
        return mDataset.length;
    }
}