package com.example.mymusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(@NonNull Context context, String [] songs) {
        super(context,R.layout.custom_row , songs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent  , false) ;
        String sfi = getItem(position);
        TextView textView = (TextView)view.findViewById(R.id.textview2);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView2);
        textView.setText(sfi);
        imageView.setImageResource(R.drawable.musical);
        return view;
    }
}
